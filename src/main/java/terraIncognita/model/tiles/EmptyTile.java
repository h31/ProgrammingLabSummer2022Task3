package terraIncognita.model.tiles;

public class EmptyTile extends Tile {

    public static final EmptyTile INSTANCE = new EmptyTile();

    private EmptyTile() {
        setImageFileName("EmptyTile.png");
    }
}
