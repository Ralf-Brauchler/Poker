package de.arbi.poker.handlers;

import com.google.common.net.HostAndPort;
import de.arbi.poker.messages.InfoMessage;
import net.engio.mbassy.bus.MBassador;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootHandler implements Handler {

    private final MBassador bus;

    @Inject
    public RootHandler(MBassador bus) {
        this.bus = bus;
    }

    @Override
    public void handle(Context context) {
        HostAndPort host = context.getRequest().getRemoteAddress();
        String infomessage = context.getRequest().getHeaders().toString();
        bus.publish(new InfoMessage(infomessage));
    }
}