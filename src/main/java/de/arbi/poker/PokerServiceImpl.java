package de.arbi.poker;

import de.arbi.poker.PokerService;

public class PokerServiceImpl implements PokerService {
    public String getValue() {
        return "none";
    }
    public String getValue(String given) {
        return given;
    }

    public String joinPlayer(String name, String ipadress) {
        return "joining player " + name + "with ip:" + ipadress;
    }

    public String joinedPlayer(String name, String ipadress) {
        return "player " + name + " joined with ip:" + ipadress;
    }

    public String message(String name, String text) {
        return "player givenname send message " + text + " to player " + name;
    }

    public String shout(String text) {
        return "player givenname shouted " + text;
    }
}
