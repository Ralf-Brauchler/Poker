package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.game.Player;
import de.arbi.poker.messages.JoinMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JoinGameHandler implements Handler {

    private final MBassador bus;

    @Inject
    public JoinGameHandler(MBassador bus) {
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        Player player = new Player(context.getPathTokens().get("player"), host);
        bus.publish(new JoinMessage(player));
        context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}