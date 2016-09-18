package de.arbi.poker.ui;

import com.google.common.net.HostAndPort;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.arbi.poker.PokerService;
import de.arbi.poker.com.PokerCom;
import de.arbi.poker.game.Game;
import de.arbi.poker.game.GameScope;
import de.arbi.poker.game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbee.javafx.scene.layout.MigPane;
import ratpack.server.RatpackServer;

public class PokerUI extends Application {
    private static final Logger log = LoggerFactory.getLogger(PokerUI.class);

    private Thread serverThread;
    private volatile RatpackServer ratpackServer;
    private volatile MBassador bus;
    private volatile PokerModule pokerModule;
    private Injector guice;
    private Game game;
    private GameScope gameScope;

    private TextField name;
    private TextField host;


    @Override
    public void init() throws Exception {
        super.init();
        bus = new MBassador();
        pokerModule = new PokerModule(bus);
        guice = Guice.createInjector(pokerModule);
        gameScope = guice.getInstance(GameScope.class);

        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ratpackServer = PokerCom.runServer(pokerModule);
                } catch (Exception e) {
                    log.error("Uncaught Exception", e);
                }
            }
        });
        serverThread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PokerService pokerService = guice.getInstance(PokerService.class);

        primaryStage.setTitle("Arbi's Poker Game");

        name = new TextField();
        name.setPromptText("Enter your name");
        host = new TextField();
        host.setPromptText("Enter host to join");

        Button newBtn = new Button();
        newBtn.setText("New Game");
        newBtn.setOnAction(event -> {
            gameScope.enter();
            game = guice.getInstance(Game.class);
            pokerService.newGame(game, createMyPlayer());
            //TODO call service for create
            System.out.println("New Game, " + game.toString());
        });

        Button quitBtn = new Button();
        quitBtn.setText("Quit Game");
        quitBtn.setOnAction(event -> {
            gameScope.exit();
            pokerService.quitGame();
            System.out.println("Quit Game");
        });

        Button joinBtn = new Button();
        joinBtn.setText("Join Game");
        joinBtn.setOnAction(event -> {
            gameScope.enter();
            game = guice.getInstance(Game.class);
            pokerService.joinGame(game, host.getText(), createMyPlayer());
            System.out.println("Join Game");
        });

        StackPane root = new StackPane();
        MigPane pane = new MigPane("align 50% 50%, gap 3 3, wrap 1", "[align 50%, fill]", "[]");
        pane.add(name);
        pane.add(newBtn);
        pane.add(host);
        pane.add(joinBtn);
        pane.add(quitBtn);
        root.getChildren().add(pane);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private Player createMyPlayer() {
        return new Player(name.getText(), HostAndPort.fromParts(ratpackServer.getBindHost(), ratpackServer.getBindPort()));
    }

    @Override
    public void stop() throws Exception {
        PokerCom.stopServer();
        try {
            gameScope.exit();
        } catch (IllegalStateException e) {
            // Ignore
        }
        super.stop();
    }
}
