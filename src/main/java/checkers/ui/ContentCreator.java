package checkers.ui;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;





import static checkers.logic.Listeners.*;
import static checkers.logic.Logic.*;
import static checkers.ui.Media.getIcon;
import static checkers.ui.Media.getImgUndoButton;
import static javafx.scene.layout.BorderPane.setMargin;

public class ContentCreator {

    private static final Stage window = new Stage();
    private static final BorderPane bp = new BorderPane();

    private static final Group tileGroup = new Group();//Сюда будут наноситься клетки
    private static final Group pieceGroup = new Group(); //Сюда будут наноситься шашки
    private static final Pane root = new Pane();


    private static final VBox top = new VBox();
    private static final Label topText = new Label(), underTopText = new Label(); //Текст для хода
    // и напоминание о еде

    private static final Text whiteEat = new Text("Белые должны есть"),
            blackEat = new Text("Чёрные должны есть");

    private static final Button undoButton = new Button(), surrenderButton = new Button();
    private static final HBox bottom = new HBox(surrenderButton, undoButton);


    private static final FlowPane right = new FlowPane(Orientation.HORIZONTAL),
            left = new FlowPane(Orientation.HORIZONTAL);

    public static FlowPane getLeft() {
        return left;
    }

    public static FlowPane getRight() {
        return right;
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

    public static Stage getWindow() {
        return window;
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

    public static BorderPane getBp() {
        return bp;
    }

    /**
     * Отрисовавает начальное окно вместе со всеми Pane, стилями и размерами
     */

    public static void createContent() {

        bp.setStyle("-fx-background-color: #3B4248");



        setTurn(Piece.PieceType.WHITE); //Чтобы при перезапуске белые ходили всегда первыми
        changingTurn();
        topText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        topText.setTextFill(Color.ORANGE);

        underTopText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 24));
        underTopText.setTextFill(Color.RED);

        top.getChildren().addAll(topText, underTopText);

        top.setPadding(new Insets(0, 0, 15, 10));
        bp.setTop(top);
        top.setAlignment(Pos.TOP_CENTER);


        right.setMaxSize(2 * TILE_SIZE + 12, TILE_SIZE * 6 + 10);//Размер FlowPane, куда складываются съеденные шашки
        left.setMaxSize(2 * TILE_SIZE + 12, TILE_SIZE * 6 + 10);
        right.setAlignment(Pos.TOP_CENTER);
        left.setAlignment(Pos.TOP_CENTER);

        left.setHgap(10); //Промежутки между элементами FlowPane
        left.setVgap(10);
        right.setHgap(10);
        right.setVgap(10);


        bp.setRight(right);

        bp.setLeft(left);

        setMargin(left, new Insets(0, 10, 0, 0));//Отодвигаем от границы
        setMargin(right, new Insets(0, 0, 0, 10));


        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.setMaxSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);


        bp.setBottom(bottom);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setSpacing(8);
        ImageView undo = new ImageView(getImgUndoButton());
        undo.setFitHeight(TILE_SIZE * 0.43);
        undo.setFitWidth(TILE_SIZE * 0.43);
        undoButton.setGraphic(undo);
        undoButton.setStyle("-fx-background-color: #bdb7ae;" +
                " -fx-border-radius: 5;");


        surrenderButton.setText("Сдаться");
        surrenderButton.setStyle("-fx-background-color: #bdb7ae;" +
                " -fx-font: 22 arial");


        surrenderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, surrender());


        boardPainter();
        root.getChildren().addAll(tileGroup, pieceGroup);//Само поле с шашками

        bp.setCenter(root);


        undoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, stepBack());
        undoButton.setPrefSize(surrenderButton.getWidth(), surrenderButton.getHeight());


        bottom.setPadding(new Insets(20,10,5,0));

        window.setResizable(false);
        window.setTitle("Checkers");
        window.getIcons().add(getIcon());
        window.setScene(new Scene(bp));
        window.show();
        window.setOnCloseRequest(closeProgram(window));
    }


    public static void boardPainter() {
        //Отрисовка начальной доски
        getPieceGroup().getChildren().clear();
        getTileGroup().getChildren().clear();

        setKillNeed(false);
        getUnderTopText().setText("");
        getLeft().getChildren().clear();
        getRight().getChildren().clear();


        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                getBoard()[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.BLACK, x, y);
                    amountOfPieces+=1;
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
    }


    public static void eatAlarm() {
        if (isKillNeed()) {
            getUnderTopText().setText(turn== Piece.PieceType.BLACK ? getBlackEat().getText() : getWhiteEat().getText());
        } else {
            getUnderTopText().setText("");
        }
    }

    public static void changingTurn() {
        getTopText().setText(turn== Piece.PieceType.BLACK ? "Чёрные ходят" : "Белые ходят");

    }

}
