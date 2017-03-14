package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.messages.InfoMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

//import de.arbi.poker.game.Player;

@Singleton
public class JoinedGameHandler implements Handler {

    private final MBassador bus;

    @Inject
    public JoinedGameHandler(MBassador bus) {
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        bus.publish(new InfoMessage("joined fullcircle"));
        //context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}