package terraIncognita.Model;

import org.jetbrains.annotations.NotNull;
import terraIncognita.Model.Tiles.Tile;

public class Desk {
    private int vCount;
    private int hCount;
    private Tile[][] tiles;

    public Desk(int vCount, int hCount) {
        tiles = new Tile[vCount][hCount];
        this.vCount = vCount;
        this.hCount = hCount;
    }

    public int getHorizontalSize() {
        return hCount;
    }

    public int getVerticalSize() {
        return vCount;
    }

    public Tile getTileAt(int vIndex, int hIndex) {
        return tiles[vIndex][hIndex];
    }

    public void insertTile(@NotNull Tile tile, int xIndex, int yIndex) {
        tiles[yIndex][xIndex] = tile;
    }

}
