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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
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

    private Label hostLbl;
    private TextField name;
    private TextField host;
    private TextArea events;

    private StringProperty hostAndPort = new SimpleStringProperty("");

    @Override
    public void init() throws Exception {
        super.init();
        bus = new MBassador();
        pokerModule = new PokerModule(bus);
        guice = Guice.createInjector(pokerModule);
        gameScope = guice.getInstance(GameScope.class);

        serverThread = new Thread((() -> {
            try {
                ratpackServer = PokerCom.runServer(bus);
                Platform.runLater(() -> hostAndPort.setValue("http://" + ratpackServer.getBindHost() + ":" + ratpackServer.getBindPort()));
            } catch (Exception e) {
                log.error("Uncaught Exception", e);
            }
        }));
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
        events = new TextArea();
        addEvent("event logging starts");

        Button newBtn = new Button();
        Button quitBtn = new Button();
        Button joinBtn = new Button();

        hostLbl = new Label();
        hostLbl.textProperty().bind(hostAndPort);

        newBtn.setText("New Game");
        newBtn.setOnAction(event -> {
            gameScope.enter();
            game = guice.getInstance(Game.class);
            pokerService.newGame(game, createMyPlayer());
            newBtn.setDisable(true);
            joinBtn.setDisable(true);
            quitBtn.setDisable(false);
            addEvent("new game");

        });

        quitBtn.setText("Quit Game");
        quitBtn.setDisable(true);
        quitBtn.setOnAction(event -> {
            gameScope.exit();
            pokerService.quitGame();
            newBtn.setDisable(false);
            joinBtn.setDisable(false);
            quitBtn.setDisable(true);
            addEvent("quit game");
        });

        joinBtn.setText("Join Game");
        joinBtn.setOnAction(event -> {
            gameScope.enter();
            game = guice.getInstance(Game.class);
            pokerService.joinGame(game, host.getText(), createMyPlayer());
            newBtn.setDisable(true);
            joinBtn.setDisable(true);
            quitBtn.setDisable(false);
            addEvent("join game");
        });

        StackPane root = new StackPane();
        MigPane pane = new MigPane("align 50% 50%, gap 3 3, wrap 1", "[align 50%, fill]", "[]");
        pane.add(hostLbl);
        pane.add(name);
        pane.add(newBtn);
        pane.add(host);
        pane.add(joinBtn);
        pane.add(quitBtn);
        pane.add(events);
        root.getChildren().add(pane);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private Player createMyPlayer() {
        return new Player(name.getText(), HostAndPort.fromParts(ratpackServer.getBindHost(), ratpackServer.getBindPort()));
    }

    public void addEvent(String eventstring) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        String fullevent = sdf.format(new java.util.Date()) + ":" + eventstring;
        events.appendText(fullevent + "\n");
        System.out.println(fullevent);
        // log.info(fullevent);
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
