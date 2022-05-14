package checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static checkers.ConfirmBox.confirmation;
import static checkers.Logic.*;


public class Checkers extends Application {

    private static final Group tileGroup = new Group();//Сюда будут наноситься клетки
    private static final Group pieceGroup = new Group(); //Сюда будут наноситься шашки

    private static Scene scene;


    private static final VBox top = new VBox();
    private static final Label topText = new Label(), underTopText = new Label(); //Текст для хода
    // и напоминание о еде
    private static final VBox eatenWhitePieces = new VBox(10), eatenBlackPieces = new VBox(10);
    private static final Text whiteEat = new Text("Белые должны есть"),
            blackEat = new Text("Чёрные должны есть");

    private static final Button undoButton = new Button(), surrenderButton = new Button();
    private static Image imgUndoButton;
    private static final HBox bottom = new HBox(surrenderButton, undoButton);
    private static final Stage window = new Stage();


    public static Button getSurrenderButton() {
        return surrenderButton;
    }


    public static Scene getScene() {
        return scene;
    }

    static {
        try {
            imgUndoButton = new Image(new FileInputStream("C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\undo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Label getUnderTopText() {
        return underTopText;
    }

    public static Text getBlackEat() {
        return blackEat;
    }

    public static Text getWhiteEat() {
        return whiteEat;
    }

    public static VBox getEatenBlackPieces() {
        return eatenBlackPieces;
    }

    public static VBox getEatenWhitePieces() {
        return eatenWhitePieces;
    }

    public static VBox getTop() {
        return top;
    }

    public static Label getTopText() {
        return topText;
    }

    public static Group getPieceGroup() {
        return pieceGroup;
    }

    public static Group getTileGroup() {
        return tileGroup;
    }

    public static Image getImgUndoButton() {
        return imgUndoButton;
    }

    private Parent createContent(Stage window) {

        Pane root = new Pane();
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: #3B4248");


        turn = false; //Чтобы при перезапуске белые ходили всегда первыми
        changingTurn();
        topText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        topText.setTextFill(Color.ORANGE);

        underTopText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 24));
        underTopText.setTextFill(Color.RED);

        top.getChildren().addAll(topText, underTopText);

        top.setPadding(new Insets(0, 0, 15, 10));
        bp.setTop(top);
        top.setAlignment(Pos.TOP_CENTER);

        bp.setRight(getEatenWhitePieces());
        bp.setLeft(getEatenBlackPieces());
        getEatenBlackPieces().setAlignment(Pos.TOP_CENTER);
        getEatenWhitePieces().setAlignment(Pos.BOTTOM_CENTER);
        getEatenWhitePieces().setPadding(new Insets(10, 10, 0, 15));

        getEatenBlackPieces().setPadding(new Insets(10, 10, 0, 15));
        getEatenWhitePieces().setPrefWidth(TILE_SIZE * 1.5);
        getEatenBlackPieces().setPrefWidth(TILE_SIZE * 1.5);


        bp.setCenter(tileGroup);
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.setMaxSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);


        bp.setBottom(bottom);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setSpacing(8);
        ImageView undo = new ImageView(getImgUndoButton());
        undo.setFitHeight(TILE_SIZE * 0.75);
        undo.setFitWidth(TILE_SIZE * 0.75);
        undoButton.setGraphic(undo);
        undoButton.setStyle("-fx-background-color: #B03653");
        surrenderButton.setText("Сдаться");
        surrenderButton.setStyle("-fx-background-color: #EBC9D0; -fx-border-color: red;" +
                " -fx-border-radius: 5; -fx-font: 22 arial");

        surrenderButton.setOnAction(e -> {
            String message = turn ? "\nПоражение чёрных" : "\n Поражение белых";
            if (confirmation("Surrender", "Вы точно хотите сдаться?" + message)) {
                getEatenWhitePieces().getChildren().clear();
                getEatenBlackPieces().getChildren().clear();
                getPieceGroup().getChildren().clear();
                getTileGroup().getChildren().clear();
                getTop().getChildren().clear();

                bp.getChildren().removeAll(tileGroup, pieceGroup, top, bottom, root);


                window.close();
                start(new Stage());
            }
        });

        root.getChildren().addAll(tileGroup, pieceGroup);//Само поле с шашками


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
    public void start(Stage window) {
        scene = new Scene(createContent(window));

        window.setTitle("Checkers");
        window.setScene(scene);
        window.show();
        window.centerOnScreen();


    }

}

