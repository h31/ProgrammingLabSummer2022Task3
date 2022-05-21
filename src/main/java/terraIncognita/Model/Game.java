package terraIncognita.Model;

import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Desk.Labyrinth;
import terraIncognita.Model.Tiles.Tile;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.Utils;

public class Game {

    private static final int MIN_PLAYER_AMOUNT = 2;
    private static final int MAX_PLAYER_AMOUNT = 4;
    private static final String STARTUP_LABYRINTH = Main.LABYRINTHS_DIR + "lab1.txt";

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

    /**
     * Decrements player amount according to the rules.
     * Bounds amount between min and max values.
     * @param decrement int
     * @return int - actual value of player amount after decreasing and bounding
     */
    public int decPlayerAmount(int decrement) {
        playerAmount = Math.max(playerAmount - decrement, MIN_PLAYER_AMOUNT);
        return playerAmount;
    }

    /**
     * Increments player amount according to the rules.
     * Bounds amount between min and max values.
     * @param increment int
     * @return int - actual value of player amount after increasing and bounding
     */
    public int incPlayerAmount(int increment) {
        playerAmount = Math.min(playerAmount + increment, MAX_PLAYER_AMOUNT);
        return playerAmount;
    }

    public Player startGame() {
        labyrinth = Utils.genLabyrinthFromExistingSource(STARTUP_LABYRINTH);
        players = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = new Player(
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
