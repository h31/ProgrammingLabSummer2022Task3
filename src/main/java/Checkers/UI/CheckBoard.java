package Checkers.UI;

import Checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CheckBoard {
    private final StackPane pane;
    final InfoCenter infoCenter;
    private static final byte size = UIConstants.SIZE;
    public static Checker[][] checkers = new Checker[size][size];
    public static boolean isGame = false;
    Turner turner;

    public CheckBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        turner = new Turner(infoCenter);
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) (UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllChecks();
    }

    private void addAllChecks() {
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                Checker checker = new Checker(row, col);
                checker.getStackPane().setTranslateX((col * 60) - 210);
                checker.getStackPane().setTranslateY((row * 60) - 210);
                pane.getChildren().add(checker.getStackPane());
                checkers[row][col] = checker;
            }
        }
    }

    public StackPane getStackPane() {
        return pane;
    }

    public class Checker {
        private final StackPane pane;
        public Label labelDown;
        public Label labelUp;
        public int row;
        public int col;
        public String color;
        public boolean isKing = false;
        public boolean someToEat = false;

        public Checker(int row, int col) {
            this.row = row;
            this.col = col;

            pane = new StackPane();
            pane.setMinSize(55, 55);

            Rectangle border = new Rectangle();
            border.setWidth(55);
            border.setHeight(55);
            border.setFill(Color.TRANSPARENT);

            pane.getChildren().add(border);

            labelUp = new Label("");
            labelUp.setMinWidth(51);
            labelUp.setMinHeight(51);
            labelUp.setAlignment(Pos.CENTER);
            labelUp.setFont(Font.font(30));
            labelDown = new Label("");
            labelDown.setMinWidth(55);
            labelDown.setMinHeight(55);
            labelDown.setAlignment(Pos.CENTER);

            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    labelUp.setBackground(UIConstants.BLACK_CHECKER);
                    labelDown.setBackground(UIConstants.BLACK_BACK);
                    color = "Black";
                } else if (row > 4) {
                    labelUp.setBackground(UIConstants.WHITE_CHECKER);
                    labelDown.setBackground(UIConstants.BLACK_BACK);
                    color = "White";
                } else {
                    labelUp.setBackground(UIConstants.NO_CHECKER);
                    color = "No";
                }
            } else {
                labelUp.setBackground(UIConstants.NO_CHECKER);
                color = "No";
            }
            pane.getChildren().add(labelDown);
            pane.getChildren().add(labelUp);


            pane.setOnMouseClicked(event -> {
                if (isGame && event.getButton() == MouseButton.PRIMARY) turner.makeATurn(row, col);
                event.consume();
            });


        }

        public StackPane getStackPane() {
            return pane;
        }
    }
}
