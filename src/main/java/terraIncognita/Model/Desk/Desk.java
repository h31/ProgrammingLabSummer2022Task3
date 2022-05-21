package terraIncognita.Model.Desk;

import org.jetbrains.annotations.NotNull;
import terraIncognita.Model.Tiles.EndTile;
import terraIncognita.Model.Tiles.StartTile;
import terraIncognita.Model.Tiles.Tile;
import terraIncognita.Model.Tiles.UnopenedTile;
import terraIncognita.Utils.Point;

public class Desk {
    protected int vCount;
    protected int hCount;
    protected Tile[][] tiles;

    public Desk(int vCount, int hCount, boolean fillWithUnopened) {
        tiles = new Tile[vCount][hCount];
        this.vCount = vCount;
        this.hCount = hCount;

        if (fillWithUnopened) {
            for (int y = 0; y < vCount; y++) {
                for (int x = 0; x < hCount; x++) {
                    insertTile(new UnopenedTile(), new Point(x, y));
                }
            }
        }

    }

    public Tile getTileAt(@NotNull Point point) {
        return tiles[point.y()][point.x()];
    }

    public void insertTile(@NotNull Tile tile, @NotNull Point point) {
        tiles[point.y()][point.x()] = tile;
    }
}
