package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SurroundShip extends Rectangle {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    public SurroundShip(Ship ship) {
        if (ship.position) {
            a = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 25,ship.a.getY() + ship.a.getTranslateY() - 25,
                    75,25);
            a.setFill(Color.LIGHTGRAY);
            b = new Rectangle(ship.a.getX() + ship.a.getTranslateX() + 25, ship.a.getY() + ship.a.getTranslateY(),
                25, 25 * ship.size1);
            b.setFill(Color.LIGHTGRAY);
            c = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 25, ship.a.getY() + ship.a.getTranslateY() + (ship.size1) * 25,
                    75,25);
            c.setFill(Color.LIGHTGRAY);
            d = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 25, ship.a.getY() + ship.a.getTranslateY(),
                    25, 25 * ship.size1);
            d.setFill(Color.LIGHTGRAY);
        }
        if (!ship.position) {
            a = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 50, ship.a.getY() + ship.a.getTranslateY() - 25,
                    25,75);
            a.setFill(Color.LIGHTGRAY);
            b = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 25, ship.a.getY() + ship.a.getTranslateY() - 25,
                    25 * ship.size1, 25);
            b.setFill(Color.LIGHTGRAY);
            c = new Rectangle(ship.a.getX() + ship.a.getTranslateX() - 25, ship.a.getY() + ship.a.getTranslateY() + 25,
                    25 * ship.size1, 25);
            c.setFill(Color.LIGHTGRAY);
            d = new Rectangle(ship.a.getX() + ship.a.getTranslateX() +(ship.size1 - 1)*25, ship.a.getY() + ship.a.getTranslateY() - 25,
                    25,75);
            d.setFill(Color.LIGHTGRAY);
        }
    }
}
