package de.arbi.poker.game;

import com.google.common.base.MoreObjects;
import com.google.common.net.HostAndPort;
import de.arbi.poker.messages.JoinMessage;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

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
        // Todo: question: how can a listener be "this" in this case?
        bus.subscribe(this);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
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

    @Handler
    public void joinMessage(JoinMessage msg) {
        players.add(msg.getPlayer());
        bus.publish("player " + msg.getPlayer().getName() + " added.");
    }
}
