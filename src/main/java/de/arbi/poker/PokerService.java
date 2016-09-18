package de.arbi.poker;

import com.google.common.net.HostAndPort;

public interface PokerService {
    String getValue();

    String getValue(String s);

    boolean joinGame(String player, HostAndPort ip);

    void onPlayerJoined(String name, HostAndPort ip);

    boolean sendMessage(String name, String text);

    boolean shout(String text);
}
