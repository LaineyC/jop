package com.lite.jop.demo.gobang.domain;

/**
 * Step
 *
 * @author LaineyC
 */
public class Step {

    private Integer id;

    private Player player;

    private Piece piece;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
