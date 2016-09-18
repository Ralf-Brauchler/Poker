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
    private final Game game;


    @Inject
    public CreateGameHandler(PokerService pokerService, Game game) {
        this.pokerService = pokerService;
        this.game = game;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        Player player = new Player(context.getPathTokens().get("player"), host);
        pokerService.newGame(game, player);
//        pokerService.onPlayerJoined(player); //TODO decouple newGame from player as standalone
        context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}