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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SeaBattle extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    Pane group = new Pane();
    Scene scene = new Scene(group, 700,400);
    int[][] table1 = new int[10][10];
    int[][] table2 = new int[10][10];
    int[][] aroundShip1 = new int[10][10];
    int[][] aroundShip2= new int[10][10];
    Double oldX, oldY;
    @Override
    public void start(Stage primaryStage) {


        Button beginButton = new Button();
        beginButton.setLayoutX(330);
        beginButton.setLayoutY(200);
        beginButton.setText("Начать игру");
        forBeginButton(beginButton);


        primaryStage.setTitle("МОРСКОЙ БОЙ");
        group.getChildren().add(beginButton);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }






    private void forBeginButton(Button beginButton) {
        beginButton.setOnAction(event -> {

            Text error = new Text("ВЫ НЕ РАССТАВИЛИ ВСЕ КОРАБЛИ!");
            error.setX(450);
            error.setY(300);
            error.setStroke(Color.RED);
            error.setStyle("-fx-font: 13 arial;");

            Ship shipFourDecks = new Ship(550,25, 4);
            installation(shipFourDecks, table1, aroundShip1);
            shipFlip(shipFourDecks, table1, aroundShip1);


            Ship shipThreeDecks1 = new Ship(450,25, 3);
            installation(shipThreeDecks1, table1, aroundShip1);
            shipFlip(shipThreeDecks1, table1, aroundShip1);

            Ship shipThreeDecks2 = new Ship(450,125, 3);
            installation(shipThreeDecks2, table1, aroundShip1);
            shipFlip(shipThreeDecks2, table1, aroundShip1);

            Ship shipTwoDecks1 = new Ship(375,25,2);
            installation(shipTwoDecks1, table1, aroundShip1);
            shipFlip(shipTwoDecks1, table1, aroundShip1);

            Ship shipTwoDecks2 = new Ship(375,100,2);
            installation(shipTwoDecks2, table1, aroundShip1);
            shipFlip(shipTwoDecks2, table1, aroundShip1);

            Ship shipTwoDecks3 = new Ship(375,175,2);
            installation(shipTwoDecks3, table1, aroundShip1);
            shipFlip(shipTwoDecks3, table1, aroundShip1);


            Ship simpleShip1 = new Ship(300,25, 1);
            installation(simpleShip1, table1, aroundShip1);

            Ship simpleShip2 = new Ship(300,75, 1);
            installation(simpleShip2, table1, aroundShip1);

            Ship simpleShip3 = new Ship(300,125, 1);
            installation(simpleShip3, table1, aroundShip1);

            Ship simpleShip4 = new Ship(300,175, 1);
            installation(simpleShip4, table1, aroundShip1);

            Water water1 = new Water(25, 25);
            group.getChildren().add(water1);

            Text participant1 = new Text("Участник1 установите корабли");
            participant1.setX(25);
            participant1.setY(350);
            participant1.setStroke(Color.ROYALBLUE);
            participant1.setStyle("-fx-font: 24 arial;");
            group.getChildren().addAll(participant1);


            group.getChildren().addAll(shipFourDecks.a);
            group.getChildren().addAll(shipThreeDecks2.a);
            group.getChildren().addAll(shipThreeDecks1.a);
            group.getChildren().addAll(shipTwoDecks3.a);
            group.getChildren().addAll(shipTwoDecks2.a);
            group.getChildren().addAll(shipTwoDecks1.a);
            group.getChildren().addAll(simpleShip4.a);
            group.getChildren().addAll(simpleShip3.a);
            group.getChildren().addAll(simpleShip2.a);
            group.getChildren().addAll(simpleShip1.a);



            beginButton.setDisable(false);
            beginButton.setVisible(false);



            Button button1 = new Button();
            button1.setLayoutX(500);
            button1.setLayoutY(325);
            button1.setText("ГОТОВО");
            group.getChildren().addAll(button1);
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
                        group.getChildren().removeAll(participant1);
                        group.getChildren().removeAll(shipFourDecks.a);
                        group.getChildren().removeAll(shipThreeDecks2.a);
                        group.getChildren().removeAll(shipThreeDecks1.a);
                        group.getChildren().removeAll(shipTwoDecks3.a);
                        group.getChildren().removeAll(shipTwoDecks2.a);
                        group.getChildren().removeAll(shipTwoDecks1.a);
                        group.getChildren().removeAll(simpleShip4.a);
                        group.getChildren().removeAll(simpleShip3.a);
                        group.getChildren().removeAll(simpleShip2.a);
                        group.getChildren().removeAll(simpleShip1.a);

                        Ship shipFourDecks1 = new Ship(550,25, 4);
                        installation(shipFourDecks1, table2, aroundShip2);
                        shipFlip(shipFourDecks1, table2, aroundShip2);


                        Ship shipThreeDecks11 = new Ship(450,25, 3);
                        installation(shipThreeDecks11, table2, aroundShip2);
                        shipFlip(shipThreeDecks11, table2, aroundShip2);

                        Ship shipThreeDecks21 = new Ship(450,125, 3);
                        installation(shipThreeDecks21, table2, aroundShip2);
                        shipFlip(shipThreeDecks21, table2, aroundShip2);

                        Ship shipTwoDecks11 = new Ship(375,25,2);
                        installation(shipTwoDecks11, table2, aroundShip2);
                        shipFlip(shipTwoDecks11, table2, aroundShip2);

                        Ship shipTwoDecks21 = new Ship(375,100,2);
                        installation(shipTwoDecks21, table2, aroundShip2);
                        shipFlip(shipTwoDecks21, table2, aroundShip2);

                        Ship shipTwoDecks31 = new Ship(375,175,2);
                        installation(shipTwoDecks31, table2, aroundShip2);
                        shipFlip(shipTwoDecks31, table2, aroundShip2);


                        Ship simpleShip11 = new Ship(300,25, 1);
                        installation(simpleShip11, table2, aroundShip2);

                        Ship simpleShip21 = new Ship(300,75, 1);
                        installation(simpleShip21, table2, aroundShip2);

                        Ship simpleShip31 = new Ship(300,125, 1);
                        installation(simpleShip31, table2, aroundShip2);

                        Ship simpleShip41 = new Ship(300,175, 1);
                        installation(simpleShip41, table2, aroundShip2);

                        Water water11 = new Water(25, 25);
                        group.getChildren().add(water11);

                        Text participant2 = new Text("Участник2 установите корабли");
                        participant2.setX(25);
                        participant2.setY(350);
                        participant2.setStroke(Color.ROYALBLUE);
                        participant2.setStyle("-fx-font: 24 arial;");
                        group.getChildren().addAll(participant2);


                        group.getChildren().addAll(shipFourDecks1.a);
                        group.getChildren().addAll(shipThreeDecks21.a);
                        group.getChildren().addAll(shipThreeDecks11.a);
                        group.getChildren().addAll(shipTwoDecks31.a);
                        group.getChildren().addAll(shipTwoDecks21.a);
                        group.getChildren().addAll(shipTwoDecks11.a);
                        group.getChildren().addAll(simpleShip41.a);
                        group.getChildren().addAll(simpleShip31.a);
                        group.getChildren().addAll(simpleShip21.a);
                        group.getChildren().addAll(simpleShip11.a);



                        button1.setDisable(false);
                        button1.setVisible(false);



                        Button button2 = new Button();
                        button2.setLayoutX(500);
                        button2.setLayoutY(325);
                        button2.setText("ГОТОВО");
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
                                    group.getChildren().removeAll(participant2);

                                    group.getChildren().removeAll(shipFourDecks1.a);
                                    group.getChildren().removeAll(shipThreeDecks21.a);
                                    group.getChildren().removeAll(shipThreeDecks11.a);
                                    group.getChildren().removeAll(shipTwoDecks31.a);
                                    group.getChildren().removeAll(shipTwoDecks21.a);
                                    group.getChildren().removeAll(shipTwoDecks11.a);
                                    group.getChildren().removeAll(simpleShip41.a);
                                    group.getChildren().removeAll(simpleShip31.a);
                                    group.getChildren().removeAll(simpleShip21.a);
                                    group.getChildren().removeAll(simpleShip11.a);
                                    button2.setDisable(false);
                                    button2.setVisible(false);
                                    Water water2 = new Water(325, 25);
                                    group.getChildren().add(water2);
                                    group.getChildren().remove(error);

                                    try {
                                        Image image = new Image(new FileInputStream(new File("").getAbsolutePath() + "/strelka.jpg"));
                                        ImageView imageView = new ImageView(image);
                                        imageView.setFitHeight(40);
                                        imageView.setFitWidth(40);
                                        imageView.setX(280);
                                        imageView.setY(150);
                                        group.getChildren().add(imageView);
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                    mouseClicked();


                                }
                            }
                        });
                    }
                }
            });


        });
    }

    int score = 0;
    int score1 = 0;


    private void mouseClicked(){
        scene.setOnMouseClicked(e -> {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    if (e.getSceneX() >= 25 * (i + 1) && e.getSceneX() < 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table1[i][j] == 1) {

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
                        while (x <= 9 && table1[x][j] != 0) {
                            if (x < minX) minX = x;
                            if (x == 0) i0 = true;
                            if (x == 9) i9 = true;
                            if (j == 0) j0 = true;
                            if (j == 9) j9 = true;
                            size++;
                            if (table1[x][j] == 1) {
                                f = true;
                                break;
                            }
                            x++;
                        }
                        x = i - 1;
                        while (!f && x >= 0 && table1[x][j] != 0) {
                            if (x < minX) minX = x;
                            if (x == 0) i0 = true;
                            if (x == 9) i9 = true;
                            if (j == 0) j0 = true;
                            if (j == 9) j9 = true;
                            size++;
                            if (table1[x][j] == 1) {
                                f = true;
                                break;
                            }
                            x--;
                        }
                        int y = j + 1;
                        while (!f && y <= 9 && table1[i][y] != 0) {
                            horizontallyVertically = true;
                            if (i == 0) i0 = true;
                            if (i == 9) i9 = true;
                            if (y == 0) j0 = true;
                            if (y == 9) j9 = true;
                            if (y < minY) minY = y;
                            size++;
                            if (table1[i][y] == 1) {
                                f = true;
                                break;
                            }
                            y++;
                        }
                        y = j - 1;
                        while (!f && y >= 0 && table1[i][y] != 0) {
                            horizontallyVertically = true;
                            if (i == 0) i0 = true;
                            if (i == 9) i9 = true;
                            if (y == 0) j0 = true;
                            if (y == 9) j9 = true;
                            if (y < minY) minY = y;
                            size++;
                            if (table1[i][y] == 1) {
                                f = true;
                                break;
                            }
                            y--;
                        }
                        if (!f) {
                            if (size == 1) horizontallyVertically = true;
                            drawRectangleRed(25 * (minX + 1), 25 * (minY + 1), horizontallyVertically, size, i0, j0, i9, j9);
                        }
                        else {
                            drawCross(25 * (i + 1), 25 * (j + 1));
                            table1[i][j] = 2;
                        }
                        score++;
                        if (score == 20) {
                            Text win = new Text("Участник2 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                        }

                    } else if (e.getSceneX() >= 25 * (i + 1) && e.getSceneX() < 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table1[i][j] == 0) {
                        drawRectangleBlue(25 * (i + 1), 25 * (j + 1));
                        try {
                            Image image = new Image(new FileInputStream(new File("").getAbsolutePath() + "/strelka2.jpg"));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(40);
                            imageView.setFitWidth(40);
                            imageView.setX(280);
                            imageView.setY(150);
                            group.getChildren().add(imageView);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
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
                        while (x <= 9 && table2[x][j] != 0) {
                            if (x < minX) minX = x;
                            if (x == 0) i0 = true;
                            if (x == 9) i9 = true;
                            if (j == 0) j0 = true;
                            if (j == 9) j9 = true;
                            size++;
                            if (table2[x][j] == 1) {
                                f = true;
                                break;
                            }
                            x++;
                        }
                        x = i - 1;
                        while (!f && x >= 0 && table2[x][j] != 0) {
                            if (x < minX) minX = x;
                            if (x == 0) i0 = true;
                            if (x == 9) i9 = true;
                            if (j == 0) j0 = true;
                            if (j == 9) j9 = true;
                            size++;
                            if (table2[x][j] == 1) {
                                f = true;
                                break;
                            }
                            x--;
                        }
                        int y = j + 1;
                        while (!f && y <= 9 && table2[i][y] != 0) {
                            horizontallyVertically = true;
                            if (i == 0) i0 = true;
                            if (i == 9) i9 = true;
                            if (y == 0) j0 = true;
                            if (y == 9) j9 = true;
                            if (y < minY) minY = y;
                            size++;
                            if (table2[i][y] == 1) {
                                f = true;
                                break;
                            }
                            y++;
                        }
                        y = j - 1;
                        while (!f && y >= 0 && table2[i][y] != 0) {
                            horizontallyVertically = true;
                            if (i == 0) i0 = true;
                            if (i == 9) i9 = true;
                            if (y == 0) j0 = true;
                            if (y == 9) j9 = true;
                            if (y < minY) minY = y;
                            size++;
                            if (table2[i][y] == 1) {
                                f = true;
                                break;
                            }
                            y--;
                        }
                        if (!f) {
                            if (size == 1) horizontallyVertically = true;
                            drawRectangleRed(300 + 25 * (minX + 1), 25 * (minY + 1), horizontallyVertically, size, i0, j0, i9, j9);
                        }
                        else {
                            drawCross(300 + 25 * (i + 1), 25 * (j + 1));
                            table2[i][j] = 2;
                        }
                        score1++;
                        if (score1 == 20) {
                            Text win = new Text("Участник1 выиграл!!!");
                            win.setX(25);
                            win.setY(350);
                            win.setStroke(Color.GOLD);
                            win.setStyle("-fx-font: 26 arial;");
                            group.getChildren().addAll(win);
                        }

                    } else if (e.getSceneX() >= 300 + 25 * (i + 1) && e.getSceneX() < 300 + 25 * (i + 2) &&
                            e.getSceneY() >= 25 * (j + 1) && e.getSceneY() < 25 * (j + 2) &&
                            table2[i][j] == 0) {
                        drawRectangleBlue(300 + 25 * (i + 1), 25 * (j + 1));
                        try {
                            Image image = new Image(new FileInputStream(new File("").getAbsolutePath() + "/strelka.jpg"));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(40);
                            imageView.setFitWidth(40);
                            imageView.setX(280);
                            imageView.setY(150);
                            group.getChildren().add(imageView);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        mouseClicked();

                    }
                }
            }
        });
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
        ship.a.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    if (ship.a.getTranslateX() != 0 || ship.a.getTranslateY() != 0) {
                        ship.a.setTranslateX(0);
                        ship.a.setTranslateY(0);
                        for (int i = 0; i <= ship.listX.size() - 1; i++) {
                            for (int j = 0; j <= ship.listY.size() - 1; j++) {
                                table[ship.listX.get(i)][ship.listY.get(j)] = 0;
                                if (tableAroundShip[ship.aroundShipX.get(i)][ship.listY.get(j)] > 0) {
                                    tableAroundShip[ship.aroundShipY.get(i)][ship.listY.get(j)] -= 1;
                                }
                            }
                        }
                        ship.listX.clear();
                        ship.listY.clear();
                        ship.aroundShipX.clear();
                        ship.aroundShipY.clear();
                    } else {
                        ship.shipPosition++;
                        Rotate rotate = new Rotate();
                        if (ship.shipPosition % 2 == 0) {
                            rotate.setPivotX(ship.a.getX());
                            rotate.setPivotY(ship.a.getY() + 25);
                            rotate.setAngle(270);
                            ship.position = false;

                        } else {
                            rotate.setPivotX(ship.a.getX());
                            rotate.setPivotY(ship.a.getY() + 25);
                            rotate.setAngle(90);
                            ship.position = true;
                        }
                        ship.a.getTransforms().add(rotate);

                    }
            }
        });
    }









    private void installation(Ship ship, int[][] table, int[][] tableAroundShip) {
        ship.a.setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                oldX = ship.a.getTranslateX() - e.getSceneX();
                oldY = ship.a.getTranslateY() - e.getSceneY();
            }
        });

        ship.a.setOnMouseDragged(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ship.a.setTranslateX(oldX + e.getSceneX() - (oldX + e.getSceneX()) % 25);
                ship.a.setTranslateY(oldY + e.getSceneY() - (oldY + e.getSceneY()) % 25);
                for (int i = 0; i <= ship.listX.size() - 1; i++) {
                    for (int j = 0; j <= ship.listY.size() - 1; j++) {
                        table[ship.listX.get(i)][ship.listY.get(j)] = 0;
                        if (tableAroundShip[ship.aroundShipX.get(i)][ship.listY.get(j)] > 0) {
                            tableAroundShip[ship.aroundShipY.get(i)][ship.listY.get(j)] -= 1;
                        }
                    }
                }
                ship.listX.clear();
                ship.listY.clear();
                ship.aroundShipX.clear();
                ship.aroundShipY.clear();
            }
        });

        mouseReleased(ship, table, tableAroundShip);


    }


    private void mouseReleased(Ship ship, int[][] table, int[][] tableAroundShip) {
        ship.a.setOnMouseReleased(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (ship.position &&
                        (ship.a.getX() + ship.a.getTranslateX() < 25 ||
                                ship.a.getX() + ship.a.getTranslateX() + 25 > 275 ||
                                ship.a.getY() + ship.a.getTranslateY() < 25 ||
                                ship.a.getY() + ship.a.getTranslateY() + (ship.size1) * 25 > 275)) {
                    ship.a.setTranslateX(0);
                    ship.a.setTranslateY(0);
                } else if (!ship.position &&
                        (ship.a.getX() + ship.a.getTranslateX() - 25 < 25 ||
                                ship.a.getX() + ship.a.getTranslateX() + (ship.size1 - 1) * 25 > 275 ||
                                ship.a.getY() + ship.a.getTranslateY() < 25 ||
                                ship.a.getY() + ship.a.getTranslateY() + 25 > 275)) {
                    ship.a.setTranslateX(0);
                    ship.a.setTranslateY(0);
                } else {

                    if (ship.position) {
                        int firstX10 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 - 2);
                        int firstX20 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 - 1);
                        int firstX30;
                        if (firstX10 >= 0) firstX30 = firstX10;
                        else firstX30 = firstX20;

                        int secondX10 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25);
                        int secondX20 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 - 1);
                        int secondX30;
                        if (secondX10 <= 9) secondX30 = secondX10;
                        else secondX30 = secondX20;

                        int firstY10 = (int) ((ship.a.getY() + ship.a.getTranslateY()) / 25 - 2);
                        int firstY20 = (int) ((ship.a.getY() + ship.a.getTranslateY()) / 25 - 1);
                        int firstY30;
                        if (firstY10 >= 0) firstY30 = firstY10;
                        else firstY30 = firstY20;

                        int secondY10 = (int)((ship.a.getY() + ship.a.getTranslateY()) / 25 + ship.size1 - 1);
                        int secondY20 = (int)((ship.a.getY() + ship.a.getTranslateY()) / 25 + ship.size1 - 2);
                        int secondY30;
                        if (secondY10 <= 9) secondY30 = secondY10;
                        else secondY30 = secondY20;
                        cycle:
                        for (int i = firstX30; i <= secondX30; i++) {
                            for (int j = firstY30; j <= secondY30; j++) {

                                if (i == firstX10 || i == secondX10 || j == firstY10 || j == secondY10) {
                                    if (table[i][j] >= 1) {
                                        ship.a.setTranslateX(0);
                                        ship.a.setTranslateY(0);
                                        break cycle;
                                    } else {
                                        ship.aroundShipX.add(i);
                                        ship.aroundShipY.add(j);
                                        tableAroundShip[i][j] += 1;
                                    }
                                }
                                else {
                                    if (table[i][j] == 1) {
                                        ship.a.setTranslateX(0);
                                        ship.a.setTranslateY(0);
                                        break cycle;
                                    } else {
                                        ship.listX.add(i);
                                        ship.listY.add(j);
                                        table[i][j] = 1;
                                    }
                                }
                            }
                        }
                    }


                    if (!ship.position) {
                        int firstX10 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 - 3);
                        int firstX20 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 - 2);
                        int firstX30;
                        if (firstX10 >= 0) firstX30 = firstX10;
                        else firstX30 = firstX20;

                        int secondX10 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 + ship.size1 - 2);
                        int secondX20 = (int) ((ship.a.getX() + ship.a.getTranslateX()) / 25 + ship.size1 - 3);
                        int secondX30;
                        if (secondX10 <= 9) secondX30 = secondX10;
                        else secondX30 = secondX20;

                        int firstY10 = (int) ((ship.a.getY() + ship.a.getTranslateY()) / 25 - 2);
                        int firstY20 = (int) ((ship.a.getY() + ship.a.getTranslateY()) / 25 - 1);
                        int firstY30;
                        if (firstY10 >= 0) firstY30 = firstY10;
                        else firstY30 = firstY20;

                        int secondY10 = (int)((ship.a.getY() + ship.a.getTranslateY()) / 25);
                        int secondY20 = (int)((ship.a.getY() + ship.a.getTranslateY()) / 25 - 1);
                        int secondY30;
                        if (secondY10 <= 9) secondY30 = secondY10;
                        else secondY30 = secondY20;
                        cycle:
                        for (int i = firstX30; i <= secondX30; i++) {
                            for (int j = firstY30; j <= secondY30; j++) {
                                if (i == firstX10 || i == secondX10 || j == firstY10 || j == secondY10) {
                                    if (table[i][j] >= 1) {
                                        ship.a.setTranslateX(0);
                                        ship.a.setTranslateY(0);
                                        break cycle;
                                    } else {
                                        ship.aroundShipX.add(i);
                                        ship.aroundShipY.add(j);
                                        tableAroundShip[i][j] += 1;
                                    }
                                }
                                else {
                                    if (table[i][j] == 1) {
                                        ship.a.setTranslateX(0);
                                        ship.a.setTranslateY(0);
                                        break cycle;
                                    } else {
                                        ship.listX.add(i);
                                        ship.listY.add(j);
                                        table[i][j] = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });
    }


}
