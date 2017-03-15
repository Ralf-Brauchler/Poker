package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.game.Player;
import de.arbi.poker.messages.QuitMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuitGameHandler implements Handler {

    private final MBassador bus;
    private final PokerService pokerService;

    @Inject
    public QuitGameHandler(MBassador bus, PokerService pokerService) {
        this.pokerService = pokerService;
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort hostandport = HostAndPort.fromParts(context.getPathTokens().get("host"), Integer.parseInt(context.getPathTokens().get("port")));
        Player player = new Player(context.getPathTokens().get("player"), hostandport);
        bus.post(new QuitMessage(player)).now();
        // do the following for all connected players
        // pokerService.send(hostandport, "info/quittingplayer/" + player.getName());
        context.getResponse().status(202).send("quitting player" + player.getName() + " on: " + player.getHostAndPort());
    }
}