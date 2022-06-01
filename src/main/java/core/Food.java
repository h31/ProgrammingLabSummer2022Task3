package core;

import java.awt.*;
import java.util.List;

import static core.Field.COLUMNS;
import static core.Field.ROWS;

public class Food {
    private Point food;
    private boolean fruitFlag;
    private boolean fruitInSnake;

    public void generateFood(List<Point> snakeBody) {

        do {
            food = new Point((int) (Math.random() * ROWS), (int) (Math.random() * COLUMNS));
            fruitInSnake = false;
            for (Point snakeElement : snakeBody) {
                if (snakeElement.getX() == food.x && snakeElement.getY() == food.y) {
                    fruitInSnake = true;
                    break;
                }
            }
        } while (fruitInSnake);
        fruitFlag = true;
    }

    public boolean getFruitFlag() {
        return fruitFlag;
    }

    public void setFruitFlag(boolean flag) {
        this.fruitFlag = flag;
    }

    public boolean getFruitInSnake() {
        return fruitInSnake;
    }

    public int getX() {
        return food.x;
    }

    public int getY() {
        return food.y;
    }
}