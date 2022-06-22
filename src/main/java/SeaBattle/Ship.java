package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import java.util.LinkedList;

public class Ship extends Rectangle {
    private boolean position = true;
    private final int size;
    private LinkedList<Integer> listX = new LinkedList<>();
    private LinkedList<Integer> listY = new LinkedList<>();
    private LinkedList<Integer> aroundShipX = new LinkedList<>();
    private LinkedList<Integer> aroundShipY = new LinkedList<>();

    public void setAroundShipY(LinkedList<Integer> aroundShipY) {
        this.aroundShipY = aroundShipY;
    }

    public void setAroundShipX(LinkedList<Integer> aroundShipX) {
        this.aroundShipX = aroundShipX;
    }

    public void setListX(LinkedList<Integer> listX) {
        this.listX = listX;
    }

    public void setListY(LinkedList<Integer> listY) {
        this.listY = listY;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }


    public Ship(int x, int y, int size) {
        super(x, y,SeaBattle.SIZE,SeaBattle.SIZE + SeaBattle.SIZE * (size - 1));
        this.size = size;
        setFill(Color.BLUE);
        setStroke(Color.BLACK);
    }

    // Обработка переворота корабля
    public static void mouseClickedRight(Ship ship, int[][] tableShipsPosition, int[][] tableAroundShip) {
        if (ship.size != 1) {
            if (ship.getTranslateX() != 0 || ship.getTranslateY() != 0) {
                ship.setTranslateX(0);
                ship.setTranslateY(0);
                mouseDragged(ship, tableShipsPosition, tableAroundShip);
            } else {
                ship.position = !ship.position;
                Rotate rotate = new Rotate();
                if (!ship.position) {
                    rotate.setPivotX(ship.getX());
                    rotate.setPivotY(ship.getY() + SeaBattle.SIZE);
                    rotate.setAngle(270);

                } else {
                    rotate.setPivotX(ship.getX());
                    rotate.setPivotY(ship.getY() + SeaBattle.SIZE);
                    rotate.setAngle(90);
                }
                ship.getTransforms().add(rotate);

            }
        }
    }

    // Очистка местонахождения кораблей
    public static void mouseDragged(Ship ship, int[][] tableShipsPosition, int[][] tableAroundShip) {
        for (int i = 0; i <= ship.listX.size() - 1; i++) {
            for (int j = 0; j <= ship.listY.size() - 1; j++) {
                tableShipsPosition[ship.listX.get(i)][ship.listY.get(j)] = 0;
                if (tableAroundShip[ship.aroundShipX.get(i)][ship.listY.get(j)] > 0) {
                    tableAroundShip[ship.aroundShipY.get(i)][ship.listY.get(j)] -= 1;
                }
            }
        }
        ship.listX.clear();
        ship.listY.clear();
        ship.aroundShipX.clear();
        ship.aroundShipY.clear();
    }

    public static void mouseReleased(Ship ship, int[][] tableShipsPosition, int[][] tableAroundShip) {
        // Возврат кораблей на начальную позицию при выходе его за границу
        if (ship.position &&
                (ship.getX() + ship.getTranslateX() < SeaBattle.SIZE ||
                        ship.getX() + ship.getTranslateX() + SeaBattle.SIZE > SeaBattle.SIZE + SeaBattle.SIZE * 10 ||
                        ship.getY() + ship.getTranslateY() < SeaBattle.SIZE ||
                        ship.getY() + ship.getTranslateY() + (ship.size) * SeaBattle.SIZE > SeaBattle.SIZE + SeaBattle.SIZE * 10)) {
            ship.setTranslateX(0);
            ship.setTranslateY(0);
        } else if (!ship.position &&
                (ship.getX() + ship.getTranslateX() - SeaBattle.SIZE < SeaBattle.SIZE ||
                        ship.getX() + ship.getTranslateX() + (ship.size - 1) * SeaBattle.SIZE > SeaBattle.SIZE + SeaBattle.SIZE * 10 ||
                        ship.getY() + ship.getTranslateY() < SeaBattle.SIZE ||
                        ship.getY() + ship.getTranslateY() + SeaBattle.SIZE > SeaBattle.SIZE + SeaBattle.SIZE * 10)) {
            ship.setTranslateX(0);
            ship.setTranslateY(0);
        } else {

            if (ship.position) {
                int firstX1 = (int) ((ship.getX() + ship.getTranslateX()) / SeaBattle.SIZE - 2);
                int firstX = firstX1;
                if (firstX1 < 0) firstX = firstX1 + 1;

                int secondX1 = (int) ((ship.getX() + ship.getTranslateX()) / SeaBattle.SIZE);
                int secondX = secondX1;
                if (secondX1 > 9) secondX = secondX1 - 1;

                int firstY1 = (int) ((ship.getY() + ship.getTranslateY()) / SeaBattle.SIZE - 2);
                int firstY = firstY1;
                if (firstY1 < 0) firstY = firstY1 + 1;

                int secondY1 = (int)((ship.getY() + ship.getTranslateY()) / SeaBattle.SIZE + ship.size - 1);
                int secondY = secondY1;
                if (secondY1 > 9) secondY = secondY1 - 1;
                recordPositionsOfShips(ship, tableShipsPosition, tableAroundShip, firstX1, secondX1, firstY1, secondY1,
                firstX, secondX, firstY, secondY);
            }

            if (!ship.position) {
                int firstX1 = (int) ((ship.getX() + ship.getTranslateX()) / SeaBattle.SIZE - 3);
                int firstX = firstX1;
                if (firstX1 < 0) firstX = firstX1 + 1;

                int secondX1 = (int) ((ship.getX() + ship.getTranslateX()) / SeaBattle.SIZE + ship.size - 2);
                int secondX = secondX1;
                if (secondX1 > 9) secondX = secondX1 - 1;

                int firstY1 = (int) ((ship.getY() + ship.getTranslateY()) / SeaBattle.SIZE - 2);
                int firstY = firstY1;
                if (firstY1 < 0) firstY = firstY1 + 1;

                int secondY1 = (int)((ship.getY() + ship.getTranslateY()) / SeaBattle.SIZE);
                int secondY = secondY1;
                if (secondY1 > 9) secondY = secondY1 - 1;

                recordPositionsOfShips(ship, tableShipsPosition, tableAroundShip, firstX1, secondX1, firstY1, secondY1,
                        firstX, secondX, firstY, secondY);
            }
        }
    }

    // Возврат при надвижении одного корабля на другой и запись их позиций при установке
    private static void recordPositionsOfShips(Ship ship, int[][] table, int[][] tableAroundShip, int firstX1,
                                               int secondX1, int firstY1, int secondY1, int firstX, int secondX,
                                               int firstY, int secondY) {
        cycle:
        for (int i = firstX; i <= secondX; i++) {
            for (int j = firstY; j <= secondY; j++) {
                if (i == firstX1 || i == secondX1 || j == firstY1 || j == secondY1) {
                    if (table[i][j] >= 1) {
                        ship.setTranslateX(0);
                        ship.setTranslateY(0);
                        break cycle;
                    } else {
                        ship.aroundShipX.add(i);
                        ship.aroundShipY.add(j);
                        tableAroundShip[i][j] += 1;
                    }
                }
                else {
                    if (table[i][j] == 1) {
                        ship.setTranslateX(0);
                        ship.setTranslateY(0);
                        break cycle;
                    } else {
                        ship.listX.add(i);
                        ship.listY.add(j);
                        table[i][j] = 1;
                    }
                }
            }
        }
    }
}
