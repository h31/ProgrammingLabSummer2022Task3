package terraIncognita.model.desk;

import org.jetbrains.annotations.NotNull;
import terraIncognita.model.tiles.Tile;
import terraIncognita.model.tiles.UnopenedTile;
import terraIncognita.utils.Point;
import terraIncognita.utils.Utils;

public class Desk {
    protected int vCount;
    protected int hCount;
    protected Tile[][] tiles;

    public Desk(int vCount, int hCount) {
        this.vCount = vCount;
        this.hCount = hCount;
        if (vCount < 0 || hCount < 0) {
            String message = String.format("Negative sizes of desk provided. [%d x %d]", vCount, hCount);
            Utils.logErrorWithExit(
                    new NegativeArraySizeException(message)
            );
            return;
        }
        tiles = new Tile[vCount][hCount];
    }

    public Tile getTileAt(@NotNull Point point) {
        Tile tile = tiles[point.y][point.x];
        return (tile == null) ? UnopenedTile.INSTANCE : tile ;
    }

    public void insertTile(@NotNull Tile tile, @NotNull Point point) {
        tiles[point.y][point.x] = tile;
    }
}
