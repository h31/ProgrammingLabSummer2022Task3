package core;

import ui.SnakeGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int width = 800;
    private final int height = 800;
    private final int rows = 20;
    private final int columns = rows;
    private final int squareSize = width / rows;
    private boolean gameOver = false;
    public int score = 0;
    private final List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;

    public void createSnake() {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(columns / 2, rows / 2));
        }
    }

    //Логика движения по клеткам
    public void snakeDoIteration() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        switch (SnakeGame.direction) {
            case right:
                snakeHead.x++;
                break;
            case left:
                snakeHead.x--;
                break;
            case up:
                snakeHead.y--;
                break;
            case down:
                snakeHead.y++;
                break;
        }
    }

    //выход за границы поля + соприкосновение с хвостом
    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 ||
                snakeHead.x * squareSize >= width || snakeHead.y * squareSize >= height) {
            gameOver = true;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() &&
                    snakeHead.y == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    public Point getSnakeHead() {
        return snakeHead;
    }

    public void setSnakeHead(Point snakeHead) {
        this.snakeHead = snakeHead;
    }

    public int getFieldWidth() {
        return width;
    }

    public int getFieldHeight() {
        return height;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSqSize() {
        return squareSize;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }
}