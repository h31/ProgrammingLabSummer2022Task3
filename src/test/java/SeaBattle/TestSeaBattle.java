package SeaBattle;


import javafx.scene.transform.Rotate;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestSeaBattle {
    int[][] table = new int[10][10];
    int[][] tableAroundShip = new int[10][10];

    @Test
    public void testMouseClickedRight1(){
        Ship ship = new Ship(325, 25, 3);
        ship.setTranslateX(300);
        Ship.mouseClickedRight(ship, table, tableAroundShip);
        assertEquals(0, ship.getTranslateX(), 0);
    }

    @Test
    public void testMouseClickedRight2(){
        Ship ship1 = new Ship(325, 25, 3);
        ship1.setPosition(true);
        Ship ship = new Ship(325, 25, 3);
        Rotate rotate = new Rotate();
        rotate.setPivotX(ship.getX());
        rotate.setPivotY(ship.getY() + 25);
        rotate.setAngle(270);
        ship.getTransforms().add(rotate);
        Ship.mouseClickedRight(ship1, table, tableAroundShip);
        assertEquals(ship.getTransforms().toString(), ship1.getTransforms().toString());
    }

    @Test
    public void testMouseClickedRight3(){
        Ship ship1 = new Ship(325, 25, 3);
        ship1.setPosition(false);
        Ship ship = new Ship(325, 25, 3);
        Rotate rotate = new Rotate();
        rotate.setPivotX(ship.getX());
        rotate.setPivotY(ship.getY() + 25);
        rotate.setAngle(90);
        ship.getTransforms().add(rotate);
        Ship.mouseClickedRight(ship1, table, tableAroundShip);
        assertEquals(ship.getTransforms().toString(), ship1.getTransforms().toString());
    }

    @Test
    public void testMouseDragged1(){
        Ship ship = new Ship(25, 25, 2);
        LinkedList<Integer> listX = new LinkedList<>();
        listX.add(0);
        listX.add(0);
        LinkedList<Integer> listY = new LinkedList<>();
        listY.add(0);
        listY.add(1);
        ship.setListX(listX);
        ship.setListY(listY);
        ship.setAroundShipX(listX);
        ship.setAroundShipY(listY);
        int[][] table1 = new int[10][10];
        table[0][0] = 1;
        table[0][1] = 1;
        Ship.mouseDragged(ship, table, tableAroundShip);
        assertArrayEquals(table, table1);
    }

    @Test
    public void testMouseReleased1(){
        Ship ship = new Ship(325, 25, 2);
        ship.setPosition(true);
        ship.setTranslateX(-325);
        Ship.mouseReleased(ship, table, tableAroundShip);
        assertEquals(0, ship.getTranslateX(), 0);
    }

    @Test
    public void testMouseReleased2(){
        Ship ship = new Ship(325, 25, 2);
        ship.setPosition(false);
        ship.setTranslateY(280);
        Ship.mouseReleased(ship, table, tableAroundShip);
        assertEquals(0, ship.getTranslateY(), 0);
    }

    @Test
    public void testMouseDragged2(){
        Ship ship = new Ship(25, 25, 2);
        ship.setPosition(true);
        ship.setListX(new LinkedList<>());
        ship.setListY(new LinkedList<>());
        ship.setAroundShipX(new LinkedList<>());
        ship.setAroundShipY(new LinkedList<>());
        int[][] table1 = new int[10][10];
        table1[0][0] = 1;
        table1[0][1] = 1;
        Ship.mouseReleased(ship, table, tableAroundShip);
        assertArrayEquals(table, table1);
    }

    @Test
    public void testMouseDragged3(){
        Ship ship = new Ship(50, 25, 2);
        ship.setPosition(false);
        ship.setListX(new LinkedList<>());
        ship.setListY(new LinkedList<>());
        ship.setAroundShipX(new LinkedList<>());
        ship.setAroundShipY(new LinkedList<>());
        int[][] table1 = new int[10][10];
        table1[0][0] = 1;
        table1[1][0] = 1;
        Ship.mouseReleased(ship, table, tableAroundShip);
        assertArrayEquals(table, table1);
    }

    @Test
    public void testMouseDragged4(){
        Ship ship = new Ship(325, 25, 2);
        ship.setTranslateX(-300);
        ship.setPosition(true);
        ship.setListX(new LinkedList<>());
        ship.setListY(new LinkedList<>());
        ship.setAroundShipX(new LinkedList<>());
        ship.setAroundShipY(new LinkedList<>());
        int[][] table1 = new int[10][10];
        table1[0][0] = 1;
        Ship.mouseReleased(ship, table1, tableAroundShip);
        assertEquals(0, ship.getTranslateY(), 0);
    }

    @Test
    public void testMouseDragged5(){
        Ship ship = new Ship(350, 25, 2);
        ship.setTranslateX(-300);
        ship.setPosition(false);
        ship.setListX(new LinkedList<>());
        ship.setListY(new LinkedList<>());
        ship.setAroundShipX(new LinkedList<>());
        ship.setAroundShipY(new LinkedList<>());
        int[][] table1 = new int[10][10];
        table1[0][0] = 1;
        Ship.mouseReleased(ship, table1, tableAroundShip);
        assertEquals(0, ship.getTranslateY(), 0);
    }
}
