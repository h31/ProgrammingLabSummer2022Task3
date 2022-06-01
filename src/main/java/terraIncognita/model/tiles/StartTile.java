package terraIncognita.model.tiles;

public class StartTile extends Tile {

    public final static StartTile INSTANCE = new StartTile();

    private StartTile() {
        setImageFileName("StartTile.png");
    }
}
