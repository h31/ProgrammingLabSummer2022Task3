package terraIncognita.model.tiles;

public class EndTile extends Tile {

    public final static EndTile INSTANCE = new EndTile();

    private EndTile() {
        setImageFileName("EndTile.png");
    }
}
