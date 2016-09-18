package de.arbi.poker;

import com.google.common.net.HostAndPort;

import java.net.URL;

public class PokerServiceImpl implements PokerService {
    public String getValue() {
        return "none";
    }

    public String getValue(String given) {
        return given;
    }

    public boolean joinGame(String player, HostAndPort url) {
        System.out.println("joining game " + player + " on url:" + url);
        return true;
    }

    public void onPlayerJoined(String name, HostAndPort url) {
        System.out.println("player " + name + " joined on url:" + url);
    }

    public boolean sendMessage(String name, String text) {
        System.out.println("player givenname send message " + text + " to player " + name);
        return true;
    }

    public boolean shout(String text) {
        System.out.println("player givenname shouted " + text);
        return true;
    }

    private boolean send(String message, HostAndPort url) {
        return false;
    }
}
