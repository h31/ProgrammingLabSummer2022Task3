package SeaBattle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SeaBattle extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public static Pane group = new Pane();
    private final Scene scene = new Scene(group, 700,400);
    private final int[][] table1 = new int[10][10];
    private final int[][] table2 = new int[10][10];
    private final int[][] aroundShip1 = new int[10][10];
    private final int[][] aroundShip2 = new int[10][10];
    private final int[][] tableOfHits1 = new int[12][12];
    private final int[][] tableOfHits2 = new int[12][12];
    private double oldX, oldY;
    private final Text error = new Text("ВЫ НЕ РАССТАВИЛИ ВСЕ КОРАБЛИ!");
    private int score = 0;
    private int score1 = 0;
    private boolean endGame = true;
    public static final int SIZE = 25;

    @Override
    public void start(Stage primaryStage) {
        group.setStyle("-fx-background-color: #efeaba; ");
        Button beginButton = new Button();
        beginButton.setLayoutX(275);
        beginButton.setLayoutY(160);
        beginButton.setPrefSize(150,50);
        beginButton.setText("Начать игру");
        beginButton.setStyle("-fx-background-color: #3cbd1a; ");
        pressStartButton(beginButton);

        primaryStage.setTitle("МОРСКОЙ БОЙ");
        group.getChildren().add(beginButton);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Объявление кораблей и добавление их на экран
    void declareShips(int[][] table, int[][] aroundShip) {
        error.setX(450);
        error.setY(300);
        error.setStroke(Color.RED);
        error.setStyle("-fx-font: 13 arial;");
        Water water1 = new Water(SIZE, SIZE);
        group.getChildren().add(water1);
        ArrayList<Ship> ships = new ArrayList<>();
        Ship shipFourDecks = new Ship(SIZE * 22, SIZE, 4);
        ships.add(shipFourDecks);
        Ship shipThreeDecks1 = new Ship(SIZE * 18, SIZE, 3);
        ships.add(shipThreeDecks1);
        Ship shipThreeDecks2 = new Ship(SIZE * 18,SIZE * 5, 3);
        ships.add(shipThreeDecks2);
        Ship shipTwoDecks1 = new Ship(SIZE * 15, SIZE,2);
        ships.add(shipTwoDecks1);
        Ship shipTwoDecks2 = new Ship(SIZE * 15,SIZE * 4,2);
        ships.add(shipTwoDecks2);
        Ship shipTwoDecks3 = new Ship(SIZE * 15,SIZE * 7,2);
        ships.add(shipTwoDecks3);
        Ship simpleShip1 = new Ship(SIZE * 12, SIZE, 1);
        ships.add(simpleShip1);
        Ship simpleShip2 = new Ship(SIZE * 12,SIZE * 3, 1);
        ships.add(simpleShip2);
        Ship simpleShip3 = new Ship(SIZE * 12,SIZE * 5, 1);
        ships.add(simpleShip3);
        Ship simpleShip4 = new Ship(SIZE * 12,SIZE * 7, 1);
        ships.add(simpleShip4);

        for (Ship ship: ships) {
            installation(ship, table, aroundShip);
            group.getChildren().add(ship);
        }
    }

    // Обработка нажатия кнопки начала игры, добавление объектов для расстановки кораблей первого игрока на экран
    private void pressStartButton(Button beginButton) {
        beginButton.setOnAction(event -> {
            declareShips(table1, aroundShip1);
            Text participant1 = new Text("Участник1 установите корабли");
            participant1.setX(25);
            participant1.setY(350);
            participant1.setStroke(Color.ROYALBLUE);
            participant1.setStyle("-fx-font: 24 arial;");
            group.getChildren().add(participant1);
            beginButton.setDisable(false);
            beginButton.setVisible(false);
            Button buttonFirstPlayer = new Button();
            buttonFirstPlayer.setLayoutX(500);
            buttonFirstPlayer.setLayoutY(325);
            buttonFirstPlayer.setText("ГОТОВО");
            buttonFirstPlayer.setStyle("-fx-background-color: #3cbd1a; ");
            group.getChildren().add(buttonFirstPlayer);
            buttonFirstPlayer.setOnAction(event1 -> {
                int score = 0;
                for (int i = 0; i <= 9; i++) {
                    for (int j = 0; j <= 9; j++) {
                        score += table1[i][j];
                    }
                }
                if (score != 20) {
                    if (!group.getChildren().contains(error)) group.getChildren().add(error);
                } else {
                    {
                        group.getChildren().clear();
                        group.getChildren().remove(error);
                        buttonFirstPlayer.setDisable(false);
                        buttonFirstPlayer.setVisible(false);
                        pressShipPlacementButton();
                    }
                }
            });
        });
    }

    //Добавление объектов для расстановки кораблей второго игрока на экран
    void pressShipPlacementButton() {
        declareShips(table2, aroundShip2);
        Text participant2 = new Text("Участник2 установите корабли");
        participant2.setX(25);
        participant2.setY(350);
        participant2.setStroke(Color.ROYALBLUE);
        participant2.setStyle("-fx-font: 24 arial;");
        group.getChildren().add(participant2);
        Button buttonSecondPlayer = new Button();
        buttonSecondPlayer.setLayoutX(500);
        buttonSecondPlayer.setLayoutY(325);
        buttonSecondPlayer.setText("ГОТОВО");
        buttonSecondPlayer.setStyle("-fx-background-color: #3cbd1a; ");
        group.getChildren().addAll(buttonSecondPlayer);
        buttonSecondPlayer.setOnAction(event2 -> {
            int score1 = 0;
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    score1 += table2[i][j];
                }
            }
            if (score1 != 20) {
                if (!group.getChildren().contains(error)) group.getChildren().add(error);
            } else {
                {
                    buttonSecondPlayer.setDisable(false);
                    buttonSecondPlayer.setVisible(false);
                    group.getChildren().clear();
                    Water water1 = new Water(SIZE, SIZE);
                    Water water2 = new Water(SIZE * 13, SIZE);
                    group.getChildren().addAll(water1, water2);
                    group.getChildren().remove(error);
                    Text player1 = new Text("Поле участника1");
                    player1.setX(SIZE);
                    player1.setY(SIZE - 7);
                    player1.setStroke(Color.ROYALBLUE);
                    player1.setStyle("-fx-font: 20 arial;");
                    Text player2 = new Text("Поле участника2");
                    player2.setX(SIZE * 13);
                    player2.setY(SIZE - 7);
                    player2.setStroke(Color.ROYALBLUE);
                    player2.setStyle("-fx-font: 20 arial;");
                    group.getChildren().addAll(player1, player2);
                    addImage("/ArrowRight.jpg", SIZE * 11 + (int) (SIZE * 0.2), SIZE * 6, (int) (SIZE * 1.6), (int) (SIZE * 1.6));
                    shootsParticipant1();
                }
            }
        });
    }

    // Обработка стрельбы по полю участника2
    private void shootsParticipant1(){
        scene.setOnMouseClicked(e -> {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    if (e.getSceneX() >= SIZE * 12 + SIZE * (i + 1) && e.getSceneX() < SIZE * 12 + SIZE * (i + 2) &&
                            e.getSceneY() >= SIZE * (j + 1) && e.getSceneY() < SIZE * (j + 2) &&
                            table2[i][j] == 1 && tableOfHits2[i + 1][j + 1] != 1 && endGame) {
                        tableOfHits2[i + 1][j + 1] = 1;
                        shipKilled(i, j, table2, SIZE * 12, 2);
                        score1++;
                        if (score1 == 20) {
                            endGame = false;
                            Text win = new Text("Участник1 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                            addImage("/Winner.jpg", SIZE, SIZE, SIZE * 10, SIZE * 10);
                        }
                    } else if (e.getSceneX() >= SIZE * 12 + SIZE * (i + 1) && e.getSceneX() < SIZE * 12 + SIZE * (i + 2) &&
                            e.getSceneY() >= SIZE * (j + 1) && e.getSceneY() < SIZE * (j + 2) &&
                            table2[i][j] == 0 && tableOfHits2[i + 1][j + 1] != 1 && endGame) {
                        tableOfHits2[i + 1][j + 1] = 1;
                        drawRectangleBlue(SIZE * 12 + SIZE * (i + 1), SIZE * (j + 1));
                        addImage("/ArrowLeft.jpg", SIZE * 11 + (int) (SIZE * 0.2), SIZE * 6, (int) (SIZE * 1.6), (int) (SIZE * 1.6));
                        shootsParticipant2();
                    }
                }
            }
        });
    }

    // Обработка стрельбы по полю участника1
    private void shootsParticipant2(){
        scene.setOnMouseClicked(e -> {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    if (e.getSceneX() >= SIZE * (i + 1) && e.getSceneX() < SIZE * (i + 2) &&
                            e.getSceneY() >= SIZE * (j + 1) && e.getSceneY() < SIZE * (j + 2) &&
                            table1[i][j] == 1 && tableOfHits1[i + 1][j + 1] != 1 && endGame) {
                        tableOfHits1[i + 1][j + 1] = 1;
                        shipKilled(i, j, table1, 0, 1);
                        score++;
                        if (score == 20) {
                            endGame = false;
                            Text win = new Text("Участник2 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                            addImage("/Winner.jpg", SIZE * 13, SIZE, SIZE * 10, SIZE * 10);
                        }
                    } else if (e.getSceneX() >= SIZE * (i + 1) && e.getSceneX() < SIZE * (i + 2) &&
                            e.getSceneY() >= SIZE * (j + 1) && e.getSceneY() < SIZE * (j + 2) &&
                            table1[i][j] == 0 && tableOfHits1[i + 1][j + 1] != 1 && endGame) {
                        tableOfHits1[i + 1][j + 1] = 1;
                        drawRectangleBlue(SIZE * (i + 1), SIZE * (j + 1));
                        addImage("/ArrowRight.jpg", SIZE * 11 + (int) (SIZE * 0.2), SIZE * 6, (int) (SIZE * 1.6), (int) (SIZE * 1.6));
                        shootsParticipant1();
                    }
                }
            }
        });
    }

    // Проверка в корабль попали или корабль убили
    void shipKilled(int i, int j, int[][] table, int segment, int participant) {
        boolean borderX0 = i == 0;
        boolean borderY0 = j == 0;
        boolean borderX9 = i == 9;
        boolean borderY9 = j == 9;
        boolean horizontallyVertically = false;
        int size = 1;
        int minX = i;
        int minY = j;
        boolean shipHasBeenKilled = false;
        int x = i + 1;
        while (x <= 9 && table[x][j] != 0) {
            if (x < minX) minX = x;
            if (x == 0) borderX0 = true;
            if (x == 9) borderX9 = true;
            if (j == 0) borderY0 = true;
            if (j == 9) borderY9 = true;
            size++;
            if (table[x][j] == 1) {
                shipHasBeenKilled = true;
                break;
            }
            x++;
        }
        x = i - 1;
        while (!shipHasBeenKilled && x >= 0 && table[x][j] != 0) {
            if (x < minX) minX = x;
            if (x == 0) borderX0 = true;
            if (x == 9) borderX9 = true;
            if (j == 0) borderY0 = true;
            if (j == 9) borderY9 = true;
            size++;
            if (table[x][j] == 1) {
                shipHasBeenKilled = true;
                break;
            }
            x--;
        }
        int y = j + 1;
        while (!shipHasBeenKilled && y <= 9 && table[i][y] != 0) {
            horizontallyVertically = true;
            if (i == 0) borderX0 = true;
            if (i == 9) borderX9 = true;
            if (y == 0) borderY0 = true;
            if (y == 9) borderY9 = true;
            if (y < minY) minY = y;
            size++;
            if (table[i][y] == 1) {
                shipHasBeenKilled = true;
                break;
            }
            y++;
        }
        y = j - 1;
        while (!shipHasBeenKilled && y >= 0 && table[i][y] != 0) {
            horizontallyVertically = true;
            if (i == 0) borderX0 = true;
            if (i == 9) borderX9 = true;
            if (y == 0) borderY0 = true;
            if (y == 9) borderY9 = true;
            if (y < minY) minY = y;
            size++;
            if (table[i][y] == 1) {
                shipHasBeenKilled = true;
                break;
            }

            y--;
        }
        if (!shipHasBeenKilled) {
            if (size == 1) horizontallyVertically = true;
            drawRectangleRed(segment + SIZE * (minX + 1), SIZE * (minY + 1), horizontallyVertically, size, borderX0, borderY0, borderX9, borderY9, participant);
        }
        else {
            drawCross(segment + SIZE * (i + 1), SIZE * (j + 1));
            table[i][j] = 2;
        }
    }

    // Отрисовка картинки
    void addImage(String name, int x, int y, int height, int width) {
        try {
            Image image = new Image(new FileInputStream(new File("").getAbsolutePath() + name));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            imageView.setX(x);
            imageView.setY(y);
            group.getChildren().add(imageView);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void forParticipants(int participant, int x, int y, int x1, int y1) {
        if (participant == 1) {
            for (int i = (x - SIZE) / SIZE; i <= x1; i++) {
                for (int j = (y - SIZE) / SIZE; j <= y1; j++) {
                    tableOfHits1[i][j] = 1;
                }
            }
        }
        if (participant == 2) {
            for (int i = (x - 13 * SIZE) / SIZE; i <= x1; i++) {
                for (int j = (y - SIZE) / SIZE; j <= y1; j++) {
                    tableOfHits2[i][j] = 1;
                }
            }
        }
    }

    // Отрисовка убитого корабля
    private void drawRectangleRed(int x, int y, boolean horizontallyVertically, int size, boolean borderX0,
                                  boolean borderY0, boolean borderX9, boolean borderY9, int participant) {
        Rectangle smallRectangleRed;
        if (horizontallyVertically)  {
            if (participant == 1) forParticipants(participant, x, y, (x - SIZE) / SIZE + 2, (y + SIZE * size - SIZE) / SIZE + 1);
            if (participant == 2) forParticipants(participant, x, y, (x - 13 * SIZE) / SIZE + 2, (y + SIZE * size - SIZE) / SIZE + 1);
            smallRectangleRed = new Rectangle(SIZE,SIZE * size);
            if (!borderX0 && borderY0 && !borderX9 && !borderY9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                y1 = y;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y + SIZE * size);
            }
            if (borderX0 && !borderY0 && !borderX9 && !borderY9) {
                int y1 = y - SIZE;
                for (int i = 0; i <= size + 1; i++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y + SIZE * size);
                drawRectangleBlue(x, y - SIZE);
            }
            if (!borderX0 && !borderY0 && !borderX9 && borderY9) {
                int y1 = y - SIZE;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                y1 = y - SIZE;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y - SIZE);
            }
            if (!borderX0 && !borderY0 && borderX9 && !borderY9) {
                int y1 = y - SIZE;
                for (int i = 0; i <= size + 1; i++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y + SIZE * size);
                drawRectangleBlue(x, y - SIZE);
            }
            if (borderX0 && borderY0 && !borderX9 && !borderY9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y + SIZE * size);
            }
            if (borderX0 && !borderY0 && !borderX9 && borderY9) {
                int y1 = y - SIZE;
                for (int i = 0; i <= size; i++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y - SIZE);
            }
            if (!borderX0 && borderY0 && borderX9 && !borderY9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y + SIZE * size);
            }
            if (!borderX0 && !borderY0 && borderX9 && borderY9) {
                int y1 = y - SIZE;
                for (int i = 0; i <= size; i++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y - SIZE);
            }
            if (!borderX0 && !borderY0 && !borderX9 && !borderY9) {
                int y1 = y - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x - SIZE, y1);
                    y1 += SIZE;
                }
                y1 = y - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x + SIZE, y1);
                    y1 += SIZE;
                }
                drawRectangleBlue(x, y - SIZE);
                drawRectangleBlue(x, y + SIZE * size);
            }


        }
        else {
            if (participant == 1) forParticipants(participant, x, y, (x + SIZE * size - SIZE) / SIZE + 1, (y - SIZE) / SIZE + 2);
            if (participant == 2) forParticipants(participant, x, y, (x  + SIZE * size - 13 * SIZE) / SIZE + 1, (y - SIZE) / SIZE + 2);
            smallRectangleRed = new Rectangle(SIZE * size,SIZE);
            if (!borderX0 && !borderY0 && !borderX9 && !borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                x1 = x - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
                drawRectangleBlue(x - SIZE, y);
            }
            if (borderX0 && !borderY0 && !borderX9 && !borderY9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
            }
            if (!borderX0 && !borderY0 && borderX9 && !borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                x1 = x - SIZE;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x - SIZE, y);
            }
            if (!borderX0 && borderY0 && !borderX9 && !borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
                drawRectangleBlue(x - SIZE, y);
            }
            if (!borderX0 && !borderY0 && !borderX9 && borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
                drawRectangleBlue(x - SIZE, y);
            }
            if (borderX0 && borderY0 && !borderX9 && !borderY9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
            }
            if (borderX0 && !borderY0 && !borderX9 && borderY9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x + SIZE * size, y);
            }
            if (!borderX0 && borderY0 && borderX9 && !borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y + SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x - SIZE, y);
            }

            if (!borderX0 && !borderY0 && borderX9 && borderY9) {
                int x1 = x - SIZE;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y - SIZE);
                    x1 += SIZE;
                }
                drawRectangleBlue(x - SIZE, y);
            }
        }
        smallRectangleRed.setFill(Color.RED);
        smallRectangleRed.setStroke(Color.BLACK);
        smallRectangleRed.setX(x);
        smallRectangleRed.setY(y);
        group.getChildren().addAll(smallRectangleRed);
    }

    // Отрисовка промаха
    private void drawRectangleBlue(int x, int y) {
        Rectangle smallRectangleBlue = new Rectangle(SIZE,SIZE);
        smallRectangleBlue.setFill(Color.LIGHTSKYBLUE);
        smallRectangleBlue.setStroke(Color.BLACK);
        smallRectangleBlue.setX(x);
        smallRectangleBlue.setY(y);
        group.getChildren().addAll(smallRectangleBlue);
    }

    // Отрисовка попадания в корабль
    private void drawCross(int x, int y) {
        Line line1 = new Line(x, y, x + SIZE, y + SIZE);
        line1.setStroke(Color.RED);
        Line line2 = new Line(x, y + SIZE, x + SIZE, y);
        line2.setStroke(Color.RED);
        group.getChildren().addAll(line1, line2);
    }

    // Установление кораблей
    private void installation(Ship ship, int[][] table, int[][] tableAroundShip) {
        // Переворот корабля
        ship.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                Ship.mouseClickedRight(ship, table, tableAroundShip);
            }
        });
        // Перемещение корабля
        ship.setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                oldX = ship.getTranslateX() - e.getSceneX();
                oldY = ship.getTranslateY() - e.getSceneY();
            }
        });
        ship.setOnMouseDragged(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ship.setTranslateX(oldX + e.getSceneX() - (oldX + e.getSceneX()) % SIZE);
                ship.setTranslateY(oldY + e.getSceneY() - (oldY + e.getSceneY()) % SIZE);
                Ship.mouseDragged(ship, table, tableAroundShip);
            }
        });
        ship.setOnMouseReleased(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Ship.mouseReleased(ship, table, tableAroundShip);
            }
        });
    }
}
