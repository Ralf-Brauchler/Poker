package de.arbi.poker;

import java.net.URL;

interface PokerService {
    String getValue();

    String getValue(String s);

    boolean joinGame(String player, URL url);

    void onPlayerJoined(String name, URL url);

    boolean sendMessage(String name, String text);

    boolean shout(String text);
}
