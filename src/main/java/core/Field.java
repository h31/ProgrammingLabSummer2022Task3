package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field {
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;

    private final List<Point> snakeBody = new ArrayList<>();
    private final Point snakeHead;
    private final Food food = new Food();
    private boolean gameOver = false;
    private int score = 0;

    public Field() {
        createSnake();
        snakeHead = snakeBody.get(0);
        food.generateFood(snakeBody);
    }

    public void createSnake() {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(COLUMNS / 2, ROWS / 2));
        }
    }

    //Логика движения по клеткам
    public void snakeDoIteration(Direction direction) {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        switch (direction) {
            case RIGHT:
                snakeHead.x++;
                break;
            case LEFT:
                snakeHead.x--;
                break;
            case UP:
                snakeHead.y--;
                break;
            case DOWN:
                snakeHead.y++;
                break;
        }
    }

    //добавление нового элемента в змейку
    public void eatFood() {
        if (snakeHead.getX() == food.getPoint().x && snakeHead.getY() == food.getPoint().y) {
            snakeBody.add(new Point(-1, -1));
            food.generateFood(snakeBody);
            score += 15;
        }
    }

    //выход за границы поля + соприкосновение с хвостом
    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 ||
                snakeHead.x >= ROWS || snakeHead.y >= COLUMNS) {
            gameOver = true;
            return;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() &&
                    snakeHead.y == snakeBody.get(i).getY()) {
                gameOver = true;
                return;
            }
        }
    }

    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    public Point getSnakeHead() {
        return snakeHead;
    }

    public boolean GameIsOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public Food getFood() {
        return food;
    }

    public Point getFoodPoint() {
        return food.getPoint();
    }

    public boolean getNeedForImage() {
        return food.getNeedForImage();
    }

    public void setFalseNeedForImage() {
        food.setNeedForImage();
    }
}