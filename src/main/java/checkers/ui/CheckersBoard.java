package checkers.ui;

import checkers.logic.GameSituation;
import checkers.logic.Utils;
import checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CheckersBoard {
    private final StackPane pane;
    private final InfoCenter infoCenter;
    private static final byte size = Constants.SIZE;
    public static Checker[][] checkers = new Checker[size][size];
    public static boolean isGame = false;
    private final Turner turner;

    public CheckersBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        turner = new Turner(infoCenter);
        pane = new StackPane();
        pane.setMinSize(Constants.APP_WIDTH, Constants.TILE_BOARD_HEIGHT);
        pane.setTranslateX((double) Constants.APP_WIDTH / 2);
        pane.setTranslateY((double) (Constants.TILE_BOARD_HEIGHT / 2) + Constants.INFO_CENTER_HEIGHT);

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

    public static void initAllForRestart() {
        GameSituation.initRestart();
        GameSituation.playerTurn = "Black";
        for (byte row = 0; row < size; row++) {
            for (byte col = 0; col < size; col++) {
                checkers[row][col].initForRestart();
            }
        }
    }

    public StackPane getStackPane() {
        return pane;
    }

    public class Checker {
        private final StackPane pane = new StackPane();;
        private final Label labelShadow = new Label("");
        private final Label labelColor = new Label("");
        private final Label labelKing = new Label("");
        public int row;
        public int col;
        public String color = "No";
        public boolean isKing = false;
        public boolean canEat = false;
        public Constants.SIDES side = Constants.SIDES.no;

        public Checker(int row, int col) {
            this.row = row;
            this.col = col;

            pane.setMinSize(55, 55);

            Rectangle border = new Rectangle(55, 55, Color.TRANSPARENT);

            pane.getChildren().add(border);

            initThreeLabels();
            initCheckerColor();

            pane.getChildren().add(labelShadow);
            pane.getChildren().add(labelColor);
            pane.getChildren().add(labelKing);


            pane.setOnMouseClicked(event -> {
                if (isGame) {
                    turner.makeATurn(row, col);
                    event.consume();
                }
            });
        }

        private void initThreeLabels() {
            labelKing.setMinSize(15, 15);
            labelKing.setMaxSize(15, 15);
            labelKing.setAlignment(Pos.CENTER);
            labelKing.setBackground(Constants.NO_CHECKER);

            labelColor.setMinSize(49, 49);
            labelColor.setMaxSize(49, 49);
            labelColor.setAlignment(Pos.CENTER);
            labelColor.setFont(Font.font(40));

            labelShadow.setMinSize(53, 55);
            labelShadow.setMaxSize(53, 55);
            labelShadow.setAlignment(Pos.CENTER);
            labelShadow.setTranslateY(1);
        }

        private void initCheckerColor() {
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    labelColor.setBackground(Constants.BLACK_CHECKER);
                    labelShadow.setBackground(Constants.BLACK_BACK);
                    side = Constants.SIDES.black;
                    color = "Black";
                } else if (row > 4) {
                    labelColor.setBackground(Constants.WHITE_CHECKER);
                    labelShadow.setBackground(Constants.BLACK_BACK);
                    side = Constants.SIDES.white;
                    color = "White";
                }
            }
        }

        private void initForRestart() {
            initCheckerColor();
            labelKing.setBackground(Constants.NO_CHECKER);
            isKing = false;
            canEat = false;
        }


        public void highlight() {
            labelColor.setBackground(Constants.CHOOSEN_CHECKER);
        }

        public void removeHighlight() {
            if (color.equals("White")) {
                labelColor.setBackground(Constants.WHITE_CHECKER);
            } else {
                labelColor.setBackground(Constants.BLACK_CHECKER);
            }

        }

        public void clearGraphic() {
            labelKing.setBackground(Constants.NO_CHECKER);
            labelColor.setBackground(Constants.NO_CHECKER);
            labelShadow.setBackground(Constants.NO_CHECKER);
        }

        public void makeKingGraphic() {
            labelKing.setBackground(Constants.KING);
        }

        public void paintInNormalColor(String activeColor) {
            labelShadow.setBackground(Constants.BLACK_BACK);
            if (activeColor.equals("White")) {
                labelColor.setBackground(Constants.WHITE_CHECKER);
            } else {
                labelColor.setBackground(Constants.BLACK_CHECKER);
            }
        }

        public void paintInGold() {
            labelShadow.setBackground(Constants.BLACK_BACK);
            labelColor.setBackground(Constants.CHOOSEN_CHECKER);
        }


        public StackPane getStackPane() {
            return pane;
        }
    }
}
