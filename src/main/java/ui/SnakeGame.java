package ui;

import core.Direction;
import core.Field;
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


public class SnakeGame extends Application {

    boolean pressFlag = false;

    private Field field = new Field();
    private final String[] fruitImages = new String[]{"Peach.png", "Apple.png",
            "Lemon.png", "Berry.png"};
    private Image foodImage;

    private final int width = 800;
    private final int height = 800;
    private final int squareSize = width / field.getRows();

    private GraphicsContext graphicsContext;
    Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(250), e -> run(graphicsContext, field)));

    public static Direction direction = Direction.RIGHT;

    @Override
    public void start(Stage primaryStage) {
        field = new Field(); //обновление всех параметров поля и змейки
        startGame(primaryStage, timeLine);
    }

    public void startGame(Stage primaryStage, Timeline timeLine) {
        //создание окна
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        primaryStage.setResizable(false);

        controlGame(scene, primaryStage);
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
    }

    //отрисовка каждую итерацию
    public void run (GraphicsContext graphicsContext, Field field) {
        if (field.GameIsOver()) {
            graphicsContext.setFill(Color.web("D10038"));
            graphicsContext.setFont(new Font("ShowCard Gothic", 70));
            graphicsContext.fillText("Game Over", 200, 400);
            return;
        }
        drawGame();

        if (pressFlag) { //начало движения только после нажатия на move-кнопки
            field.snakeDoIteration(direction);
            field.gameOver();
        } else drawRules();

        field.eatFood();
        if (field.getFruitFlag()) getImage();
        field.setFruitFlag(false);
    }

    //Управление
    public void controlGame(Scene scene, Stage primaryStage) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                pressFlag = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                pressFlag = true;
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                pressFlag = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                pressFlag = true;
            } else if (code == KeyCode.ESCAPE) {
            start(primaryStage);
            }
        });
    }

    public void drawFood(GraphicsContext graphicsContext, Field field) {
        graphicsContext.drawImage(foodImage, field.getFood().getX() * squareSize,
                field.getFood().getY()  * squareSize, squareSize, squareSize);
    }

    //подбор след. картинки после съедания
    public void getImage() {
        foodImage = new Image(fruitImages[(int) (Math.random() * fruitImages.length)]);
    }

    public void drawSnake(GraphicsContext graphicsContext, Field field) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRoundRect(field.getSnakeHead().getX() * squareSize,
                field.getSnakeHead().getY() * squareSize, squareSize - 1, squareSize - 1, 40, 40);

        for (int i = 1; i < field.getSnakeBody().size(); i++) {
            if (i % 2 == 0) {
                graphicsContext.setFill(Color.RED);
            } else graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRoundRect(field.getSnakeBody().get(i).getX() * squareSize,
                    field.getSnakeBody().get(i).getY() * squareSize,
                    squareSize - 1, squareSize - 1, 30, 30);
        }
    }

    public void drawBackground(GraphicsContext graphicsContext, Field field) {
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getColumns(); j++) {
                if ((i + j) % 2 == 0) {
                    graphicsContext.setFill(Color.web("79B350"));
                } else {
                    graphicsContext.setFill(Color.web("67AC36"));
                }
                graphicsContext.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }

    //пояснения к управлению
    public void drawRules() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("ShowCard Gothic", 35));
        graphicsContext.fillText("-Start & Move:\nW,A,S,D  or ←↑↓→\n" +
                "-Restart: Esc", 470, 150);
    }

    //счётчик баллов
    public void drawScore(Field field) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("ShowCard Gothic", 40));
        graphicsContext.fillText("Score  " + field.getScore(), 300, 75);
    }

    public void drawGame() {
        drawBackground(graphicsContext, field);
        drawFood(graphicsContext, field);
        drawSnake(graphicsContext, field);
        drawScore(field);
    }

    public static void main(String[] args) {
        launch(args);
    }

}