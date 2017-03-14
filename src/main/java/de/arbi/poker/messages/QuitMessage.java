package de.arbi.poker.messages;

import de.arbi.poker.game.Player;

public class QuitMessage {
    private final Player player;

    public QuitMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
