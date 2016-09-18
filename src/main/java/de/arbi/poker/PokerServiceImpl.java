package de.arbi.poker;

import com.google.common.net.HostAndPort;
import de.arbi.poker.game.Game;
import de.arbi.poker.game.Player;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class PokerServiceImpl implements PokerService {

    private Game game;

    public Game getGame() {
        return game;
    }

    private HttpClient http = HttpClientBuilder.create().build();

    public void newGame(Game game, Player player) {
        this.game = game;
        game.getPlayers().add(player);
    }

    public boolean joinGame(Game game, String url, Player player) {
        this.game = game;
        game.getPlayers().add(player);
        game.setHostAndPort(HostAndPort.fromString(url));
        send(game.getHostAndPort(), "join/" + player.getName());
        return true;
    }

    public void quitGame() {

    }

    public void onPlayerJoined(Player player) {
        game.getPlayers().add(player);
        System.out.println("player " + player.getName() + " joined on:" + player.getHostAndPort());
    }

    public boolean sendMessage(Player player, String message) {
        System.out.println("player givenname send message " + message + " to player " + player.getName());
        return true;
    }

    public boolean shout(String text) {
        System.out.println("player givenname shouted " + text);
        return true;
    }

    private boolean send(HostAndPort hostAndPort, String path) {
        try {
            HttpResponse response = http.execute(

            );
            System.out.println(response.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
