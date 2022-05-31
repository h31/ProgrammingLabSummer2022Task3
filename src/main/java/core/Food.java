package core;

import java.awt.*;

public class Food {
    private Point food;
    private boolean newFruitFlag;

    public void generateFood(Field field) {
        while (true) {
            food = new Point((int)(Math.random() * field.getRows()), (int)(Math.random() * field.getColumns()));
            boolean fruitInSnake = false;
            for (Point snakeElement: field.getSnakeBody()) {
                if (snakeElement.getX() == food.x && snakeElement.getY() == food.y) {
                    fruitInSnake = true;
                }
            }
            if (!fruitInSnake) {
                newFruitFlag = true;
                return;
            }
        }
    }

    //добавление нового элемента в змейку
    public void eatFood(Field field) {
        if (field.getSnakeHead().getX() == food.x && field.getSnakeHead().getY() == food.y) {
            field.getSnakeBody().add(new Point(-1, -1));
            generateFood(field);
            newFruitFlag = true;
            field.score += 5;
        }
    }

    public boolean getNewFruitFlag() {
        return newFruitFlag;
    }

    public void setNewFruitFlag(boolean newFruitFlag) {
        this.newFruitFlag = newFruitFlag;
    }

    public int getX() {
        return food.x;
    }

    public int getY() {
        return food.y;
    }

}