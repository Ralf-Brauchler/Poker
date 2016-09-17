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
        context.render("service value: " + pokerService.getValue());
    }
}