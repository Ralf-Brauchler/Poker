package de.arbi.poker;

import com.google.common.net.HostAndPort;

import java.net.URL;

interface PokerService {
    String getValue();

    String getValue(String s);

    boolean joinGame(String player, HostAndPort url);

    void onPlayerJoined(String name, HostAndPort url);

    boolean sendMessage(String name, String text);

    boolean shout(String text);
}
