package com.lite.jop.demo.gobang.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Hall
 *
 * @author LaineyC
 */
public class Hall {

    private Integer id;

    private List<Player> players = new CopyOnWriteArrayList<>();

    private List<Table> tables = new CopyOnWriteArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

}
