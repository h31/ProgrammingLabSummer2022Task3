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

public class SeaBattle extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public static Pane group = new Pane();
    private final Scene scene = new Scene(group, 700,400);
    private final int[][] table1 = new int[10][10];
    private final int[][] table2 = new int[10][10];
    private final int[][] aroundShip1 = new int[10][10];
    private final int[][] aroundShip2= new int[10][10];
    private Double oldX, oldY;
    private final Text error = new Text("ВЫ НЕ РАССТАВИЛИ ВСЕ КОРАБЛИ!");
    private int score = 0;
    private int score1 = 0;
    @Override
    public void start(Stage primaryStage) {
        group.setStyle("-fx-background-color: #efeaba; ");
        Button beginButton = new Button();
        beginButton.setLayoutX(275);
        beginButton.setLayoutY(160);
        beginButton.setPrefSize(150,50);
        beginButton.setText("Начать игру");
        beginButton.setStyle("-fx-background-color: #3cbd1a; ");
        forBeginButton(beginButton);

        primaryStage.setTitle("МОРСКОЙ БОЙ");
        group.getChildren().add(beginButton);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    void shipPosition(int[][] table, int[][] aroundShip) {
        error.setX(450);
        error.setY(300);
        error.setStroke(Color.RED);
        error.setStyle("-fx-font: 13 arial;");

        Water water1 = new Water(25, 25);
        group.getChildren().add(water1);

        Ship shipFourDecks1 = new Ship(550,25, 4);
        installation(shipFourDecks1, table, aroundShip);
        shipFlip(shipFourDecks1, table, aroundShip);

        Ship shipThreeDecks11 = new Ship(450,25, 3);
        installation(shipThreeDecks11, table, aroundShip);
        shipFlip(shipThreeDecks11, table, aroundShip);

        Ship shipThreeDecks21 = new Ship(450,125, 3);
        installation(shipThreeDecks21, table, aroundShip);
        shipFlip(shipThreeDecks21, table, aroundShip);

        Ship shipTwoDecks11 = new Ship(375,25,2);
        installation(shipTwoDecks11, table, aroundShip);
        shipFlip(shipTwoDecks11, table, aroundShip);

        Ship shipTwoDecks21 = new Ship(375,100,2);
        installation(shipTwoDecks21, table, aroundShip);
        shipFlip(shipTwoDecks21, table, aroundShip);

        Ship shipTwoDecks31 = new Ship(375,175,2);
        installation(shipTwoDecks31, table, aroundShip);
        shipFlip(shipTwoDecks31, table, aroundShip);

        Ship simpleShip11 = new Ship(300,25, 1);
        installation(simpleShip11, table, aroundShip);

        Ship simpleShip21 = new Ship(300,75, 1);
        installation(simpleShip21, table, aroundShip);

        Ship simpleShip31 = new Ship(300,125, 1);
        installation(simpleShip31, table, aroundShip);

        Ship simpleShip41 = new Ship(300,175, 1);
        installation(simpleShip41, table, aroundShip);

        Text participant2 = new Text("Участник2 установите корабли");
        participant2.setX(25);
        participant2.setY(350);
        participant2.setStroke(Color.ROYALBLUE);
        participant2.setStyle("-fx-font: 24 arial;");
        group.getChildren().addAll(participant2, shipFourDecks1.getRect(), shipThreeDecks21.getRect(),
                shipThreeDecks11.getRect(), shipTwoDecks31.getRect(), shipTwoDecks21.getRect(),
                shipTwoDecks11.getRect(), simpleShip41.getRect(), simpleShip31.getRect(),
                simpleShip21.getRect(), simpleShip11.getRect());

    }

    private void forBeginButton(Button beginButton) {
        beginButton.setOnAction(event -> {

            shipPosition(table1, aroundShip1);

            Text participant1 = new Text("Участник1 установите корабли");
            participant1.setX(25);
            participant1.setY(350);
            participant1.setStroke(Color.ROYALBLUE);
            participant1.setStyle("-fx-font: 24 arial;");
            beginButton.setDisable(false);
            beginButton.setVisible(false);
            Button button1 = new Button();
            button1.setLayoutX(500);
            button1.setLayoutY(325);
            button1.setText("ГОТОВО");
            button1.setStyle("-fx-background-color: #3cbd1a; ");
            group.getChildren().add(button1);
            button1.setOnAction(event1 -> {
                int score = 0;
                for (int i = 0; i <= 9; i++) {
                    for (int j = 0; j <= 9; j++) {
                        score += table1[i][j];
                    }
                }
                if (score != 20) {
                    group.getChildren().addAll(error);
                } else {
                    {
                        group.getChildren().clear();
                        group.getChildren().remove(error);
                        button1.setDisable(false);
                        button1.setVisible(false);
                        forButton1();
                    }
                }
            });


        });
    }

    void forButton1() {
        shipPosition(table2, aroundShip2);
        Button button2 = new Button();
        button2.setLayoutX(500);
        button2.setLayoutY(325);
        button2.setText("ГОТОВО");
        button2.setStyle("-fx-background-color: #3cbd1a; ");
        group.getChildren().addAll(button2);
        button2.setOnAction(event2 -> {
            int score1 = 0;
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    score1 += table2[i][j];
                }
            }
            if (score1 != 20) {
                group.getChildren().addAll(error);
            } else {
                {
                    button2.setDisable(false);
                    button2.setVisible(false);
                    group.getChildren().clear();
                    Water water1 = new Water(25, 25);
                    Water water2 = new Water(325, 25);
                    group.getChildren().addAll(water1, water2);
                    group.getChildren().remove(error);
                    addImage("/ArrowLeft.jpg", 280, 150, 40, 40);
                    mouseClicked();
                }
            }
        });
    }

    private void mouseClicked(){
        scene.setOnMouseClicked(e -> {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    if (e.getSceneX() >= 25 * (i + 1) && e.getSceneX() < 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table1[i][j] == 1) {

                        shipKilled(i, j, table1, 0);
                        score++;
                        if (score == 20) {
                            Text win = new Text("Участник2 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                            addImage("/Winner.jpg", 325, 25, 250, 250);
                        }

                    } else if (e.getSceneX() >= 25 * (i + 1) && e.getSceneX() < 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table1[i][j] == 0) {
                        drawRectangleBlue(25 * (i + 1), 25 * (j + 1));
                        addImage("/ArrowRight.jpg", 280, 150, 40, 40);
                        mouseClicked1();
                    }
                }
            }
        });
    }

    private void mouseClicked1(){
        scene.setOnMouseClicked(e -> {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {

                    if (e.getSceneX() >= 300 + 25 * (i + 1) && e.getSceneX() < 300 + 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table2[i][j] == 1) {
                        shipKilled(i, j, table2, 300);
                        score1++;
                        if (score1 == 20) {
                            Text win = new Text("Участник1 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                            addImage("/Winner.jpg", 25, 25, 250, 250);
                        }

                    } else if (e.getSceneX() >= 300 + 25 * (i + 1) && e.getSceneX() < 300 + 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table2[i][j] == 0) {
                        drawRectangleBlue(300 + 25 * (i + 1), 25 * (j + 1));
                        addImage("/ArrowLeft.jpg", 280, 150, 40, 40);
                        mouseClicked();

                    }
                }
            }
        });
    }

    void shipKilled(int i, int j, int[][] table, int segment){
        boolean i0 = i == 0;
        boolean j0 = j == 0;
        boolean i9 = i == 9;
        boolean j9 = j == 9;
        boolean horizontallyVertically = false;
        int size = 1;
        int minX = i;
        int minY = j;
        boolean f = false;
        int x = i + 1;
        while (x <= 9 && table[x][j] != 0) {
            if (x < minX) minX = x;
            if (x == 0) i0 = true;
            if (x == 9) i9 = true;
            if (j == 0) j0 = true;
            if (j == 9) j9 = true;
            size++;
            if (table[x][j] == 1) {
                f = true;
                break;
            }
            x++;
        }
        x = i - 1;
        while (!f && x >= 0 && table[x][j] != 0) {
            if (x < minX) minX = x;
            if (x == 0) i0 = true;
            if (x == 9) i9 = true;
            if (j == 0) j0 = true;
            if (j == 9) j9 = true;
            size++;
            if (table[x][j] == 1) {
                f = true;
                break;
            }
            x--;
        }
        int y = j + 1;
        while (!f && y <= 9 && table[i][y] != 0) {
            horizontallyVertically = true;
            if (i == 0) i0 = true;
            if (i == 9) i9 = true;
            if (y == 0) j0 = true;
            if (y == 9) j9 = true;
            if (y < minY) minY = y;
            size++;
            if (table[i][y] == 1) {
                f = true;
                break;
            }
            y++;
        }
        y = j - 1;
        while (!f && y >= 0 && table[i][y] != 0) {
            horizontallyVertically = true;
            if (i == 0) i0 = true;
            if (i == 9) i9 = true;
            if (y == 0) j0 = true;
            if (y == 9) j9 = true;
            if (y < minY) minY = y;
            size++;
            if (table[i][y] == 1) {
                f = true;
                break;
            }

            y--;
        }
        if (!f) {
            if (size == 1) horizontallyVertically = true;
            drawRectangleRed(segment + 25 * (minX + 1), 25 * (minY + 1), horizontallyVertically, size, i0, j0, i9, j9);
        }
        else {
            drawCross(segment + 25 * (i + 1), 25 * (j + 1));
            table[i][j] = 2;
        }
    }

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

    private void drawRectangleRed(int x, int y, boolean horizontallyVertically, int size, boolean i0, boolean j0, boolean i9, boolean j9) {
        Rectangle smallRectangleRed;
        if (horizontallyVertically)  {
            smallRectangleRed = new Rectangle(25,25 * size);
            if (!i0 && j0 && !i9 && !j9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                y1 = y;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y + 25 * size);
            }
            if (i0 && !j0 && !i9 && !j9) {
                int y1 = y - 25;
                for (int i = 0; i <= size + 1; i++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y + 25 * size);
                drawRectangleBlue(x, y - 25);
            }
            if (!i0 && !j0 && !i9 && j9) {
                int y1 = y - 25;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                y1 = y - 25;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y - 25);
            }
            if (!i0 && !j0 && i9 && !j9) {
                int y1 = y - 25;
                for (int i = 0; i <= size + 1; i++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y + 25 * size);
                drawRectangleBlue(x, y - 25);
            }
            if (i0 && j0 && !i9 && !j9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y + 25 * size);
            }
            if (i0 && !j0 && !i9 && j9) {
                int y1 = y - 25;
                for (int i = 0; i <= size; i++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y - 25);
            }
            if (!i0 && j0 && i9 && !j9) {
                int y1 = y;
                for (int i = 1; i <= size + 1; i++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y + 25 * size);
            }
            if (!i0 && !j0 && i9 && j9) {
                int y1 = y - 25;
                for (int i = 0; i <= size; i++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y - 25);
            }
            if (!i0 && !j0 && !i9 && !j9) {
                int y1 = y - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x - 25, y1);
                    y1 += 25;
                }
                y1 = y - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x + 25, y1);
                    y1 += 25;
                }
                drawRectangleBlue(x, y - 25);
                drawRectangleBlue(x, y + 25 * size);
            }
        }
        else {
            smallRectangleRed = new Rectangle(25 * size,25);
            if (!i0 && !j0 && !i9 && !j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                x1 = x - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
                drawRectangleBlue(x - 25, y);
            }
            if (i0 && !j0 && !i9 && !j9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
            }
            if (!i0 && !j0 && i9 && !j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                x1 = x - 25;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x - 25, y);
            }
            if (!i0 && j0 && !i9 && !j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
                drawRectangleBlue(x - 25, y);
            }
            if (!i0 && !j0 && !i9 && j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
                drawRectangleBlue(x - 25, y);
            }
            if (i0 && j0 && !i9 && !j9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
            }
            if (i0 && !j0 && !i9 && j9) {
                int x1 = x;
                for (int i = 1; i <= size + 1; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x + 25 * size, y);
            }
            if (!i0 && j0 && i9 && !j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y + 25);
                    x1 += 25;
                }
                drawRectangleBlue(x - 25, y);
            }

            if (!i0 && !j0 && i9 && j9) {
                int x1 = x - 25;
                for (int i = 0; i <= size; i ++) {
                    drawRectangleBlue(x1, y - 25);
                    x1 += 25;
                }
                drawRectangleBlue(x - 25, y);
            }
        }
        smallRectangleRed.setFill(Color.RED);
        smallRectangleRed.setStroke(Color.BLACK);
        smallRectangleRed.setX(x);
        smallRectangleRed.setY(y);
        group.getChildren().addAll(smallRectangleRed);
    }

    private void drawRectangleBlue(int x, int y) {
        Rectangle smallRectangleBlue = new Rectangle(25,25);
        smallRectangleBlue.setFill(Color.LIGHTSKYBLUE);
        smallRectangleBlue.setStroke(Color.BLACK);
        smallRectangleBlue.setX(x);
        smallRectangleBlue.setY(y);
        group.getChildren().addAll(smallRectangleBlue);
    }

    private void drawCross(int x, int y) {
        Line line1 = new Line(x, y, x + 25, y + 25);
        line1.setStroke(Color.RED);
        Line line2 = new Line(x, y + 25, x +25, y);
        line2.setStroke(Color.RED);
        group.getChildren().addAll(line1, line2);
    }

    private void shipFlip(Ship ship, int[][] table, int[][] tableAroundShip) {
        ship.getRect().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                Ship.mouseClickedRight(ship, table, tableAroundShip);
            }
        });
    }

    private void installation(Ship ship, int[][] table, int[][] tableAroundShip) {
        ship.getRect().setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                oldX = ship.getRect().getTranslateX() - e.getSceneX();
                oldY = ship.getRect().getTranslateY() - e.getSceneY();
            }
        });

        ship.getRect().setOnMouseDragged(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ship.getRect().setTranslateX(oldX + e.getSceneX() - (oldX + e.getSceneX()) % 25);
                ship.getRect().setTranslateY(oldY + e.getSceneY() - (oldY + e.getSceneY()) % 25);
                Ship.mouseDragged(ship, table, tableAroundShip);
            }
        });
        mouseReleased(ship, table, tableAroundShip);
    }

    private void mouseReleased(Ship ship, int[][] table, int[][] tableAroundShip) {
        ship.getRect().setOnMouseReleased(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Ship.mouseReleased(ship, table, tableAroundShip);
            }
        });
    }
}
