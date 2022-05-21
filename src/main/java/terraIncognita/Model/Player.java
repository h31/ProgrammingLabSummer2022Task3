package terraIncognita.Model;

import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Tiles.StartTile;
import terraIncognita.Utils.Point;

public class Player {

    private Point position;
    private Desk desk;
    private String name;

    public Player(String name, Point startPosition, int vDeskSize, int hDeskSize) {
        position = new Point(startPosition);
        desk = new Desk(vDeskSize, hDeskSize, true);
        this.name = name;
        desk.insertTile(new StartTile(), startPosition);
    }

    public String getName() {
        return name;
    }

    public Desk getDesk() {
        return desk;
    }

    public Point getPosition() {
        return position;
    }

    public void move(MovementDirection movementDirection) {
        //Point expectedPosition = movementDirection.move(position);
        position = movementDirection.move(position);
    }

}
