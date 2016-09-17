package de.arbi.poker.game;

import java.net.InetAddress;

public class Player {
    private String name;
    private InetAddress ip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
