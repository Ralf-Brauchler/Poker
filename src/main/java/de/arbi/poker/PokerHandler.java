package de.arbi.poker;

import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PokerHandler implements Handler {

    private final PokerService pokerService;

    @Inject
    public PokerHandler(PokerService pokerService) {
        this.pokerService = pokerService;
    }

    @Override
    public void handle(Context context) {
        String parameter = context.getRequest().getQueryParams().get("text");

        if (parameter != null) {
            context.render("service value: " + pokerService.getValue(parameter));
        } else {
            context.render("service value: " + pokerService.getValue());
        }
    }
}