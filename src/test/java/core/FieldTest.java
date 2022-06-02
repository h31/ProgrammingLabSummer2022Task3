package core;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    Field field = new Field();

    @Test
    void createSnakeTest() {
        assertEquals(3, field.getSnakeBody().size());
    }

    @Test
    void snakeDoIterationTest() {
        field.getSnakeHead().x = 11;
        field.getSnakeHead().y = 11;

        field.snakeDoIteration(Direction.UP);
        assertEquals(10, field.getSnakeHead().y);
        assertEquals(field.getSnakeBody().get(2).x, field.getSnakeBody().get(1).x - 1);
        assertEquals(field.getSnakeBody().get(2).y, field.getSnakeBody().get(1).y - 1);

        field.snakeDoIteration(Direction.DOWN);
        assertEquals(11, field.getSnakeHead().y);

        field.snakeDoIteration(Direction.LEFT);
        assertEquals(10, field.getSnakeHead().x);

        field.snakeDoIteration(Direction.RIGHT);
        assertEquals(11, field.getSnakeHead().x);

    }

    @Test
    void eatFoodTest() {
        field.getFood().generateFood(field.getSnakeBody());
        field.getSnakeHead().x = field.getFoodPoint().x;
        field.getSnakeHead().y = field.getFoodPoint().y;
        field.eatFood();

        assertEquals(4, field.getSnakeBody().size());
        assertEquals(15, field.getScore());
        assertNotEquals(field.getSnakeHead().x, field.getFoodPoint().x);//координата меняется рандомно -> может упасть
        assertNotEquals(field.getSnakeHead().y, field.getFoodPoint().y);
    }

    @Test
    void gameOverTest() {
        //в точке 0,0 змейка существует
        field.getSnakeHead().x = 0;
        field.getSnakeHead().y = 0;
        field.gameOver();
        assertFalse(field.GameIsOver());

        //4 условия метода gameOver
        field.getSnakeHead().y = -1;
        field.gameOver();
        assertTrue(field.GameIsOver());
        field.getSnakeHead().y = 0;

        field.getSnakeHead().x = -1;
        field.gameOver();
        assertTrue(field.GameIsOver());
        field.getSnakeHead().x = 0;

        field.getSnakeHead().x = 20;
        field.gameOver();
        assertTrue(field.GameIsOver());
        field.getSnakeHead().y = 0;

        field.getSnakeHead().y = 20;
        field.gameOver();
        assertTrue(field.GameIsOver());
        field.getSnakeHead().x = 0;

        //столкновение с телом
        field.getSnakeHead().x = 5;
        field.getSnakeHead().y = 1;
        field.getSnakeBody().add(new Point(5,1));
        field.gameOver();
        assertTrue(field.GameIsOver());
    }
}