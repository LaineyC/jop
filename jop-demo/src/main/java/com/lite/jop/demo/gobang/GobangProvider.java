package com.lite.jop.demo.gobang;

import com.lite.jop.demo.gobang.domain.*;
import com.lite.jop.demo.gobang.parameter.*;
import com.lite.jop.demo.security.SimpleSession;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.Provider;
import com.lite.jop.platform.config.Service;
import com.lite.jop.platform.model.Error;
import com.lite.jop.platform.result.DeferredResult;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GobangProvider
 *
 * @author LaineyC
 */
@Provider
public class GobangProvider {

    private static class DataBase{

        public static AtomicInteger increment = new AtomicInteger();

        public static List<Player> players = new CopyOnWriteArrayList<>();
        static{
            for(String username: new String[]{"272874065", "342479980", "491745874", "350108447"}){
                Player player = new Player();
                player.setId(increment.incrementAndGet());
                player.setUsername(username);
                player.setPassword(username);
                players.add(player);
            }
        }
        public static Player findPlayerById(Integer id){
            for(Player player : players){
                if(player.getId().equals(id)){
                    return player;
                }
            }
            return null;
        }

        public static List<Table> tables = new CopyOnWriteArrayList<>();
        static{
            for(int i = 0 ; i < 12 ; i++){
                Table table = new Table();
                table.setId(increment.incrementAndGet());
                tables.add(table);
            }
        }
        public static Table findTableById(Integer id){
            for(Table table : tables){
                if(table.getId().equals(id)){
                    return table;
                }
            }
            return null;
        }

        public static List<Hall> halls = new CopyOnWriteArrayList<>();
        static{
            Hall hall = new Hall();
            hall.setId(increment.incrementAndGet());
            hall.setTables(tables);
            halls.add(hall);
        }

    }

    public Player systemPlayer = new Player();
    {
        systemPlayer = new Player();
        systemPlayer.setUsername("系统");
    }

    private Hall hall = DataBase.halls.get(0);

    private Map<Player, DeferredResult> playerConnect = new ConcurrentHashMap<>();

    private Map<Player, LinkedBlockingQueue<DeferredResponse>> responseMap = new ConcurrentHashMap<>();

    //缓存响应
    private synchronized void cacheDeferredResponse(Player player, DeferredResponse deferredResponse){
        if(responseMap.containsKey(player)) {
            LinkedBlockingQueue<DeferredResponse> responses = responseMap.get(player);
            try {
                responses.put(deferredResponse);
            }
            catch (InterruptedException e){
                throw new JopException(e);
            }
        }
        else{
            LinkedBlockingQueue<DeferredResponse> responses = new LinkedBlockingQueue<>();
            try {
                responses.put(deferredResponse);
            }
            catch (InterruptedException e){
                throw new JopException(e);
            }
            responseMap.put(player, responses);
        }
    }

    //提交响应
    private synchronized void commitDeferredResponse(Player player){
        DeferredResult deferredResult = new DeferredResult();
        deferredResult.execute(JopServiceContext.getContext());
        DeferredResult old = playerConnect.put(player, deferredResult);
        if(old != null && !old.isCommitted()){
            old.cancel();
        }
        if(responseMap.containsKey(player)) {
            LinkedBlockingQueue<DeferredResponse> responses = responseMap.get(player);
            DeferredResponse deferredResponse = responses.poll();
            if(deferredResponse != null){
                deferredResult.resolveResult(deferredResponse);
            }
        }
        else{
            //
        }
    }

    @Service(method = "player.login", needSession = false)
    public PlayerLoginResponse player_login(PlayerLoginRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        Player player = null;
        for(Player p : DataBase.players){
            if(p.getUsername().equals(request.getUsername()) && p.getPassword().equals(request.getPassword())){
                player = p;
                break;
            }
        }
        if(player == null){
            throw new ApiException(new Error("登录失败"));
        }
        if(hall.getPlayers().contains(player)){
            return new PlayerLoginResponse( jopServiceContext.getSystemParameter().getSession());
        }
        String uuid = UUID.randomUUID().toString();
        SimpleSession simpleSession = new SimpleSession();
        simpleSession.setAttribute("player", player);
        jopServiceContext.addSession(uuid, simpleSession);
        hall.getPlayers().add(player);
        playerLogin(player);
        return new PlayerLoginResponse(uuid);
    }

    //玩家登陆
    private synchronized void playerLogin(Player player){
        Message message = new Message();
        message.setFrom(systemPlayer);
        message.setContent("玩家 [ " + player.getUsername() + " ] 进入了大厅");
        messageSend(message);
        playerConnect.entrySet().forEach(e -> {
            Player p = e.getKey();
            DeferredResult d = e.getValue();
            if(d.isCommitted()){
                cacheDeferredResponse(p, new DeferredPlayerLoginResponse(player));
            }
            else{
                if(!player.equals(e.getKey())){
                    d.resolveResult(new DeferredPlayerLoginResponse(player));
                }
            }
        });
    }

    @Service(method = "player.detail")
    public PlayerDetailResponse player_detail(PlayerDetailRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.getSession();
        Player player = simpleSession.getAttribute("player");
        return new PlayerDetailResponse(player);
    }

    @Service(method = "deferred.connect")
    public ServiceResult deferred_connect(DeferredConnectRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.getSession();
        Player player = simpleSession.getAttribute("player");
        commitDeferredResponse(player);
        return (s) -> {};
    }

    @Service(method = "message.send")
    public MessageSendResponse message_send(MessageSendRequest request){
        Player from = DataBase.findPlayerById(request.getFrom());
        Player to = DataBase.findPlayerById(request.getTo());
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setContent(request.getContent());
        messageSend(message);
        return new MessageSendResponse();
    }

    //消息发送
    private synchronized void messageSend(Message message){
        if(message.getTo() == null){
            playerConnect.entrySet().forEach(e -> {
                Player p = e.getKey();
                DeferredResult d = e.getValue();
                if(d.isCommitted()){
                    cacheDeferredResponse(p, new DeferredMessageSendResponse(message));
                }
                else{
                    d.resolveResult(new DeferredMessageSendResponse(message));
                }
            });
        }
        else{
            DeferredResult d = playerConnect.get(message.getTo());
            if(d.isCommitted()) {
                cacheDeferredResponse(message.getTo(), new DeferredMessageSendResponse(message));
            }
            else{
                d.resolveResult(new DeferredMessageSendResponse(message));
            }
        }
    }

    @Service(method = "player.logout")
    public PlayerLogoutResponse player_logout(PlayerLogoutRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.removeSession();
        Player player = simpleSession.getAttribute("player");
        hall.getPlayers().remove(player);
        playerLogout(player);
        return new PlayerLogoutResponse();
    }

    @Service(method = "player.logout", version = "2.0")
    public PlayerLogoutResponse logout$2_0(PlayerLogoutRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.removeSession();
        Player player = simpleSession.getAttribute("player");
        hall.getPlayers().remove(player);
        playerLogout(player);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return new PlayerLogoutResponse(sdf.format(new Date()));
    }

    //玩家注销
    private synchronized void playerLogout(Player player){
        Message message = new Message();
        message.setFrom(systemPlayer);
        message.setContent("玩家 [ " + player.getUsername() + " ] 离开了大厅");
        messageSend(message);
        playerConnect.entrySet().forEach(e -> {
            Player p = e.getKey();
            DeferredResult d = e.getValue();
            if (d.isCommitted()) {
                cacheDeferredResponse(p, new DeferredPlayerLogoutResponse(player));
            } else {
                if (!player.equals(e.getKey())) {
                    d.resolveResult(new DeferredPlayerLogoutResponse(player));
                }
            }
        });
    }

    @Service(method = "hall.detail")
    public HallDetailResponse hall_detail(HallDetailRequest request){
        Hall hall = new Hall();
        hall.setId(this.hall.getId());
        hall.setPlayers(this.hall.getPlayers());
        this.hall.getTables().forEach(t -> {
            Table table = new Table();
            table.setId(t.getId());
            table.setP1(t.getP1());
            table.setP2(t.getP2());
            hall.getTables().add(table);
        });
        return new HallDetailResponse(hall);
    }

    @Service(method = "table.leave")
    public TableLeaveResponse table_leave(TableLeaveRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.getSession();
        Player player = simpleSession.getAttribute("player");
        Table table = DataBase.findTableById(request.getTable());
        tableLeave(table, player);
        return new TableLeaveResponse();
    }

    //玩家离开
    public synchronized void tableLeave(Table table, Player player){
        if(table.getP1() == player){
            table.setP1(null);
        }
        else if(table.getP2() == player){
            table.setP2(null);
        }
        Table t = new Table();
        t.setId(table.getId());
        t.setP1(table.getP1());
        t.setP2(table.getP2());
        playerConnect.entrySet().forEach(e -> {
            Player p = e.getKey();
            DeferredResult d = e.getValue();
            if (d.isCommitted()) {
                cacheDeferredResponse(p, new DeferredTableLeaveResponse(t));
            } else {
                d.resolveResult(new DeferredTableLeaveResponse(t));
            }
        });
    }

    @Service(method = "table.ready")
    public TableReadyResponse table_ready(TableReadyRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.getSession();
        Player player = simpleSession.getAttribute("player");
        Table table = DataBase.findTableById(request.getTable());
        tableReady(table, player);
        return new TableReadyResponse();
    }

    //玩家准备
    public synchronized void tableReady(Table table, Player player){
        Player p1, p2, p3 = null;
        if(table.getP1() == null){
            table.setP1(player);
        }
        else if(table.getP2() == null){
            table.setP2(player);
        }
        else{
            p3 = player;
        }
        p1 = table.getP1();
        p2 = table.getP2();

        Table t = new Table();
        t.setId(table.getId());
        t.setP1(table.getP1());
        t.setP2(table.getP2());

        if(p3 != null){
            DeferredResult d = playerConnect.get(p3);
            if(d.isCommitted()){
                cacheDeferredResponse(p3, new DeferredTableReadyResponse(t));
            }
            else{
                d.resolveResult(new DeferredTableReadyResponse(t));
            }
        }
        else{
            playerConnect.entrySet().forEach(e -> {
                Player p = e.getKey();
                DeferredResult d = e.getValue();
                if(d.isCommitted()){
                    cacheDeferredResponse(p, new DeferredTableReadyResponse(t));
                }
                else{
                    d.resolveResult(new DeferredTableReadyResponse(t));
                }
            });
            if(p1 != null && p2 != null){
                Gobang g = new Gobang();
                g.setId(table.getId());
                g.setP1(p1);
                g.setP2(p2);
                g.setNext(p1);
                DeferredResult d1 = playerConnect.get(p1);
                if(d1.isCommitted()){
                    cacheDeferredResponse(p1, new DeferredGobangActionResponse(g));
                }
                else{
                    d1.resolveResult(new DeferredGobangActionResponse(g));
                }
                DeferredResult d2 = playerConnect.get(p2);
                if(d2.isCommitted()){
                    cacheDeferredResponse(p2, new DeferredGobangActionResponse(g));
                }
                else{
                    d2.resolveResult(new DeferredGobangActionResponse(g));
                }
                Gobang gobang = new Gobang();
                gobang.setId(table.getId());
                gobang.setP1(p1);
                gobang.setP2(p2);
                gobang.setNext(p1);
                gobang.setPieces(new Piece[19][19]);
                table.setGobang(gobang);
            }
        }
    }

    @Service(method = "gobang.step")
    public GobangStepResponse gobang_step(GobangStepRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.getSession();
        Player player = simpleSession.getAttribute("player");
        Table table = DataBase.findTableById(request.getTable());
        Step step = new Step();
        step.setPlayer(player);
        Piece piece = new Piece();
        piece.setRow(request.getRow());
        piece.setColumn(request.getColumn());
        step.setPiece(piece);
        gobangStep(table, step);
        return new GobangStepResponse();
    }

    //玩家下每一步棋
    public synchronized void gobangStep(Table table, Step step){
        Gobang gobang = table.getGobang();
        Integer id = gobang.increment();
        step.setId(id);
        Player p1 = table.getP1();
        Player p2 = table.getP2();
        Player player = step.getPlayer();
        Piece piece = step.getPiece();
        piece.setId(id);
        Integer row = piece.getRow();
        Integer column = piece.getColumn();
        Piece[][] pieces = gobang.getPieces();
        DeferredResult d1;
        DeferredResult d2;
        if(pieces[row][column] == null){
            pieces[row][column] = piece;
            piece.setColor(player == p1 ? "black" : "white");
            d1 = playerConnect.get(p1);
            if(d1.isCommitted()){
                cacheDeferredResponse(player, new DeferredGobangStepResponse(step));
            }
            else{
                d1.resolveResult(new DeferredGobangStepResponse(step));
            }
            d2 = playerConnect.get(p2);
            if(d2.isCommitted()){
                cacheDeferredResponse(player, new DeferredGobangStepResponse(step));
            }
            else{
                d2.resolveResult(new DeferredGobangStepResponse(step));
            }
            if(gobang.isWin(step)){
                Gobang g = new Gobang();
                g.setId(table.getId());
                g.setFinish(true);
                g.setWinner(player);
                d1 = playerConnect.get(p1);
                if(d1.isCommitted()){
                    cacheDeferredResponse(p1, new DeferredGobangActionResponse(g));
                }
                else{
                    d1.resolveResult(new DeferredGobangActionResponse(g));
                }
                d2 = playerConnect.get(p2);
                if(d2.isCommitted()){
                    cacheDeferredResponse(p2, new DeferredGobangActionResponse(g));
                }
                else{
                    d2.resolveResult(new DeferredGobangActionResponse(g));
                }
                table.setP1(null);
                table.setP2(null);
                table.setGobang(null);
            }
            else if(gobang.isDraw()){
                Gobang g = new Gobang();
                g.setId(table.getId());
                g.setFinish(true);
                d1 = playerConnect.get(p1);
                if(d1.isCommitted()){
                    cacheDeferredResponse(p1, new DeferredGobangActionResponse(g));
                }
                else{
                    d1.resolveResult(new DeferredGobangActionResponse(g));
                }
                d2 = playerConnect.get(p2);
                if(d2.isCommitted()){
                    cacheDeferredResponse(p2, new DeferredGobangActionResponse(g));
                }
                else{
                    d2.resolveResult(new DeferredGobangActionResponse(g));
                }
                table.setP1(null);
                table.setP2(null);
                table.setGobang(null);
            }
        }
    }

}


