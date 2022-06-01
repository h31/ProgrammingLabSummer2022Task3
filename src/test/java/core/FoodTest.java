package core;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {
    Field field = new Field();
    @Test
    void generateFoodTest() {
        int foodX = field.getFood().getX();
        int foodY = field.getFood().getY();
        field.getSnakeBody().add(new Point(foodX, foodY));
        field.getFood().generateFood(field.getSnakeBody());
        assertFalse(field.getFood().getFruitInSnake());
        assertTrue(field.getFruitFlag());
    }
}