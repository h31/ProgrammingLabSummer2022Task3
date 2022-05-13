package checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static checkers.Logic.*;


public class Checkers extends Application {

    private static final Group tileGroup = new Group();
    private static final Group pieceGroup = new Group();
    public static  HBox top = new HBox();
    public static Label topText;

    public static Group getPieceGroup() {
        return pieceGroup;
    }

    public static Group getTileGroup() {
        return tileGroup;
    }

    private Parent createContent() {

        Pane root = new Pane();
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: #3B4248");
        root.setStyle("-fx-background-color: red");

        topText = new Label("Белые ходят");
        topText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        topText.setTextFill(Color.ORANGE);
        top.getChildren().add(topText);

        top.setPadding(new Insets(0, 0, 15, 10));
        bp.setTop(top);
        top.setAlignment(Pos.TOP_CENTER);

        bp.setCenter(tileGroup);
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.setMaxSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        root.getChildren().addAll(tileGroup, pieceGroup);


        for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.BLACK, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }


            }

        }



        bp.setCenter(root);


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
        primaryStage.centerOnScreen();

    }

}