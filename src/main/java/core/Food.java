package core;

import java.awt.*;
import java.util.List;

import static core.Field.COLUMNS;
import static core.Field.ROWS;

class Food {
    private Point food;
    private boolean needForImage;

    public void generateFood(List<Point> snakeBody) {
        boolean fruitInSnake;
        do {
            food = new Point((int)(Math.random() * ROWS), (int)(Math.random() * COLUMNS));
            fruitInSnake = false;
            for (Point snakeElement : snakeBody) {
                if (snakeElement.getX() == food.x && snakeElement.getY() == food.y) {
                    fruitInSnake = true;
                    break;
                }
            }
        } while (fruitInSnake);
        needForImage = true;
    }

    public boolean getNeedForImage() {
        return needForImage;
    }

    public void setNeedForImage() {
        this.needForImage = false;
    }

    public Point getPoint() {
        return food;
    }
}