package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.messages.InfoMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JoinedGameHandler implements Handler {

    private final MBassador bus;
    private final PokerService pokerService;

    @Inject
    public JoinedGameHandler(MBassador bus, PokerService pokerService) {
        this.pokerService = pokerService;
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        bus.post(new InfoMessage("joined fullcircle")).now();
        //context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}