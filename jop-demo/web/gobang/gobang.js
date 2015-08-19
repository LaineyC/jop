/**
 * Created by LaineyC on 2015/7/31.
 */
;(function( $, window, undefined ) {

    $(function(){

        var session = $.cookie("session");
        if(!session){
            window.location = "/gobang/login.html";
            return;
        }
        //Jop客户端
        var client = new JopClient("/api", "gobang", "971B1EC7563DA71B143EDE15D1E34AAC420C9942");
        client.setSession(session);

        //下一步落子的玩家显示
        var $nextPlayer = $("#next-player");
        //下一步落子的玩家
        var nextPlayer;
        //当前游戏桌
        var currentTable;

        //当前玩家
        var currentPlayer;
        //请求当前玩家数据
        client.execute({},"player.detail").done(function(response){
            currentPlayer = response.body;

            //请求大厅数据
            client.execute({},"hall.detail").done(function(response){
                if(response.errors.length){
                    //理论上应该是判断错误码
                    if(response.errors[0].message == "无效sessionId"){
                        window.location = "/gobang/login.html";
                        return;
                    }
                    alert(response.errors[0].message);
                }
                else{
                    loadHall(response.body);
                }
            });

            deferredConnect();
        });

        //大厅
        var $hall = $("#hall");
        //游戏室
        var $room = $("#room");
        //离开按钮
        var $leaveBtn = $("#leave-btn");
        $leaveBtn.click(function(){
            $leaveBtn.hide();
            $room.hide();
            $hall.show("fast");
        });

        //在线玩家
        var $onlinePlayer =$("#online-player");
        //玩家消息
        var $playerMessage = $("#player-message");

        //是否在请求中
        var isLoading = false;
        //消息输入框
        var $message = $("#message");
        //消息发送按钮
        var $messageSendBtn = $("#message-send-btn");
        //发送消息
        function messageSend(){
            if(!currentPlayer || isLoading){
                return;
            }
            var message = $message.val();
            if(message.length < 1 || message.length > 20){
                return;
            }
            isLoading = true;
            client.execute({from: currentPlayer.id, content: message}, "message.send").done(function(){
                isLoading = false;
            });
            $message.val("");
        }
        $messageSendBtn.click(function(){
            messageSend();
        });
        $message.keydown(function(event){
            if(event.which == 13){
                messageSend();
            }
        });

        //注销按钮
        var $logoutBtn = $("#logout-btn");
        //玩家注销
        $logoutBtn.click(function(){
            if(isLoading){
                return;
            }
            if(currentTable){
                modalShow("正在游戏无法退出！！！");
                return;
            }
            isLoading = true;
            client.execute({}, "player.logout").done(function(){
                isLoading = false;
                window.location = "/gobang/login.html";
            });
        });

        //玩家准备与离开
        $hall.delegate(".btn", "click", function(){
            if(isLoading){
                return;
            }
            isLoading = true;
            var $table = $(this).closest(".game-table");
            var table = $table.data("table");
            var p1 = table.p1;
            var p2 = table.p2;
            var hasMe = (p1 && p1.id == currentPlayer.id) || (p2 && p2.id == currentPlayer.id);
            if(hasMe){
                client.execute({table: table.id}, "table.leave").done(function(){
                    isLoading = false;
                });
            }
            else {
                if (!currentTable) {
                    client.execute({table: table.id}, "table.ready").done(function () {
                        isLoading = false;
                    });
                }
                else{
                    isLoading = false;
                }
            }
        });

        var $chessboard = $room.find("#chessboard");
        $chessboard.delegate("td", "click", function(){
            var $td = $(this);
            var piece = $td.data("piece");
            if(isLoading || !currentPlayer || currentPlayer.id != nextPlayer.id || piece.color){
                return;
            }
            isLoading = true;
            //玩家走一步棋
            client.execute({table: currentTable.id, row: piece.row, column: piece.column}, "gobang.step").done(function(){
                isLoading = false;
            });

        });
        //初始化棋子行列
        $chessboard.find("tr").each(function(i , tr){
            var $tr = $(tr);
            $tr.find("td").each(function(j, td){
                var $td = $(td);
                $td.data("piece",{
                    row: i,
                    column: j
                });
                $td.attr("index", i + "-" + j);
            });
        });

        //对话框
        var $messageDialog = $("#message-dialog");
        var timeout = null;
        //定时显示对话框
        function modalShow(message, time){
            $messageDialog.find("h3").text(message);
            $messageDialog.modal("show");
            timeout = window.setTimeout(function(){
                $messageDialog.modal("hide");
                timeout && window.clearTimeout(timeout);
            }, time ? time : 2500);
        }

        //响应通道
        var channelMethods = {
            "player.login": function(player){
                var $player = $('<a href="javascript:void(0);" class="list-group-item list-group-item-info">' + player.username + '</a>');
                $player.data("player", player);
                $onlinePlayer.append($player);
            },
            "player.logout": function(player){
                $onlinePlayer.find("a").each(function(i, v){
                    var $a = $(v);
                    if($a.data("player").id == player.id){
                        $a.remove();
                        return false;
                    };
                })
            },
            "message.send": function(message){
                var username = message.from.id == currentPlayer.id ? "你说" : message.from.username;
                $playerMessage.append('<a href="javascript:void(0);" class="list-group-item list-group-item-info">' + username + '：' + message.content +  '</a>');
            },
            "table.ready": function(table){
                $hall.find(".game-table").each(function(i ,v){
                    var $table = $(v);
                    var t = $table.data("table");
                    if(t.id == table.id){
                        var p1 = table.p1;
                        var p2 = table.p2;
                        var hasMe = (p1 && p1.id == currentPlayer.id) || (p2 && p2.id == currentPlayer.id);
                        var isStart = p1 && p2;
                        var $readyBtn= $table.find(".btn");
                        if(isStart && hasMe){
                            currentTable = table;
                            $readyBtn.addClass("disabled");
                            $readyBtn.prop("disabled", true);
                            $readyBtn.text("开战中");
                        }
                        else if(hasMe){
                            currentTable = table;
                            $readyBtn.removeClass("disabled");
                            $readyBtn.prop("disabled", false);
                            $readyBtn.text("离开");
                        }
                        else if(isStart){
                            $readyBtn.addClass("disabled");
                            $readyBtn.prop("disabled", true);
                            $readyBtn.text("开战中");
                        }
                        else{
                            $readyBtn.removeClass("disabled");
                            $readyBtn.prop("disabled", false);
                            $readyBtn.text("准备");
                        }
                        $table.find(".p1").html(p1 ? ("P1：" + p1.username) : "P1：");
                        $table.find(".p2").html(p2 ? ("P2：" + p2.username) : "P2：");
                        $table.data("table", table);
                        return false;
                    }
                });
            },
            "table.leave": function(table){
                $hall.find(".game-table").each(function(i ,v){
                    var $table = $(v);
                    var t = $table.data("table");
                    if(t.id == table.id) {
                        var p1 = table.p1;
                        var p2 = table.p2;
                        var hasMe = (p1 && p1.id == currentPlayer.id) || (p2 && p2.id == currentPlayer.id);
                        var hasMeThen = (t.p1 && t.p1.id == currentPlayer.id) || (t.p2 && t.p2.id == currentPlayer.id);
                        var isMe = hasMeThen && !hasMe;
                        var $readyBtn = $table.find(".btn");
                        if (hasMe) {
                            $readyBtn.removeClass("disabled");
                            $readyBtn.prop("disabled", false);
                            $readyBtn.text("离开");
                        }
                        else {
                            if (isMe) {
                                currentTable = null;
                                $readyBtn.removeClass("disabled");
                                $readyBtn.prop("disabled", false);
                                $readyBtn.text("准备");
                            }
                            else {
                                $readyBtn.removeClass("disabled");
                                $readyBtn.prop("disabled", false);
                                $readyBtn.text("准备");
                            }
                        }
                        $table.find(".p1").html(p1 ? ("P1：" + p1.username) : "P1：");
                        $table.find(".p2").html(p2 ? ("P2：" + p2.username) : "P2：");
                        $table.data("table", table);
                        return false;
                    }
                });
            },
            "gobang.action": function(gobang){
                var finish = gobang.finish;
                if(finish){
                    var winner = gobang.winner;
                    $hall.find(".game-table").each(function(i ,v){
                        var $table = $(v);
                        var t = $table.data("table");
                        if(t.id == gobang.id){
                            var $readyBtn = $table.find(".btn");
                            $readyBtn.removeClass("disabled");
                            $readyBtn.prop("disabled", false);
                            $readyBtn.text("准备");
                            $table.find(".p1").html("P1：");
                            $table.find(".p2").html("P2：");
                            t.p1 = null;
                            t.p2 = null;
                        }
                    });
                    $chessboard.find("tr").each(function(i , tr){
                        var $tr = $(tr);
                        $tr.find("td").each(function(j, td){
                            var $td = $(td);
                            $td.data("piece",{
                                row: i,
                                column: j
                            });
                            $td.removeClass("black").removeClass("white");
                        });
                    });
                    //$room.hide();
                    if(winner){
                        modalShow(winner.id == currentPlayer.id ? "起来！！！" : "垃圾！！！");
                    }
                    else{
                        modalShow("平局！！！");
                    }
                    $leaveBtn.show();
                    currentTable = null;
                    nextPlayer = null;
                }
                else{
                    $hall.hide();
                    nextPlayer = gobang.next;
                    currentTable.p1 = gobang.p1;
                    currentTable.p2 = gobang.p2;
                    $nextPlayer.html("当前棋手：" + nextPlayer.username);
                    if(nextPlayer.id == currentPlayer.id){
                        $chessboard.removeClass("not-allowed");
                    }
                    else{
                        $chessboard.addClass("not-allowed");
                    }
                    $room.show("fast", function(){
                        modalShow("开战！！！");
                    });
                }
            },
            "gobang.step": function(step){
                var p1 = currentTable.p1;
                var p2 = currentTable.p2;
                var piece = step.piece;
                nextPlayer = p1.id == step.player.id ? p2 : p1;
                $nextPlayer.html("当前棋手：" + nextPlayer.username);
                if(nextPlayer.id == currentPlayer.id){
                    $chessboard.removeClass("not-allowed");
                }
                else{
                    $chessboard.addClass("not-allowed");
                }
                var $td = $chessboard.find("td[index='"+ (piece.row + "-" + piece.column) +"']");
                $td.addClass(piece.color);
                $td.data("piece", piece);
            }
        };

        //延时长连接
        function deferredConnect(){
            client.execute({},"deferred.connect").done(function(response){
                if(response.errors.length){
                    alert(response.errors[0].message);
                }
                else{
                    var channelMethod = response.channelMethod;
                    channelMethods[channelMethod](response.body);
                    deferredConnect();
                }
            });
        }

        //加载大厅数据 游戏桌 在线玩家
        function loadHall(hall){
            for(var i = 0 ; i < hall.tables.length ; i++){
                var table = hall.tables[i];
                var p1 = table.p1;
                var p2 = table.p2;
                var hasMe = (p1 && p1.id == currentPlayer.id) || (p2 && p2.id == currentPlayer.id);
                var isStart = p1 && p2;
                var disabledProp = "";
                var disabledClass = "";
                var btnText = "";
                if(isStart && hasMe){
                    currentTable = table;
                    disabledProp = 'disabled="disabled"';
                    disabledClass = "disabled";
                    btnText = "开战中";
                }
                else if(hasMe){
                    currentTable = table;
                    btnText = "离开";
                }
                else if(isStart){
                    disabledProp = 'disabled="disabled"';
                    disabledClass = "disabled";
                    btnText = "开战中";
                }
                else{
                    btnText = "准备";
                }
                var $able = $(
                    '<div class="col-sm-4 game-table"> '+
                        '<div class="panel panel-primary"> '+
                        '<div class="panel-heading"><h3 class="panel-title">' + (i + 1) + '号桌</h3></div> '+
                            '<div class="list-group bg-info"> '+
                                '<a href="javascript:void(0);" class="list-group-item list-group-item-info">' +
                                    '<button ' + disabledProp + ' type="button" class="btn btn-primary input-sm' + disabledClass + '">' + btnText + '</button>' +
                                '</a> ' +
                                '<a href="javascript:void(0);" class="list-group-item list-group-item-info p1">'+ (p1 ? ("P1：" + p1.username) : 'P1：') +'</a>' +
                                '<a href="javascript:void(0);" class="list-group-item list-group-item-info p2">'+ (p2 ? ("P2：" + p2.username) : 'P2：') +'</a>' +
                            '</div> '+
                        '</div> '+
                    '</div>'
                )
                $able.data("table", table);
                $hall.append($able);
            }
            for(var i = 0 ; i < hall.players.length ; i++){
                var player = hall.players[i];
                var $player = $('<a href="javascript:void(0);" class="list-group-item list-group-item-info">' + player.username + '</a>');
                $player.data("player", player);
                $onlinePlayer.append($player);
            }
        }
    });

}( jQuery, window ));