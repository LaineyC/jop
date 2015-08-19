package com.lite.jop.demo.gobang.domain;

/**
 * Gobang
 *
 * @author LaineyC
 */
public class Gobang {

    private int increment;

    private Integer id;

    private Player p1;

    private Player p2;

    private Player next;

    private Player winner;

    private Boolean finish;

    private Piece[][] pieces;

    public int increment(){
        return ++increment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    //判断这一步棋是否赢了
    public boolean isWin(Step step){
        Piece piece = step.getPiece();
        String color = piece.getColor();
        Integer row = piece.getRow();
        Integer column = piece.getColumn();

        int number = 0;
        for(int r = row - 1 ; r > 0 && r < row + 3 ; r--){
            Piece p = pieces[r][column];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }
        for(int r = row + 1 ; r < 19 && r > row - 3 ; r++){
            Piece p = pieces[r][column];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }

        number = 0;
        for(int r = row - 1, c = column + 1 ; r > 0 && r < row + 3 && c < 19 && c > column - 3 ; r--, c++){
            Piece p = pieces[r][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }
        for(int r = row + 1, c = column - 1 ; r < 19 && r > row - 3 && c > 0 && c < column + 3 ; r++, c--){
            Piece p = pieces[r][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }

        number = 0;
        for(int c = column - 1 ; c > 0 && c < column + 3 ; c--){
            Piece p = pieces[row][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }
        for(int c = column + 1; c < 19 && c > column - 3 ; c++){
            Piece p = pieces[row][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }

        number = 0;
        for(int r = row - 1, c = column - 1 ; r > 0 && r < row + 3 && c > 0 && c < column + 3 ; r--, c--){
            Piece p = pieces[r][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }
        for(int r = row + 1, c = column + 1 ; r < 19 && r > row - 3 && c < 19 && c > column - 3 ; r++, c++){
            Piece p = pieces[r][c];
            if(p != null && p.getColor().equals(color)){
                number++;
                if(number == 4){
                    return true;
                }
            }
            else{
                break;
            }
        }
        return false;
    }

    //判断玩家是否打平
    public boolean isDraw(){
        if(pieces == null){
            return false;
        }
        return increment == pieces.length * pieces[0].length;
    }

}
