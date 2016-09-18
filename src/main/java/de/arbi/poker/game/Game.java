package de.arbi.poker.game;

import com.google.common.base.MoreObjects;
import com.google.common.net.HostAndPort;
import net.engio.mbassy.bus.MBassador;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GameScoped
public class Game {
    private List<Player> players = new ArrayList<>();

    private MBassador bus;

    private HostAndPort hostAndPort;

    @Inject
    public Game(MBassador bus) {
        this.bus = bus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("players", players).toString();
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
}
