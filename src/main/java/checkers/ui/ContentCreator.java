package checkers.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static checkers.logic.Listeners.*;
import static checkers.logic.Logic.*;
import static checkers.ui.BoardPainter.boardPainter;
import static checkers.ui.Media.getIcon;
import static checkers.ui.ChangeContent.changingTurn;
import static javafx.scene.layout.BorderPane.setMargin;

public class ContentCreator {
    private static final Stage window = new Stage();
    private static final BorderPane bp = new BorderPane();
    private static final Group tileGroup = new Group(), pieceGroup = new Group();//Сюда будут наноситься клетки и шашки
    private static final Pane root = new Pane();
    private static final VBox top = new VBox();
    private static final Label topText = new Label(), underTopText = new Label(); //Текст для хода
    // и напоминание о еде
    private static final Text whiteEat = new Text("Белые должны есть"),
            blackEat = new Text("Чёрные должны есть");
    private static final HBox bottom = new HBox(Buttons.getSurrenderButton(), Buttons.getUndoButton());
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

    /**
     * Отрисовавает начальное окно вместе со всеми Pane, стилями и размерами
     */

    public static void createContent() {
        bp.setStyle("-fx-background-color: #3B4248");

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
        boardPainter();
        root.getChildren().addAll(tileGroup, pieceGroup);//Само поле с шашками

        bp.setCenter(root);

        bottom.setPadding(new Insets(20,10,5,0));

        window.setResizable(false);
        window.setTitle("Checkers");
        window.getIcons().add(getIcon());
        window.setScene(new Scene(bp));
        window.show();
        window.setOnCloseRequest(closeProgram(window));
    }

}
