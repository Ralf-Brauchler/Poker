package de.arbi.poker.messages;

import de.arbi.poker.game.Player;

public class ChatMessage {
    private final Player player;
    private final String text;

    public ChatMessage(Player player, String text) {
        this.player = player;
        this.text = text;
    }

    public Player getPlayer() {
        return player;
    }

    public String getText() {
        return text;
    }
}
