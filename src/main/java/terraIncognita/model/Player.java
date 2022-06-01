package terraIncognita.model;

import org.jetbrains.annotations.NotNull;
import terraIncognita.App;
import terraIncognita.model.desk.Desk;
import terraIncognita.model.tiles.*;
import terraIncognita.utils.Point;
import terraIncognita.utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Player {

    private int id = 0;

    private Point position;
    private Desk desk = new Desk(0, 0);
    private final String name;
    private boolean hasTreasure = false;
    private boolean isEndGame = false;

    private final Color color;

    public Player(int id, String name, Point startPosition, int vDeskSize, int hDeskSize) {
        this.id = id;
        this.name = name;
        color  = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
        if (startPosition == null || vDeskSize < 1 || hDeskSize < 1) {
            Utils.logErrorWithExit(new IllegalArgumentException("Players start position can't be null"));
            return;
        }
        position = startPosition;
        desk = new Desk(vDeskSize, hDeskSize);
        desk.insertTile(StartTile.INSTANCE, startPosition);
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

    public boolean isHasTreasure() {
        return hasTreasure;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Moves player according to the given movement direction.
     * @param movementDirection direction of player he is trying to go to
     * @return position of tile that is opened. If tile was already opened returns null.
     */
    public List<Point> move(@NotNull MovementDirection movementDirection) {
        List<Point> tilesToUnlock = new ArrayList<>(2);
        Consumer<Point> addTileToStorages = (pos) -> {
            tilesToUnlock.add(pos);
            desk.insertTile(App.game.getLabyrinthTileAt(pos), pos);
        };

        Point expectedPosition = movementDirection.move(position);
        Tile tile = App.game.getLabyrinthTileAt(expectedPosition);

        if (desk.getTileAt(expectedPosition) instanceof UnopenedTile) {
            addTileToStorages.accept(expectedPosition);
        }

        if (!(tile instanceof WallTile || tile instanceof WormholeTile)) {
            position = expectedPosition;
        }

        if (tile instanceof WormholeTile) {
            position = App.game.getNextWormholePosition(((WormholeTile)tile).getNumber());
            if (desk.getTileAt(position) instanceof UnopenedTile) {
                addTileToStorages.accept(position);
            }
        }

        if (tile instanceof TreasureTile) {
            hasTreasure = true;
        }

        isEndGame = (tile instanceof EndTile) && hasTreasure;

        return tilesToUnlock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return  id == player.id &&
                hasTreasure == player.hasTreasure &&
                isEndGame == player.isEndGame &&
                position.equals(player.position) &&
                desk.equals(player.desk) &&
                name.equals(player.name) &&
                color.equals(player.color);
    }

    @Override
    public String toString() {
        return name;
    }
}
