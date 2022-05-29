package Checkers.UI;

import Checkers.logic.Turner;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

import static java.lang.Math.abs;

public class CheckBoard {
    private final StackPane pane;
    boolean someToEatAllWhite = false;
    boolean someToEatAllBlack = false;
    private final InfoCenter infoCenter;
    private final byte size = 8;
    private final Check[][] checks = new Check[size][size];

    public static boolean isGame = false;

    private boolean isTurn = false;

    private int checkerTurnRow;

    private int checkerTurnCol;
    boolean checkerSomeToEat = false;
    private byte lastX = 0;
    private Color checkerTurnColor;

    private String playerTurn = "White";
    private int cntBlack = 12;
    private int cntWhite = 12;

    public CheckBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllChecks();
    }

    private void addAllChecks() {

        for (byte row = 0; row < size; row ++) {
            for(byte col = 0; col < size; col++) {
                Check check = new Check(row, col);
                check.getStackPane().setTranslateX((col * 60) - 210);
                check.getStackPane().setTranslateY((row * 60) - 210);
                pane.getChildren().add(check.getStackPane());
                checks[row][col] = check;
            }
        }
    }

    public void changePlayerTurn() {
        if (Objects.equals(playerTurn, "White")) {
            playerTurn = "Black";
        } else {
            playerTurn = "White";
        }
        infoCenter.updateMessage("Player " + playerTurn + "'s turn");
    }

    public StackPane getStackPane() {
        return pane;
    }

    public class Check {
        boolean someToEat = false;
        int row;
        boolean isKing;
        int col;
        private StackPane pane;
        private Label label;
        Rectangle border = new Rectangle();

        public Color color;



        public Check(int row, int col) {
            this.row = row;
            this.col = col;

            pane = new StackPane();
            pane.setMinSize(51, 51);

            border.setWidth(51);
            border.setHeight(51);
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    border.setFill(Color.RED);
                    color = Color.RED;
                } else
                if (row > 4) {
                    border.setFill(Color.GREEN);
                    color = Color.GREEN;
                } else {
                    border.setFill(Color.TRANSPARENT);
                    color = Color.TRANSPARENT;
                }
            } else {
                border.setFill(Color.TRANSPARENT);
                color = Color.TRANSPARENT;
            }
            pane.getChildren().add(border);

            label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);

            pane.setOnMouseClicked(event -> {
                if (isGame) {
                    if (!isTurn && !Objects.equals(color, Color.TRANSPARENT) && lastX != 2 &&
                            (isWhiteTurn() && color.equals(Color.GREEN) && (someToEat || !someToEatAllWhite) ||
                                    !isWhiteTurn() && color.equals(Color.RED)  && (someToEat || !someToEatAllBlack))) {
                        isTurn = true;
                        checkerTurnRow = row;
                        checkerTurnCol = col;
                        checkerTurnColor = color;
                        checkerSomeToEat = someToEat;
                        inline(row, col);
                    }  else if (checkerTurnRow == row && checkerTurnCol == col && lastX != 2) {
                        isTurn = false;
                        unline(checkerTurnRow, checkerTurnCol);
                    } else if (checkerTurnRow == row && checkerTurnCol == col) {
                        isTurn = false;
                        unline(checkerTurnRow, checkerTurnCol);
                        lastX = 0;
                        changePlayerTurn();
                    }else if(checkerTurnColor == color && isTurn && lastX != 2 && checkerSomeToEat == someToEat) {
                        unline(checkerTurnRow, checkerTurnCol);
                        checkerTurnRow = row;
                        checkerTurnCol = col;
                        inline(row, col);
                    } else if (isTurn) {
                        if (Objects.equals(color, Color.TRANSPARENT)) {
                            Turner checkTurn = new Turner(checkerTurnRow, checkerTurnCol, row, col, color, checkerTurnColor);
                            byte x = checkTurn.checkTurn(checks);
                            if (x != 0) {
                                if (x == 1 && !checkTurn.checkAll(checks, checkerTurnCol, checkerTurnRow)) {
                                    pane.getChildren().remove(border);
                                    border.setFill(checkerTurnColor);
                                    pane.getChildren().add(border);
                                    color = checkerTurnColor;
                                    delete(checkerTurnRow, checkerTurnCol);
                                    isTurn = false;
                                    if (isWhiteTurn() && row == 0 || !isWhiteTurn() && row == 7) {
                                        makeAKing(row, col);
                                    }
                                    changePlayerTurn();
                                } else if (x == 2) {
                                    pane.getChildren().remove(border);
                                    border.setFill(Color.GOLD);
                                    pane.getChildren().add(border);
                                    color = checkerTurnColor;
                                    delete(checkerTurnRow, checkerTurnCol);
                                    if (checks[checkTurn.eatenRow()][checkTurn.eatenCol()].color.equals(Color.RED)) {
                                        cntBlack--;
                                    } else {
                                        cntWhite--;
                                    }
                                    delete(checkTurn.eatenRow(), checkTurn.eatenCol());
                                    checkerTurnRow = row;
                                    checkerTurnCol = col;
                                    lastX = 2;
                                    if (!checkTurn.checkAll(checks, checkerTurnCol, checkerTurnRow)) {
                                        pane.getChildren().remove(border);
                                        border.setFill(checkerTurnColor);
                                        pane.getChildren().add(border);
                                        isTurn = false;
                                        changePlayerTurn();
                                        lastX = 0;
                                    }
                                }
                            }
                        }
                    }

                    boolean tWhite = false;
                    boolean tBlack = false;
                    for (byte i = 0; i < size; i ++) {
                        for(byte j = 0; j < size; j++) {
                            if (!checks[i][j].color.equals(Color.TRANSPARENT)) {
                                Turner checkTurn2 = new Turner(j, i, 0, 0,
                                        Color.TRANSPARENT, checks[i][j].color);
                                if (checkTurn2.checkAll(checks, j, i)) {

                                    checks[i][j].someToEat = true;
                                    if (checks[i][j].color.equals(Color.GREEN)) {
                                        someToEatAllWhite = true;
                                        tWhite = true;
                                    } else {
                                        someToEatAllBlack = true;
                                        tBlack = true;
                                    }
                                } else checks[i][j].someToEat = false;
                            }
                        }
                    }
                    someToEatAllWhite = tWhite;
                    someToEatAllBlack = tBlack;




                }
                if (cntBlack == 0 || cntWhite == 0) {
                    isGame = false;
                    if (cntBlack == 0) {
                        infoCenter.updateMessage("White Won!");
                    } else {
                        infoCenter.updateMessage("Black Won!");
                    }
                }
            });



        }

        public void delete(int row, int col) {
            Check check = checks[row][col];
            check.pane.getChildren().remove(check.border);
            check.border.setFill(Color.TRANSPARENT);
            check.pane.getChildren().add(check.border);
            check.color = Color.TRANSPARENT;
        }

        public void makeAKing(int row, int col) {
            Check check = checks[row][col];
            check.label.setText("X");
        }

        public void inline(int row, int col) {
            Check check = checks[row][col];
            check.pane.getChildren().remove(check.border);
            check.border.setFill(Color.GOLD);
            check.pane.getChildren().add(check.border);
        }

        public void unline(int row, int col) {
            Check check = checks[row][col];
            check.pane.getChildren().remove(check.border);
            check.border.setFill(check.color);
            check.pane.getChildren().add(check.border);
        }

        public boolean isWhiteTurn() {
            boolean result;
            result = playerTurn.equals("White");
            return result;
        }

        public StackPane getStackPane() {
            return pane;
        }




    }





}
