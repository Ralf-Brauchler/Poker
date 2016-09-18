package de.arbi.poker.messages;

import de.arbi.poker.game.Player;

public class JoinMessage {
    private final Player player;

    public JoinMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
