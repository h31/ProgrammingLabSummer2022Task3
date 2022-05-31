package checkers.UI;

import checkers.logic.DuringGameChecks;
import checkers.logic.SomeStaff;
import checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CheckersBoard {
    private final StackPane pane;
    private final InfoCenter infoCenter;
    private static final byte size = UIConstants.SIZE;
    public static Checker[][] checkers = new Checker[size][size];
    public static boolean isGame = false;
    private Turner turner;

    public CheckersBoard() {
        //THIS INIT FOR LOGIC TESTS!!!!
        pane = null;
        infoCenter = null;
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                Checker checker = new Checker(row, col, true);
                checkers[row][col] = checker;
            }
        }
    }

    public CheckersBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        turner = new Turner(infoCenter);
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) (UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        DuringGameChecks.cntBlack = 12;
        DuringGameChecks.cntWhite = 12;
        DuringGameChecks.someToEatAllBlack = false;
        DuringGameChecks.someToEatAllWhite = false;

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
        public Label labelKing;
        public int row;
        public int col;
        public String color;
        public boolean isKing = false;
        public boolean someToEat = false;
        public Checker(int row, int col, boolean withoutGraphic) {
            //THIS INIT FOR LOGIC TESTS!!!!
            pane = null;
            this.row = row;
            this.col = col;
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    color = "Black";
                } else if (row > 4) {
                    color = "White";
                } else {
                    color = "No";
                }
            } else {
                color = "No";
            }
        }

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


            labelKing = new Label("");
            labelKing.setMinWidth(15);
            labelKing.setMinHeight(15);
            labelKing.setMaxWidth(15);
            labelKing.setMaxHeight(15);
            labelKing.setAlignment(Pos.CENTER);
            labelKing.setBackground(UIConstants.NO_CHECKER);
            labelUp = new Label("");
            labelUp.setMinWidth(49);
            labelUp.setMinHeight(49);
            labelUp.setMaxWidth(49);
            labelUp.setMaxHeight(49);
            labelUp.setAlignment(Pos.CENTER);
            labelUp.setFont(Font.font(40));
            labelDown = new Label("");
            labelDown.setMinWidth(53);
            labelDown.setMinHeight(55);
            labelDown.setMaxWidth(53);
            labelDown.setMaxHeight(55);
            labelDown.setAlignment(Pos.CENTER);
            labelDown.setTranslateY(1);
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
            pane.getChildren().add(labelKing);


            pane.setOnMouseClicked(event -> {
                if (isGame) {
                    turner.makeATurn(row, col);
                    event.consume();
                }
            });


        }

        public void inline() {
            labelUp.setBackground(UIConstants.CHOOSEN_CHECKER);
        }

        public void unline() {
            if (color.equals("White")) {
                labelUp.setBackground(UIConstants.WHITE_CHECKER);
            } else {
                labelUp.setBackground(UIConstants.BLACK_CHECKER);
            }

        }

        public void clearGraphic() {
            labelUp.setBackground(UIConstants.NO_CHECKER);
            labelKing.setBackground(UIConstants.NO_CHECKER);
            labelDown.setBackground(UIConstants.NO_CHECKER);
        }

        public void makeAKingGraphic() {
            labelKing.setBackground(UIConstants.KING);
        }

        public void paintInNormalColor(String activeColor) {
            labelDown.setBackground(UIConstants.BLACK_BACK);
            if (activeColor.equals("White")) {
                labelUp.setBackground(UIConstants.WHITE_CHECKER);
            } else {
                labelUp.setBackground(UIConstants.BLACK_CHECKER);
            }
        }

        public void paintInGold() {
            labelDown.setBackground(UIConstants.BLACK_BACK);
            labelUp.setBackground(UIConstants.CHOOSEN_CHECKER);
        }


        public StackPane getStackPane() {
            return pane;
        }
    }
}
