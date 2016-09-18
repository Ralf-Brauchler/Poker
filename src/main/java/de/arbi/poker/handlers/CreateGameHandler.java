package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.game.Game;
import de.arbi.poker.game.Player;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateGameHandler implements Handler {

    private final PokerService pokerService;


    @Inject
    public CreateGameHandler(PokerService pokerService) {
        this.pokerService = pokerService;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        Player player = new Player(context.getPathTokens().get("player"), host);
        pokerService.newGame(pokerService.getGame(), player);
//        pokerService.onPlayerJoined(player); //TODO decouple newGame from player as standalone
        context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}