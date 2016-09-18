package de.arbi.poker;

import com.google.common.net.HostAndPort;
import de.arbi.poker.game.Player;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;

@Singleton
public class PokerHandler implements Handler {

    private final PokerService pokerService;

    @Inject
    public PokerHandler(PokerService pokerService) {
        this.pokerService = pokerService;
    }

    @Override
    public void handle(Context context) {
        /*
        String parameter = context.getRequest().getQueryParams().get("text");

        if (parameter != null) {
            context.render("service value: " + pokerService.getValue(parameter));
        } else {
            context.render("service value: " + pokerService.getValue());
        }
        */

//        String path = context.getRequest().getPath();
//        HostAndPort host = context.getRequest().getRemoteAddress();
//
//        if (path.startsWith("join")) {
//            Player player = new Player(context.getPathTokens().get("player"), host);
//            if (pokerService.getGame()) {
//                context.getResponse().status(202).send("joining player " + player.getName() + " on: "+ player.getHostAndPort());
//            }
//        } else if (path.startsWith("/shout")) {
//            pokerService.shout("ha");
//        } else {
//            context.next();
//        }
    }
}