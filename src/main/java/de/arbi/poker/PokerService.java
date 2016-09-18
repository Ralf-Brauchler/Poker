package de.arbi.poker;

import com.google.common.net.HostAndPort;
import de.arbi.poker.game.Game;
import de.arbi.poker.game.Player;

public interface PokerService {

    Game getGame();

    void newGame(Game game, Player player);

    boolean joinGame(Game game, String url, Player player);

    void quitGame();

    void onPlayerJoined(Player player);

    boolean sendMessage(Player player, String message);

    boolean shout(String message);
}
