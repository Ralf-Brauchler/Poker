package de.arbi.poker;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LoggingHandler implements Handler {
    @Override
    public void handle(Context context) {
        System.out.println("Received: " + context.getRequest().getUri());
        context.next();
    }
}