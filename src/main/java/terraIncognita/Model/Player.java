package terraIncognita.Model;

import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Tiles.*;
import terraIncognita.Utils.Point;

import java.awt.*;

public class Player {

    private Point position;
    private Desk desk;
    private String name;
    private boolean hasTreasure = false;
    private boolean isEndGame = false;

    private Color color;

    public Player(String name, Point startPosition, int vDeskSize, int hDeskSize) {
        position = new Point(startPosition);
        desk = new Desk(vDeskSize, hDeskSize, true);
        this.name = name;
        desk.insertTile(new StartTile(), startPosition);
        color  = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
    }

    public String getName() {
        return name;
    }

    public Desk getDesk() {
        return desk;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isEndGame() {
        return isEndGame;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Moves player according to the given movement direction.
     * @param movementDirection direction of player he is trying to go to
     * @return position of tile that is opened. If tile was already opened returns null.
     */
    public Point move(MovementDirection movementDirection) {
        Point expectedPosition = movementDirection.move(position);
        Tile tile = Main.game.getLabyrinthTileAt(expectedPosition);
        Point newTileAt = (desk.getTileAt(expectedPosition).getClass() == UnopenedTile.class)? expectedPosition: null;
        desk.insertTile(tile, expectedPosition);
        if (tile.getClass() != WallTile.class &&
            tile.getClass() != WormholeTile.class) {
            position = expectedPosition;
        }
        if (tile.getClass() == TreasureTile.class) {
            hasTreasure = true;
        }

        isEndGame = tile.getClass() == EndTile.class && hasTreasure;

        return newTileAt;
    }

}
