package de.arbi.poker;

import com.google.common.net.HostAndPort;
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

        String path = context.getRequest().getPath();
        HostAndPort host = context.getRequest().getRemoteAddress();

        if (path.startsWith("join")) {
            String[] parts = path.split("/");
            if (parts.length == 2) {
                if (pokerService.joinGame(parts[1], host)) {
                    context.getResponse().status(202).send("joining player " + parts[1] + " with ip:" + host);
                }
            }
        } else if (path.startsWith("/shout")) {
            pokerService.shout("ha");
        } else {
            context.next();
        }
    }
}