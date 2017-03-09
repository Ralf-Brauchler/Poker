package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.PokerService;
import de.arbi.poker.game.Player;
import de.arbi.poker.messages.ChatMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateGameHandler implements Handler {

    private final MBassador bus;
    private final PokerService pokerService;

    @Inject
    public CreateGameHandler(MBassador bus, PokerService pokerService) {
        this.pokerService = pokerService;
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        Player player = new Player(context.getPathTokens().get("player"), host);
        pokerService.newGame(pokerService.getGame(), player);
        bus.publish(new ChatMessage(player, " todo: reasonable text for create."));
//        pokerService.onPlayerJoined(player); //TODO decouple newGame from player as standalone
        context.getResponse().status(202).send("joining player " + player.getName() + " on: " + player.getHostAndPort());
    }
}