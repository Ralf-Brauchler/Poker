package de.arbi.poker.game;

import net.engio.mbassy.bus.MBassador;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GameScoped
public class Game {
    private List<Player> players = new ArrayList<>();

    private MBassador bus;

    @Inject
    public Game(MBassador bus) {
        this.bus = bus;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
