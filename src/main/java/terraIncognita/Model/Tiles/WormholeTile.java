package terraIncognita.Model.Tiles;

public class WormholeTile extends Tile {
    private char number;

    public WormholeTile(char number) {
        this.number = number;
        setImageFileName("Wormhole_" + number + ".png");
    }

}
