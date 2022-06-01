package terraIncognita.model.tiles;

public class WallTile extends Tile {

    public final static WallTile INSTANCE = new WallTile();

    private WallTile() {
        setImageFileName("WallTile.png");
    }
}
