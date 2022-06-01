package terraIncognita.model;

import org.jetbrains.annotations.NotNull;
import terraIncognita.model.desk.Labyrinth;
import terraIncognita.model.tiles.Tile;
import terraIncognita.utils.Point;

import java.io.InputStream;

public class Game {

    public static final int MIN_PLAYER_AMOUNT = 2;
    public static final int MAX_PLAYER_AMOUNT = 4;

    private int playerAmount = 2;
    private Labyrinth labyrinth;
    private Player[] players;
    private int activePlayerIndex = -1;

    public int getLabyrinthHorSize() {
        return labyrinth.getHorizontalSize();
    }

    public int getLabyrinthVerSize() {
        return  labyrinth.getVerticalSize();
    }

    public Tile getLabyrinthTileAt(Point point) {
        return labyrinth.getTileAt(point);
    }

    public Player getActivePlayer() {
        if(activePlayerIndex < 0) {
            return null;
        }
        return players[activePlayerIndex];
    }

    public Point getNextWormholePosition(int oldNumber) {
        return labyrinth.getNextWormholePosition(oldNumber);
    }

    /**
     * Decrements player amount according to the rules.
     * Bounds amount between min and max values.
     * @param decrement int
     * @return int - actual value of player amount after decreasing and bounding
     */
    public int decPlayerAmount(int decrement)   {
        if (decrement > MAX_PLAYER_AMOUNT - MIN_PLAYER_AMOUNT) {
            playerAmount = MIN_PLAYER_AMOUNT;
        } else {
            playerAmount = Math.max(playerAmount - decrement, MIN_PLAYER_AMOUNT);
        }
        return playerAmount;
    }

    /**
     * Increments player amount according to the rules.
     * Bounds amount between min and max values.
     * @param increment int
     * @return int - actual value of player amount after increasing and bounding
     */
    public int incPlayerAmount(int increment) {
        if (increment > MAX_PLAYER_AMOUNT - MIN_PLAYER_AMOUNT) {
            playerAmount = MAX_PLAYER_AMOUNT;
        } else {
            playerAmount = Math.min(playerAmount + increment, MAX_PLAYER_AMOUNT);
        }
        return playerAmount;
    }

    public Player startGame(@NotNull InputStream labyrinthSource) {
        labyrinth = Labyrinth.genLabyrinthFromExistingSource(labyrinthSource);
        players = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = new Player(
                    i,                              // ID
                    "Player " + (i + 1),
                    labyrinth.getStartPosition(),
                    labyrinth.getVerticalSize(),
                    labyrinth.getHorizontalSize()
            );
        }
        return nextPlayer();
    }

    public Player nextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % playerAmount;
        return players[activePlayerIndex];
    }

}
