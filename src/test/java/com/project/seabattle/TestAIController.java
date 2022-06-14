package com.project.seabattle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAIController {

    @Test
    void TestAttackAI() {
        Field field = new Field(true);
        AIController aiController = new AIController(field);
        aiController.isTest = true;

        aiController.startAttack();
        assertTrue(field.field.containsValue(Cell.MISS));

        field.clear();
        field.fillCell(1, 5, Cell.SHIP, false);
        field.fillCell(1, 6, Cell.SHIP, false);
        field.fillCell(1, 7, Cell.SHIP, false);

        aiController.targetedAttack(1, 7);
        assertEquals(15, field.field.size());

        field.clear();
        field.fillCell(9, 3, Cell.SHIP, false);
        field.fillCell(8, 3, Cell.SHIP, false);
        field.fillCell(1, 1, Cell.SHIP, false);

        aiController.targetedAttack(9, 3);
        assertEquals(10, field.field.size());

        aiController.targetedAttack(1, 1);
        assertEquals(19, field.field.size());
    }
}
