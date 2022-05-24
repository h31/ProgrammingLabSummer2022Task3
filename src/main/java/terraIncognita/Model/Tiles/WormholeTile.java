package terraIncognita.Model.Tiles;

public class WormholeTile extends Tile {
    private final int number;

    public WormholeTile(char number) {
        this.number = Character.getNumericValue(number);
        setImageFileName("Wormhole_" + number + ".png");
    }

    public int getNumber() {
        return number;
    }

}
