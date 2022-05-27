package enCheckers.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

public class TileBoard {

    private StackPane pane;
    private InfoCenter infoCenter;
    private byte size = 9;
    private Tile[][] tiles = new Tile[size][size];

    private String playerTurn = "White";
    private boolean isEndOfGame = false;

    public TileBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllTiles();
    }

    private void addAllTiles() {
        String[] setLetters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] setDigits = {"1", "2", "3", "4", "5", "6", "7", "8"};

        for (byte row = 0; row < size; row ++) {
            for(byte col = 0; col < size; col++) {
                Label label = new Label("");
                label.setAlignment(Pos.CENTER);
                label.setFont(Font.font(24));
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((col * 60) - 240);
                tile.getStackPane().setTranslateY((row * 60) - 240);
                if (row == 0 && col != 0) {
                    label.setText(setLetters[col - 1]);
                    tile.getStackPane().getChildren().add(label);
                } else if (col == 0 && row != 0) {
                    label.setText(setDigits[row - 1]);
                    tile.getStackPane().getChildren().add(label);
                } else if ((col + row) % 2 == 0 && col != 0 && row != 0) {
                    tile.getStackPane().setBackground(new Background
                            (new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
                }
                pane.getChildren().add(tile.getStackPane());
                tiles[row][col] = tile;
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

    public String getPlayerTurn() {
        return String.valueOf(playerTurn);
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void checkForWinner() {
        //TODO
    }

    private class Tile {
        private StackPane pane;
        private Label label;

        public Tile() {
            pane = new StackPane();
            pane.setMinSize(60, 60);

            Rectangle border = new Rectangle();
            border.setWidth(60);
            border.setHeight(60);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);

            pane.setOnMouseClicked(event -> {
                if (label.getText().isEmpty() && !isEndOfGame) {
                    label.setText(getPlayerTurn());
                    changePlayerTurn();
                    checkForWinner();
                }
            });
        }

        public StackPane getStackPane() {
            return pane;
        }

        public String getValue() {
            return label.getText();
        }

        public void setValue(String value) {
            label.setText(value);
        }
    }
}
