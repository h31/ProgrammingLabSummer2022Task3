package core;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {
    Field field = new Field();
    @Test
    void generateFoodTest() {
        int foodX = field.getFoodPoint().x;
        int foodY = field.getFoodPoint().y;
        field.getSnakeBody().add(new Point(foodX, foodY));
        field.getFood().generateFood(field.getSnakeBody());
        assertTrue(field.getNeedForImage());
    }
}