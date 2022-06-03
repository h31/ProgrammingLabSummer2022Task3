package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Ship extends Rectangle {
    int shipPosition = 1;
    boolean position = true;
    Rectangle a;
    int size1;
    int getX;
    int getY;
    LinkedList<Integer> listX = new LinkedList<Integer>();
    LinkedList<Integer> listY = new LinkedList<Integer>();
    LinkedList<Integer> aroundShipX = new LinkedList<Integer>();
    LinkedList<Integer> aroundShipY = new LinkedList<Integer>();
    public Ship(int x, int y, int size) {
        getX = x;
        getY = y;
        size1 = size;
        a = new Rectangle(x, y,25,25 + 25 * (size - 1));
        a.setFill(Color.BLUE);
        a.setStroke(Color.BLACK);
    }
}
