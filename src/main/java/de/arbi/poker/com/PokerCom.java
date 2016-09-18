package de.arbi.poker.com;

import de.arbi.poker.handlers.JoinGameHandler;
import de.arbi.poker.ui.PokerModule;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class PokerCom {
    private static final Logger log = LoggerFactory.getLogger(PokerCom.class);

    private static RatpackServer ratpackServer;

    public static void main(String[] args) {
        try {
            runServer(new PokerModule(new MBassador()));
        } catch (Exception e) {
            log.error("Uncaught Exception", e);
        }
    }

    public static RatpackServer runServer(PokerModule pokerModule) throws Exception {
        ratpackServer = RatpackServer.start(serverSpec -> serverSpec
                .serverConfig(config -> config.baseDir(BaseDir.find()))
                .registry(Guice.registry(bindings -> {
                            bindings.module(pokerModule);
                            bindings.module(PokerComModule.class);
                        }
                ))
                .handlers(chain -> chain
                        .post("join/:player", JoinGameHandler.class)
//                        .post("create/:player", NewGameHandler.class)
                        .all(ctx -> ctx.render("root handler!"))
                )
        );
        return ratpackServer;
    }

    public static void stopServer() throws Exception {
        ratpackServer.stop();
    }
}
