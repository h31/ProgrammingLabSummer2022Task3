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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;


import static checkers.logic.Listeners.stepBack;
import static checkers.logic.Listeners.surrender;
import static checkers.logic.Logic.*;
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
    private static Image imgUndoButton, icon;
    private static final HBox bottom = new HBox(surrenderButton, undoButton);


    private static final FlowPane right = new FlowPane(Orientation.HORIZONTAL),
            left = new FlowPane(Orientation.HORIZONTAL);

    public static FlowPane getLeft() {
        return left;
    }

    public static FlowPane getRight() {
        return right;
    }



    static {
        try {
//            sound = new Media("C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\sound.mp3");
//            stepSound = new MediaPlayer(sound);
            icon = new Image("C:\\ProgrammingLabSummer2022Task3\\input\\icon.png");
            imgUndoButton = new Image(new FileInputStream("C:\\ProgrammingLabSummer2022Task3\\input\\undo.png"));
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

    public static Image getIcon() {
        return icon;
    }

    public static Image getImgUndoButton() {
        return imgUndoButton;
    }

    public static BorderPane getBp() {
        return bp;
    }

    public static void createContent() {

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
        undo.setFitHeight(TILE_SIZE * 0.75);
        undo.setFitWidth(TILE_SIZE * 0.75);
        undoButton.setGraphic(undo);
        undoButton.setStyle("-fx-background-color: #B03653");
        surrenderButton.setText("Сдаться");
        surrenderButton.setStyle("-fx-background-color: #EBC9D0; -fx-border-color: red;" +
                " -fx-border-radius: 5; -fx-font: 22 arial");


        surrenderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, surrender());


        boardPainter();
        root.getChildren().addAll(tileGroup, pieceGroup);//Само поле с шашками

        bp.setCenter(root);


        undoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, stepBack());



        window.setResizable(false);
        window.setTitle("Checkers");
        window.getIcons().add(getIcon());
        window.setScene(new Scene(bp));
        window.show();
    }


    public static void boardPainter() {
        //Отрисовка начальной доски
        getPieceGroup().getChildren().clear();
        getTileGroup().getChildren().clear();

        setKillNeed(false);
        getUnderTopText().setText("");
        getLeft().getChildren().clear();
        getRight().getChildren().clear();

        turn = false;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                getBoard()[x][y] = tile;

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
    }


    public static void eatAlarm() {
        if (isKillNeed()) {
            getUnderTopText().setText(turn ? getBlackEat().getText() : getWhiteEat().getText());
        } else {
            getUnderTopText().setText("");
        }
    }

}
