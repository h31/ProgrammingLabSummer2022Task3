package terraIncognita.model.tiles;

public class UnopenedTile extends Tile{

    public final static UnopenedTile INSTANCE = new UnopenedTile();

    private UnopenedTile() {
        setImageFileName("UnopenedTile.png");
    }
}
