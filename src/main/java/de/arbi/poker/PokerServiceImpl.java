package de.arbi.poker;

import de.arbi.poker.PokerService;

public class PokerServiceImpl implements PokerService {
    public String getValue() {
        return "none";
    }
    public String getValue(String given) {
        return given;
    }
}
