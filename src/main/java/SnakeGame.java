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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SnakeGame extends Application {

    private static final int width = 800;
    private static final int height = width;
    private static final int rows = 20;
    private static final int columns = rows;
    private static final int square_size = width / rows;
    private static final String[] fruitImages = new String[]{"Apple.png", "Peach.png",
            "Lemon.png", "Berry.png"};
    private static final int right = 0;
    private static final int left = 1;
    private static final int up = 2;
    private static final int down = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int direction;
    private int score;
    private final Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(200), e -> run(gc)));

    @Override
    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }

    public void startGame(Stage primaryStage) {
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        primaryStage.setResizable(false);

        //Управление
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (direction != left) {
                    direction = right;
                }
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (direction != right) {
                    direction = left;
                }
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (direction != down) {
                    direction = up;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (direction != up) {
                    direction = down;
                }
            }
            if (code == KeyCode.ESCAPE) {
                restart(primaryStage);
            }
        });
        //Начальная клетка
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, rows/2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();
        //Скорость,анимация движения
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
    }

    public void restart(Stage primaryStage) {
        cleanUp();
        startGame(primaryStage);
    }

    public void cleanUp() {
        snakeBody = new ArrayList<>();
        score = 0;
        gameOver = false;
        direction = right;
    }

    public void run (GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.web("D10038"));
            gc.setFont(new Font("ShowCard Gothic", 70));
            gc.fillText("Game Over", 200, 400);
            return;
        }
        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();
        drawRestart();

        //Логика движения по клеткам
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (direction) {
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
        gameOver();
        eatFood();

    }

    public void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("79B350"));
                } else {
                    gc.setFill(Color.web("67AC36"));
                }
                gc.fillRect(i * square_size, j * square_size, square_size, square_size);
            }
        }
    }

    public void generateFood() {
        start:
        while (true) {
            foodX = (int)(Math.random() * rows);
            foodY = (int)(Math.random() * columns);

            for (Point snake: snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }
            foodImage = new Image(fruitImages[(int)(Math.random() * fruitImages.length)]);
            break;
        }
    }

    public void drawRestart() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("ShowCard Gothic", 35));
        gc.fillText("Restart", 635, 35);

    }

    public void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * square_size, foodY * square_size, square_size, square_size);
    }

    public void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("692DA2"));
        gc.fillRoundRect(snakeHead.getX() * square_size, snakeHead.getY() * square_size,
                square_size - 1, square_size - 1, 35, 35);

        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect(snakeBody.get(i).getX() * square_size, snakeBody.get(i).getY() * square_size,
                    square_size - 1, square_size - 1, 20, 20);
        }
    }

    //выход за границы поля + соприкосновение с хвостом
    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * square_size >= width ||
                snakeHead.y * square_size >= height) {
            gameOver = true;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.y == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }
    //добавление нового элемента в змейку
    public void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 5;
        }
    }
    //счётчик баллов
    public void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("ShowCard Gothic", 35));
        gc.fillText("Score  " + score, 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
