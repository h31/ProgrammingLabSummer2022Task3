package terraIncognita.Model;

import terraIncognita.Main;
import terraIncognita.Model.Tiles.Tile;
import terraIncognita.Utils.Utils;

public class Game {

    private static final int MIN_PLAYER_AMOUNT = 2;
    private static final int MAX_PLAYER_AMOUNT = 4;

    private int playerAmount = 2;

    private Desk labyrinth;
    private Player[] players;
    private String startupLabyrinth = Main.LABYRINTHS_DIR + "lab3.txt";

    public int getLabyrinthHorSize() {
        return labyrinth.getHorizontalSize();
    }

    public int getLabyrinthVerSize() {
        return  labyrinth.getVerticalSize();
    }

    public Tile getLabyrinthTileAt(int vIndex, int hIndex) {
        return labyrinth.getTileAt(vIndex, hIndex);
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

    public void startGame() {
        labyrinth = Utils.genLabyrinthFromExistingSource(startupLabyrinth);
        players = new Player[playerAmount];
    }

}
