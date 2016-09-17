package de.arbi.poker;

interface PokerService {
    String getValue();
    String getValue(String s);

    String joinPlayer(String name, String ipadress);
    String joinedPlayer(String name, String ipadress);
    String message(String name, String text);
    String shout(String name);
}
