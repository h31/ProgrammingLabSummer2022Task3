package checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Checkers extends Application {
    public static final int TILE_SIZE = 50;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPrefSize(500,500);
        bp.autosize();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x <WIDTH ; x++) {
                Tile tile = new Tile((x+y) % 2 == 0, x, y);


                tileGroup.getChildren().add(tile);
                

            }

        }

        bp.setCenter(root);
        HBox hbox = new HBox();
        HBox hbox1 = new HBox();
        Label label1 = new Label("Игрок 1");
        Label label2 = new Label("Игрок 2");

        hbox.getChildren().addAll(label1);
        bp.setTop(hbox);
        hbox.setAlignment(Pos.TOP_LEFT);

        hbox1.getChildren().add(label2);
        bp.setBottom(hbox1);
        hbox.setAlignment(Pos.TOP_LEFT);
        bp.setPadding(new Insets(10,10,10,10));

        return bp;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}