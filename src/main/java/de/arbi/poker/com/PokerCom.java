package de.arbi.poker.com;

import de.arbi.poker.handlers.CreateGameHandler;
import de.arbi.poker.handlers.JoinGameHandler;
import de.arbi.poker.handlers.JoinedGameHandler;
import de.arbi.poker.handlers.RootHandler;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.util.Random;

public class PokerCom {
    private static final Logger log = LoggerFactory.getLogger(PokerCom.class);
    private static Random random = new Random(System.currentTimeMillis());

    public static RatpackServer ratpackServer;

    public static void main(String[] args) {
        try {
            runServer(new MBassador());
        } catch (Exception e) {
            log.error("Uncaught Exception", e);
        }
    }

    public static RatpackServer runServer(MBassador bus) throws Exception {
        ratpackServer = RatpackServer.start(serverSpec -> serverSpec
                .serverConfig(config -> {
                    config.baseDir(BaseDir.find());
                    config.port(random.nextInt(1000) + 42000);
                })
                .registry(Guice.registry(bindings -> {
                            bindings.bindInstance(MBassador.class, bus);
                            bindings.module(PokerComModule.class);
                        }
                ))
                .handlers(chain -> chain
                        .post("join/:player/:host/:port", JoinGameHandler.class)
                        .post("create/:player", CreateGameHandler.class)
                        .post("info/joiningplayer/:player", JoinedGameHandler.class)
                        .all(RootHandler.class)
                        .all(ctx -> ctx.render("root handler!"))
                )
        );
        return ratpackServer;
    }

    public static void stopServer() throws Exception {
        ratpackServer.stop();
    }
}
