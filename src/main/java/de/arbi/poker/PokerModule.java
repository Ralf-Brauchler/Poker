package de.arbi.poker;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import ratpack.handling.HandlerDecorator;

public class PokerModule extends AbstractModule{
    protected void configure() {
        bind(PokerService.class).to(PokerServiceImpl.class);
        bind(PokerHandler.class);
        Multibinder.newSetBinder(binder(), HandlerDecorator.class).addBinding().toInstance(HandlerDecorator.prepend(new de.arbi.poker.LoggingHandler()));
    }
}
