package ui;

import core.Field;
import core.Food;
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

    private Field field = new Field();
    private final Food food = new Food();
    private final String[] fruitImages = new String[]{"Peach.png", "Apple.png",
            "Lemon.png", "Berry.png"};
    private Image foodImage;

    private GraphicsContext graphicsContext;
    Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(250), e -> run(graphicsContext, field)));

    public enum Direction {
        right, left, up, down
    }
    public static Direction direction = Direction.right;

    @Override
    public void start(Stage primaryStage) {
        field = new Field(); //обновление всех параметров доски и змейки
        startGame(primaryStage, field, timeLine);
    }

    public void startGame(Stage primaryStage, Field field, Timeline timeLine) {
        //создание окна
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(field.getFieldWidth(), field.getFieldHeight());
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        primaryStage.setResizable(false);

        controlGame(scene, primaryStage);

        //анимация движения
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        field.createSnake();
        field.setSnakeHead(field.getSnakeBody().get(0));
        food.generateFood(field);
        if (food.getNewFruitFlag()) getImage();
    }

    //отрисовка каждую итерацию
    public void run (GraphicsContext graphicsContext, Field field) {
        if (field.getGameOver()) {
            graphicsContext.setFill(Color.web("D10038"));
            graphicsContext.setFont(new Font("ShowCard Gothic", 70));
            graphicsContext.fillText("Game Over", 200, 400);
            return;
        }
        drawBackground(graphicsContext, field);
        drawFood(graphicsContext, field);
        drawSnake(graphicsContext, field);
        drawScore(field);
        drawRules();

        field.snakeDoIteration();
        field.gameOver();
        food.eatFood(field);
        if (food.getNewFruitFlag()) getImage();
        food.setNewFruitFlag(false);
    }

    //Управление
    public void controlGame(Scene scene, Stage primaryStage) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (direction != Direction.left) {
                    direction = Direction.right;
                }
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (direction != Direction.right) {
                    direction = Direction.left;
                }
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (direction != Direction.down) {
                    direction = Direction.up;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (direction != Direction.up) {
                    direction = Direction.down;
                }
            } else if (code == KeyCode.ESCAPE) {
            start(primaryStage);
            }
        });
    }

    public void drawFood(GraphicsContext gc, Field field) {
        gc.drawImage(foodImage, food.getX() * field.getSqSize(), food.getY() * field.getSqSize(),
                field.getSqSize(), field.getSqSize());
    }

    //подбор след. картинки после съедания
    public void getImage() {
        foodImage = new Image(fruitImages[(int) (Math.random() * fruitImages.length)]);
    }

    public void drawSnake(GraphicsContext gc, Field field) {
        gc.setFill(Color.web("692DA2"));
        gc.fillRoundRect(field.getSnakeHead().getX() * field.getSqSize(), field.getSnakeHead().getY() * field.getSqSize(),
                field.getSqSize() - 1, field.getSqSize() - 1, 35, 35);

        for (int i = 1; i < field.getSnakeBody().size(); i++) {
            gc.fillRoundRect(field.getSnakeBody().get(i).getX() * field.getSqSize(),
                    field.getSnakeBody().get(i).getY() * field.getSqSize(),
                    field.getSqSize() - 1, field.getSqSize() - 1, 20, 20);
        }
    }

    public void drawBackground(GraphicsContext gc, Field field) {
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getColumns(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("79B350"));
                } else {
                    gc.setFill(Color.web("67AC36"));
                }
                gc.fillRect(i * field.getSqSize(), j * field.getSqSize(), field.getSqSize(), field.getSqSize());
            }
        }
    }

    //пояснения к управлению
    public void drawRules() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("ShowCard Gothic", 35));
        graphicsContext.fillText("Move:\nW,A,S,D  or ←↑↓→\n\n" +
                "Restart: Esc\n\nStart: any button", 450, 45);

    }

    //счётчик баллов
    public void drawScore(Field field) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("ShowCard Gothic", 35));
        graphicsContext.fillText("Score  " + field.getScore(), 20, 45);
    }

    public static void main(String[] args) {
        launch(args);
    }

}