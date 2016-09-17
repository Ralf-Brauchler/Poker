package de.arbi.poker.ui;

import de.arbi.poker.Poker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerUI extends Application {
    private static final Logger log = LoggerFactory.getLogger(PokerUI.class);

    private Thread serverThread;

    @Override
    public void init() throws Exception {
        super.init();
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Poker.runServer();
                } catch (Exception e) {
                    log.error("Uncaught Exception", e);
                }
            }
        });
        serverThread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Arbi's Poker Game");
        Button btn = new Button();
        btn.setText("Hello World");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Poker.stopServer();
        super.stop();
    }
}
