package terraIncognita.Model;

import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Tiles.StartTile;
import terraIncognita.Model.Tiles.Tile;
import terraIncognita.Model.Tiles.UnopenedTile;
import terraIncognita.Model.Tiles.WallTile;
import terraIncognita.Utils.Point;

public class Player {

    private Point position;
    private Desk desk;
    private String name;

    public Player(String name, Point startPosition, int vDeskSize, int hDeskSize) {
        position = new Point(startPosition);
        desk = new Desk(vDeskSize, hDeskSize, true);
        this.name = name;
        desk.insertTile(new StartTile(), startPosition);
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
        if(tile.getClass() != WallTile.class) {
            position = expectedPosition;
        }
        return newTileAt;
    }

}
