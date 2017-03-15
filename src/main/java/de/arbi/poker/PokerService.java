package de.arbi.poker;

import com.google.common.net.HostAndPort;
import com.google.inject.Singleton;
import de.arbi.poker.game.Game;
import de.arbi.poker.game.Player;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

@Singleton
public class PokerService {

    private Game game;
    private Player me;

    public Game getGame() {
        return game;
    }

    public Player getMe() {
        return me;
    }

    private HttpClient http = HttpClientBuilder.create().build();

    public boolean newGame(Game game, Player player) {
        this.game = game;
        this.me = player;
        if (game == null || player == null) {
            return false;
        }
        game.setHostAndPort(player.getHostAndPort());
        // Do not add the player, because it is added by the usual way and not implicit by creating the game
        // on the other hand we use the player-web-credentials for getting host and port and to set the "owner" of the game
        // because of all that the following line is commented out
        // game.getPlayers().add(player);
        return true;
    }

    public boolean joinGame(Game game, String url, Player player) {
        this.game = game;
        this.me = player;
        if (game == null || player == null || url == null) {
            return false;
        }
        game.getPlayers().add(player);
        game.setHostAndPort(HostAndPort.fromString(url.replace("http://", "")));
        String path = "join/" + player.getName() + "/" + player.getHostAndPort().getHostText() + "/" + String.valueOf(player.getHostAndPort().getPort());
        HttpResponse response = send(game.getHostAndPort(), path);
        if (response == null) {
            return false;
        }
        int status = response.getStatusLine().getStatusCode();
        return (status == 202);
    }

    public boolean quitGame(Game game, String url, Player player) {
        if (game == null || player == null || url == null) {
            return false;
        }
        game.getPlayers().remove(player);
        game.setHostAndPort(HostAndPort.fromString(url.replace("http://", "")));
        String path = "quit/" + player.getName() + "/" + player.getHostAndPort().getHostText() + "/" + String.valueOf(player.getHostAndPort().getPort());
        HttpResponse response = send(game.getHostAndPort(), path);
        if (response == null) {
            return false;
        }
        int status = response.getStatusLine().getStatusCode();
        this.game = null;
        return (status == 202);
    }

    public HttpResponse send(HostAndPort hostAndPort, String path) {
        HttpResponse response = null;
        try {
            response = http.execute(new HttpPost("http://" + hostAndPort.getHostText() + ":" + String.valueOf(hostAndPort.getPort()) + "/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
