package com.lite.jop.demo.gobang.domain;

/**
 * Table
 *
 * @author LaineyC
 */
public class Table {

    private Integer id;

    private Player p1;

    private Player p2;

    private Gobang gobang;

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

    public Gobang getGobang() {
        return gobang;
    }

    public void setGobang(Gobang gobang) {
        this.gobang = gobang;
    }

}
