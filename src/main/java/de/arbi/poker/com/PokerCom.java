package de.arbi.poker.com;

import de.arbi.poker.PokerHandler;
import de.arbi.poker.handlers.JoinGameHandler;
import de.arbi.poker.ui.PokerModule;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.registry.Registry;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.util.Random;

public class PokerCom {
    private static final Logger log = LoggerFactory.getLogger(PokerCom.class);
    private static Random random = new Random(System.currentTimeMillis());

    public static RatpackServer ratpackServer;

    public static void main(String[] args) {
        try {
            runServer(new PokerModule(new MBassador()));
        } catch (Exception e) {
            log.error("Uncaught Exception", e);
        }
    }

    public static RatpackServer runServer(PokerModule pokerModule) throws Exception {
        ratpackServer = RatpackServer.start(serverSpec -> serverSpec
                .serverConfig(config -> {
                    config.baseDir(BaseDir.find());
                    config.port(random.nextInt(1000) + 42000);
                })
                .registry(Guice.registry(bindings -> {
                            bindings.module(pokerModule);
                            bindings.module(PokerComModule.class);
                        }
                ))
                .handlers(chain -> chain
                        /*
                        .path("foo", ctx -> ctx.render("from the foo handler")) // Map to /foo
                        .path("bar", ctx -> ctx.render("from the bar handler")) // Map to /bar
                        .prefix("nested", nested -> { // Set up a nested routing block, which is delegated to `nestedHandler`
                            nested.path(":var1/:var2?", ctx -> { // The path tokens are the :var1 and :var2 path components above
                                Map<String, String> pathTokens = ctx.getPathTokens();
                                ctx.render("from the nested handler, var1: " + pathTokens.get("var1") + ", var2: " + pathTokens.get("var2"));
                            });
                        })
                        .path("injected", PokerHandler.class) // Map to a dependency injected handler
                        */
                        .post("join/:player", JoinGameHandler.class)
                        .all(PokerHandler.class)
                        /*
                        .prefix("static", nested -> nested
                                .fileSystem("public", Chain::files)
                        ) // Bind the /static app path to the src/ratpack/assets/images dir
                        */
                        .all(ctx -> ctx.render("root handler!"))
                )
        );
        return ratpackServer;
    }

    public static void stopServer() throws Exception {
        ratpackServer.stop();
    }
}
