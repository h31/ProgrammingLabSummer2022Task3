package Checkers.UI;

import Checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CheckBoard {
    private final StackPane pane;
    public final InfoCenter infoCenter;
    private static final byte size = 8;
    public static Check[][] checks = new Check[size][size];
    public static boolean isGame = false;
    Turner turner;

    public CheckBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) (UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);
        addAllChecks();
        turner = new Turner(infoCenter);
    }

    private void addAllChecks() {
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                Check check = new Check(row, col);
                check.getStackPane().setTranslateX((col * 60) - 210);
                check.getStackPane().setTranslateY((row * 60) - 210);
                pane.getChildren().add(check.getStackPane());
                checks[row][col] = check;
            }
        }
    }

    public StackPane getStackPane() {
        return pane;
    }

    public class Check {
        private final StackPane pane;
        public Label label;
        Rectangle border = new Rectangle();
        public int row;
        public int col;
        public String color;
        public boolean isKing;
        public boolean someToEat = false;

        public Check(int row, int col) {
            this.row = row;
            this.col = col;

            pane = new StackPane();
            pane.setMinSize(51, 51);

            border.setWidth(51);
            border.setHeight(51);
            border.setFill(Color.TRANSPARENT);

            pane.getChildren().add(border);

            label = new Label("");
            label.setMinWidth(51);
            label.setMinHeight(51);
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(30));
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    label.setBackground(UIConstants.backBlack);
                    color = "Black";
                } else if (row > 4) {
                    label.setBackground(UIConstants.backWhite);
                    color = "White";
                } else {
                    label.setBackground(UIConstants.backNo);
                    color = "No";
                }
            } else {
                label.setBackground(UIConstants.backNo);
                color = "No";
            }
            pane.getChildren().add(label);


            pane.setOnMouseClicked(event -> {
                if (isGame) turner.makeATurn(row, col, checks);
            });


        }

        public StackPane getStackPane() {
            return pane;
        }
    }
}
