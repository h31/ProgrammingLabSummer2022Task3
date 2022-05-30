package checkers.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static checkers.logic.Listeners.stepBack;
import static checkers.logic.Listeners.surrender;
import static checkers.logic.Logic.TILE_SIZE;
import static checkers.ui.Media.getImgUndoButton;

public class Buttons {
    private static final Button undoButton = new Button(), surrenderButton = new Button();
    private static final ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES),
            noButton = new ButtonType("Нет", ButtonBar.ButtonData.NO);

    public static Button getSurrenderButton() {
        return surrenderButton;
    }

    public static Button getUndoButton() {
        return undoButton;
    }

    public static ButtonType getNoButton() {
        return noButton;
    }

    public static ButtonType getYesButton() {
        return yesButton;
    }

    public static void makeButtons() {

        ImageView undo = new ImageView(getImgUndoButton());
        undo.setFitHeight(TILE_SIZE * 0.43);
        undo.setFitWidth(TILE_SIZE * 0.43);
        undoButton.setGraphic(undo);
        undoButton.setStyle("-fx-background-color: #bdb7ae;" +
                " -fx-border-radius: 5;");
        undoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, stepBack());
        undoButton.setPrefSize(surrenderButton.getWidth(), surrenderButton.getHeight());


        surrenderButton.setText("Сдаться");
        surrenderButton.setStyle("-fx-background-color: #bdb7ae;" +
                " -fx-font: 22 arial");
        surrenderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, surrender());



    }
}
