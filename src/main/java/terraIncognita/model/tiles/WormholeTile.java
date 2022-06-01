package terraIncognita.model.tiles;

public class WormholeTile extends Tile {
    private final int number;

    private final static WormholeTile INSTANCE_0 = new WormholeTile(0);
    private final static WormholeTile INSTANCE_1 = new WormholeTile(1);
    private final static WormholeTile INSTANCE_2 = new WormholeTile(2);
    private final static WormholeTile INSTANCE_3 = new WormholeTile(3);
    private final static WormholeTile INSTANCE_4 = new WormholeTile(4);
    private final static WormholeTile INSTANCE_5 = new WormholeTile(5);
    private final static WormholeTile INSTANCE_6 = new WormholeTile(6);
    private final static WormholeTile INSTANCE_7 = new WormholeTile(7);
    private final static WormholeTile INSTANCE_8 = new WormholeTile(8);
    private final static WormholeTile INSTANCE_9 = new WormholeTile(9);

    private WormholeTile(int number) {
        this.number = number;
        setImageFileName("Wormhole_" + number + ".png");
    }

    public static WormholeTile getInstance(char number) {
        return switch (number) {
            case '0' -> INSTANCE_0;
            case '1' -> INSTANCE_1;
            case '2' -> INSTANCE_2;
            case '3' -> INSTANCE_3;
            case '4' -> INSTANCE_4;
            case '5' -> INSTANCE_5;
            case '6' -> INSTANCE_6;
            case '7' -> INSTANCE_7;
            case '8' -> INSTANCE_8;
            case '9' -> INSTANCE_9;
            default -> throw new IllegalArgumentException(number + " is out of bounds [0;9]");
        };
    }

    public int getNumber() {
        return number;
    }

}
