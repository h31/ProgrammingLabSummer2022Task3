package enCheckers.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

public class CheckBoard {
    private final StackPane pane;
    private final InfoCenter infoCenter;
    private final byte size = 8;
    private final Check[][] checks = new Check[size][size];

    public static boolean isGame = false;

    private boolean isTurn = false;

    private int checkerTurnRow;

    private int checkerTurnCol;
    private Color checkerTurnColor;

    private String playerTurn = "White";

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

    private class Check {
        int row;
        int col;
        private StackPane pane;
        private Label label;
        Rectangle border = new Rectangle();

        Color color;



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
                    if (!isTurn && !Objects.equals(color, Color.TRANSPARENT)) {
                        isTurn = true;
                        checkerTurnRow = row;
                        checkerTurnCol = col;
                        checkerTurnColor = color;
                        inline(row, col);
                    } else if (checkerTurnRow == row && checkerTurnCol == col){
                        isTurn = false;
                        unline(checkerTurnRow, checkerTurnCol);
                    } else if (checkerTurnColor == color){
                        unline(checkerTurnRow, checkerTurnCol);
                        checkerTurnRow = row;
                        checkerTurnCol = col;
                        inline(row, col);
                    } else if (isTurn) {
                        if (Objects.equals(color, Color.TRANSPARENT)) {
                            pane.getChildren().remove(border);
                            if (Objects.equals(checkerTurnColor, Color.GREEN)) {
                                color = Color.GREEN;
                            }
                            if (Objects.equals(checkerTurnColor, Color.RED)) {
                                color = Color.RED;
                            }
                            border.setFill(color);
                            pane.getChildren().add(border);
                            delete(checkerTurnRow, checkerTurnCol);
                            isTurn = false;
                            changePlayerTurn();
                        }
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

        public StackPane getStackPane() {
            return pane;
        }




    }





}
