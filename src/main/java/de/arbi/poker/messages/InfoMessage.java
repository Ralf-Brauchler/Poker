package de.arbi.poker.messages;

public class InfoMessage {
    private final String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
