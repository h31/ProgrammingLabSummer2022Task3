package terraIncognita.Model.Desk;

import org.jetbrains.annotations.NotNull;
import terraIncognita.Model.Tiles.EndTile;
import terraIncognita.Model.Tiles.StartTile;
import terraIncognita.Model.Tiles.Tile;
import terraIncognita.Utils.Point;

public class Labyrinth extends Desk {

    private Point startPosition;
    private Point endPosition;

    public Labyrinth(int vCount, int hCount) {
        super(vCount, hCount, false);
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public int getHorizontalSize() {
        return this.hCount;
    }

    public int getVerticalSize() {
        return this.vCount;
    }

    @Override
    public void insertTile(@NotNull Tile tile, @NotNull Point point) {
        super.insertTile(tile, point);

        if (tile.getClass() == StartTile.class) {
            if (startPosition != null) {
                throw new IllegalArgumentException("Two or more StartTiles provided");
            }
            startPosition = point;
        }
        if (tile.getClass() == EndTile.class) {
            if (endPosition != null) {
                throw new IllegalArgumentException("Two or more EndTiles provided");
            }
            endPosition = point;
        }

    }
}
