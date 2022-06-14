package com.project.seabattle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestField {

    @Test
    void testFieldPlacement() {
        Field field = new Field(true);
        field.fillCell(1, 9, Cell.SHIP, false);
        assertTrue(field.field.containsKey(new Coordinate(1, 9)));

        field.clear();
        assertTrue(field.field.isEmpty());
    }

    @Test
    void testFieldAttackCell() {
        Field field = new Field(true);
        field.fillCell(0, 1, Cell.SHIP, false);
        field.fillCell(0, 2, Cell.SHIP, false);

        assertTrue(field.isAllowFire(0, 0));
        assertFalse(field.attackCell(0, 0));
        assertFalse(field.isAllowFire(0, 0));

        assertTrue(field.attackCell(0, 1));
        assertFalse(field.isKill(0, 1));
        assertTrue(field.attackCell(0, 2));
        assertTrue(field.isKill(0, 1));

        assertFalse(field.checkWin());
    }

    @Test
    void testFieldAllowFireCellsList() {
        Field field = new Field(true);
        field.fillCell(5, 1, Cell.SHIP, false);
        field.fillCell(5, 2, Cell.SHIP, false);

        assertEquals(100, field.allowFireCells().size());

        field.attackCell(0, 0);
        field.attackCell(5, 1); // don't kill ship
        assertEquals(98, field.allowFireCells().size());

        field.attackCell(5, 2); // kill ship
        assertEquals(87, field.allowFireCells().size());
    }
}
