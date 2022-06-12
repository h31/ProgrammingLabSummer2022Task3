package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import java.util.LinkedList;

public class Ship extends Rectangle {
    private boolean position = true;
    private final Rectangle rect;
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

    public Rectangle getRect(){
        return rect;
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
        this.size = size;
        rect = new Rectangle(x, y,25,25 + 25 * (size - 1));
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.BLACK);
    }

    public static void mouseClickedRight(Ship ship, int[][] table, int[][] tableAroundShip) {
        if (ship.rect.getTranslateX() != 0 || ship.rect.getTranslateY() != 0) {
            ship.rect.setTranslateX(0);
            ship.rect.setTranslateY(0);
            for (int i = 0; i <= ship.listX.size() - 1; i++) {
                for (int j = 0; j <= ship.listY.size() - 1; j++) {
                    table[ship.listX.get(i)][ship.listY.get(j)] = 0;
                    if (tableAroundShip[ship.aroundShipX.get(i)][ship.listY.get(j)] > 0) {
                        tableAroundShip[ship.aroundShipY.get(i)][ship.listY.get(j)] -= 1;
                    }
                }
            }
            ship.listX.clear();
            ship.listY.clear();
            ship.aroundShipX.clear();
            ship.aroundShipY.clear();
        } else {
            ship.position = !ship.position;
            Rotate rotate = new Rotate();
            if (!ship.position) {
                rotate.setPivotX(ship.rect.getX());
                rotate.setPivotY(ship.rect.getY() + 25);
                rotate.setAngle(270);

            } else {
                rotate.setPivotX(ship.rect.getX());
                rotate.setPivotY(ship.rect.getY() + 25);
                rotate.setAngle(90);
            }
            ship.rect.getTransforms().add(rotate);

        }
    }

    public static void mouseDragged(Ship ship, int[][] table, int[][] tableAroundShip) {
        for (int i = 0; i <= ship.listX.size() - 1; i++) {
            for (int j = 0; j <= ship.listY.size() - 1; j++) {
                table[ship.listX.get(i)][ship.listY.get(j)] = 0;
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



    public static void mouseReleased(Ship ship, int[][] table, int[][] tableAroundShip) {
        if (ship.position &&
                (ship.rect.getX() + ship.rect.getTranslateX() < 25 ||
                        ship.rect.getX() + ship.rect.getTranslateX() + 25 > 275 ||
                        ship.rect.getY() + ship.rect.getTranslateY() < 25 ||
                        ship.rect.getY() + ship.rect.getTranslateY() + (ship.size) * 25 > 275)) {
            ship.rect.setTranslateX(0);
            ship.rect.setTranslateY(0);
        } else if (!ship.position &&
                (ship.rect.getX() + ship.rect.getTranslateX() - 25 < 25 ||
                        ship.rect.getX() + ship.rect.getTranslateX() + (ship.size - 1) * 25 > 275 ||
                        ship.rect.getY() + ship.rect.getTranslateY() < 25 ||
                        ship.rect.getY() + ship.rect.getTranslateY() + 25 > 275)) {
            ship.rect.setTranslateX(0);
            ship.rect.setTranslateY(0);
        } else {

            if (ship.position) {
                int firstX10 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 - 2);
                int firstX20 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 - 1);
                int firstX30;
                if (firstX10 >= 0) firstX30 = firstX10;
                else firstX30 = firstX20;

                int secondX10 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25);
                int secondX20 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 - 1);
                int secondX30;
                if (secondX10 <= 9) secondX30 = secondX10;
                else secondX30 = secondX20;

                int firstY10 = (int) ((ship.rect.getY() + ship.rect.getTranslateY()) / 25 - 2);
                int firstY20 = (int) ((ship.rect.getY() + ship.rect.getTranslateY()) / 25 - 1);
                int firstY30;
                if (firstY10 >= 0) firstY30 = firstY10;
                else firstY30 = firstY20;

                int secondY10 = (int)((ship.rect.getY() + ship.rect.getTranslateY()) / 25 + ship.size - 1);
                int secondY20 = (int)((ship.rect.getY() + ship.rect.getTranslateY()) / 25 + ship.size - 2);
                int secondY30;
                if (secondY10 <= 9) secondY30 = secondY10;
                else secondY30 = secondY20;
                cycle:
                for (int i = firstX30; i <= secondX30; i++) {
                    for (int j = firstY30; j <= secondY30; j++) {

                        if (i == firstX10 || i == secondX10 || j == firstY10 || j == secondY10) {
                            if (table[i][j] >= 1) {
                                ship.rect.setTranslateX(0);
                                ship.rect.setTranslateY(0);
                                break cycle;
                            } else {
                                ship.aroundShipX.add(i);
                                ship.aroundShipY.add(j);
                                tableAroundShip[i][j] += 1;
                            }
                        }
                        else {
                            if (table[i][j] == 1) {
                                ship.rect.setTranslateX(0);
                                ship.rect.setTranslateY(0);
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


            if (!ship.position) {
                int firstX10 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 - 3);
                int firstX20 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 - 2);
                int firstX30;
                if (firstX10 >= 0) firstX30 = firstX10;
                else firstX30 = firstX20;

                int secondX10 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 + ship.size - 2);
                int secondX20 = (int) ((ship.rect.getX() + ship.rect.getTranslateX()) / 25 + ship.size - 3);
                int secondX30;
                if (secondX10 <= 9) secondX30 = secondX10;
                else secondX30 = secondX20;

                int firstY10 = (int) ((ship.rect.getY() + ship.rect.getTranslateY()) / 25 - 2);
                int firstY20 = (int) ((ship.rect.getY() + ship.rect.getTranslateY()) / 25 - 1);
                int firstY30;
                if (firstY10 >= 0) firstY30 = firstY10;
                else firstY30 = firstY20;

                int secondY10 = (int)((ship.rect.getY() + ship.rect.getTranslateY()) / 25);
                int secondY20 = (int)((ship.rect.getY() + ship.rect.getTranslateY()) / 25 - 1);
                int secondY30;
                if (secondY10 <= 9) secondY30 = secondY10;
                else secondY30 = secondY20;
                cycle:
                for (int i = firstX30; i <= secondX30; i++) {
                    for (int j = firstY30; j <= secondY30; j++) {
                        if (i == firstX10 || i == secondX10 || j == firstY10 || j == secondY10) {
                            if (table[i][j] >= 1) {
                                ship.rect.setTranslateX(0);
                                ship.rect.setTranslateY(0);
                                break cycle;
                            } else {
                                ship.aroundShipX.add(i);
                                ship.aroundShipY.add(j);
                                tableAroundShip[i][j] += 1;
                            }
                        }
                        else {
                            if (table[i][j] == 1) {
                                ship.rect.setTranslateX(0);
                                ship.rect.setTranslateY(0);
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
    }
}
