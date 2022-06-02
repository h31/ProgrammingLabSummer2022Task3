package checkers.ui;

import checkers.logic.GameStatistic;
import checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import static checkers.ui.Constants.SIDES;

public class CheckersBoard {

    private StackPane pane = null;
    private static final byte size = Constants.SIZE;
    public static Checker[][] checkers = new Checker[size][size];
    public static boolean isGame = false;
    private final Turner turner;
    boolean thatForTests;

    public CheckersBoard(InfoCenter infoCenter, boolean thatForTests) {
        this.thatForTests = thatForTests;
        if (!thatForTests) {
            pane = new StackPane();
            pane.setMinSize(Constants.APP_WIDTH, Constants.TILE_BOARD_HEIGHT);
            pane.setTranslateX((double) Constants.APP_WIDTH / 2);
            pane.setTranslateY((double) (Constants.TILE_BOARD_HEIGHT / 2) + Constants.INFO_CENTER_HEIGHT);
        } else {
            GameStatistic.isTest = true;
        }
        turner = new Turner(infoCenter);
        addAllChecks();
    }

    private void addAllChecks() {
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                Checker checker = new Checker(row, col);
                if (!thatForTests) {
                    checker.getStackPane().setTranslateX((col * 60) - 210);
                    checker.getStackPane().setTranslateY((row * 60) - 210);
                    pane.getChildren().add(checker.getStackPane());
                }
                checkers[row][col] = checker;
            }
        }
    }

    public static void restart() {
        GameStatistic.initRestart();
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                checkers[row][col].restart();
            }
        }
    }


    public StackPane getStackPane() {
        return pane;
    }

    public class Checker {
        private StackPane pane = null;
        private Label labelShadow = null;
        private Label labelColor = null;
        private Label labelKing = null;
        public int row;
        public int col;
        public boolean king = false;
        public boolean canEat = false;
        public SIDES side = Constants.SIDES.no;

        public Checker(int row, int col) {
            if (!thatForTests){
                this.row = row;
                this.col = col;

                pane = new StackPane();
                pane.setMinSize(55, 55);

                Rectangle border = new Rectangle(55, 55, Color.TRANSPARENT);

                pane.getChildren().add(border);

                initGraphicLevels();
                initSideAndColor();

                pane.getChildren().add(labelShadow);
                pane.getChildren().add(labelColor);
                pane.getChildren().add(labelKing);


                pane.setOnMouseClicked(event -> {
                    if (isGame) {
                        turner.makeATurn(row, col);
                        event.consume();
                    }
                });
            } else {
                this.row = row;
                this.col = col;
                initSideAndColor();
            }
        }

        private void initGraphicLevels() {
            labelKing = new Label("");
            labelKing.setMinSize(15, 15);
            labelKing.setMaxSize(15, 15);
            labelKing.setAlignment(Pos.CENTER);
            labelKing.setBackground(Constants.NO_CHECKER);

            labelColor = new Label("");
            labelColor.setMinSize(49, 49);
            labelColor.setMaxSize(49, 49);
            labelColor.setAlignment(Pos.CENTER);
            labelColor.setFont(Font.font(40));

            labelShadow = new Label("");
            labelShadow.setMinSize(53, 55);
            labelShadow.setMaxSize(53, 55);
            labelShadow.setAlignment(Pos.CENTER);
            labelShadow.setTranslateY(1);
        }

        private void initSideAndColor() {
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    if (!thatForTests) {
                        labelColor.setBackground(Constants.BLACK_CHECKER);
                        labelShadow.setBackground(Constants.BLACK_BACK);
                    }
                    side = SIDES.black;
                } else if (row > 4) {
                    if (!thatForTests) {
                        labelColor.setBackground(Constants.WHITE_CHECKER);
                        labelShadow.setBackground(Constants.BLACK_BACK);
                    }
                    side = SIDES.white;
                } else {
                    if (!thatForTests) {
                        labelColor.setBackground(Constants.NO_CHECKER);
                        labelShadow.setBackground(Constants.NO_CHECKER);
                    }
                    side = SIDES.no;
                }
            }
             else {
                if (!thatForTests) {
                    labelColor.setBackground(Constants.NO_CHECKER);
                    labelShadow.setBackground(Constants.NO_CHECKER);
                }
                side = SIDES.no;
            }
        }

        private void restart() {
            initSideAndColor();
            if (!thatForTests)
                labelKing.setBackground(Constants.NO_CHECKER);
            king = false;
            canEat = false;
        }


        public void highlightGraphic() {
            if (!thatForTests) labelColor.setBackground(Constants.CHOOSEN_CHECKER);
        }

        public void unHighlightGraphic() {
            if (!thatForTests)
                if (side.equals(SIDES.white)) {
                    labelColor.setBackground(Constants.WHITE_CHECKER);
                } else {
                    labelColor.setBackground(Constants.BLACK_CHECKER);
                }

        }

        public void clearGraphic() {
            if (!thatForTests) {
                labelKing.setBackground(Constants.NO_CHECKER);
                labelColor.setBackground(Constants.NO_CHECKER);
                labelShadow.setBackground(Constants.NO_CHECKER);
            }
        }

        public void makeKingGraphic() {
            if (!thatForTests) labelKing.setBackground(Constants.KING);
        }

        public void setSideColorGraphic() {
            if (!thatForTests) {
                labelShadow.setBackground(Constants.BLACK_BACK);
                if (side.equals(SIDES.white)) {
                    labelColor.setBackground(Constants.WHITE_CHECKER);
                } else {
                    labelColor.setBackground(Constants.BLACK_CHECKER);
                }
            }
        }


        public StackPane getStackPane() {
            return pane;
        }
    }
}
