package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.game.Player;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JoinGameHandler implements Handler {

    private final PokerService pokerService;

    @Inject
    public JoinGameHandler(PokerService pokerService) {
        this.pokerService = pokerService;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        Player player = new Player(context.getPathTokens().get("player"), host);
        pokerService.onPlayerJoined(player);
        context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}