package core;

import java.awt.*;
import java.util.List;

public class Food {
    private Point food;
    private boolean fruitFlag;
    private boolean fruitInSnake;

    public void generateFood(List<Point> snakeBody) {

        do {
            food = new Point((int)(Math.random() * 20), (int)(Math.random() * 20));
            fruitInSnake = false;
            for (Point snakeElement: snakeBody) {
                if (snakeElement.getX() == food.x && snakeElement.getY() == food.y) {
                    fruitInSnake = true;
                    break;
                }
            }
            if (!fruitInSnake) {
                fruitFlag = true;
                return;
            }
        } while (true);
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