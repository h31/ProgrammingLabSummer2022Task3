package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SnakeGame extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int squareSize = WIDTH / ROWS;
    private static final String[] fruitImages = new String[]{"frog.png", "rat.png",
            "shrew.png", "grasshopper.png"};

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext graphicsContext;
    private final List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;
    boolean pressFlag = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();

        //управление
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (currentDirection != LEFT) {
                    currentDirection = RIGHT;
                }
                pressFlag = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (currentDirection != RIGHT) {
                    currentDirection = LEFT;
                }
                pressFlag = true;
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (currentDirection != DOWN) {
                    currentDirection = UP;
                }
                pressFlag = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (currentDirection != UP) {
                    currentDirection = DOWN;
                }
                pressFlag = true;
            }
        });

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(COLUMNS / 2, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> run(graphicsContext)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext graphicsContext) {
        if (gameOver) {
            graphicsContext.setFill(Color.web("F54C00"));
            graphicsContext.setFont(new Font("ShowCard Gothic", 70));
            graphicsContext.fillText("Game Over", 200, 400);
            return;
        }

        drawBackground(graphicsContext);
        drawFood(graphicsContext);
        drawSnake(graphicsContext);
        drawScore();

        //движение по клеткам
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        switch (currentDirection) {
            case RIGHT -> snakeHead.x++;
            case LEFT -> snakeHead.x--;
            case UP -> snakeHead.y--;
            case DOWN -> snakeHead.y++;
        }

        gameOver();
        eatFood();

      if (pressFlag) { //отображение пояснения к управлению
            gameOver();
        } else drawRules();

    }

    //пояснения к управлению
    public void drawRules() {
        graphicsContext.setFill(Color.web("EAFFBF"));
        graphicsContext.setFont(new Font("ShowCard Gothic", 35));
        graphicsContext.fillText("""
                -Start & Move:
                W,A,S,D  or ←↑↓→""", 470, 150);
    }

    private void drawBackground(GraphicsContext graphicsContext) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    graphicsContext.setFill(Color.web("AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("A2D149"));
                }
                graphicsContext.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }

    private void generateFood() {
        start:
        while (true) {
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }
            foodImage = new Image(fruitImages[(int) (Math.random() * fruitImages.length)]);
            break;
        }
    }

    private void drawFood(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(foodImage, foodX * squareSize, foodY * squareSize, squareSize, squareSize);
    }

    //голова и тело змейки
    private void drawSnake(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.web("#502611"));
        graphicsContext.fillRoundRect(snakeHead.getX() * squareSize,
                snakeHead.getY() * squareSize, squareSize - 1, squareSize - 1, 20, 30);

        for (int i = 1; i < snakeBody.size(); i++) {
            if (i % 2 == 0) {
                graphicsContext.setFill(Color.web("#2d130b"));
            } else graphicsContext.setFill(Color.web("#2d130b"));
            graphicsContext.fillRoundRect(snakeBody.get(i).getX() * squareSize,
                    snakeBody.get(i).getY() * squareSize,
                    squareSize - 1, squareSize - 1, 10, 10);
        }

    }

    //выход за границы поля и соприкосновение с хвостом
    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * squareSize >= WIDTH || snakeHead.y * squareSize >= HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    //увеличение змейки
    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 15;
        }
    }



    //счётчик баллов
    private void drawScore() {
        graphicsContext.setFill(Color.web("#ff6709"));
        graphicsContext.setFont(new Font("ShowCard Gothic", 40));
        graphicsContext.fillText("Score: " + score, 300, 75);
    }


    public static void main(String[] args) {
        launch(args);
    }
}