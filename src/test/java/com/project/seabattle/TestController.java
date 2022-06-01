package com.project.seabattle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestController {

    @Test
    void testControllerPlacement() {
        Controller controller = new Controller();
        controller.isTest = true;
        controller.newGame();
        controller.placeShip(new ShipType(true, 1, 1), 1, 7);
        assertTrue(controller.fieldPlayer.field.containsKey(new Coordinate(1, 7)));
        assertFalse(controller.fieldPlayer.field.containsKey(new Coordinate(1, 9)));
    }

    @Test
    void testControllerQuickPlacement() {
        Controller controller = new Controller();
        controller.isTest = true;
        controller.newGame();
        controller.quickPlacement();
        assertEquals(0, controller.shipList.get(3).count);
        assertEquals(0, controller.shipList.get(2).count);
        assertEquals(0, controller.shipList.get(1).count);
        assertEquals(0, controller.shipList.get(0).count);

        controller.clearField();
        assertTrue(controller.fieldPlayer.field.isEmpty());
    }

}
