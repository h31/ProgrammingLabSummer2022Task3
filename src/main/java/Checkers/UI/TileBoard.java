package Checkers.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {

    private final StackPane pane;
    private InfoCenter infoCenter;
    private final byte size = 10;
    private Tile[][] tiles = new Tile[size][size];

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
        String[] setDigits = {"8", "7", "6", "5", "4", "3", "2", "1"};

        for (byte row = 0; row < size; row ++) {
            for(byte col = 0; col < size; col++) {
                Label label = new Label("");
                label.setAlignment(Pos.CENTER);
                label.setFont(Font.font(24));
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((col * 60) - 270);
                tile.getStackPane().setTranslateY((row * 60) - 270);
                if (row == 0 && col != 0 && col != 9 || row == 9 && col != 0 && col != 9) {
                    label.setText(setLetters[col - 1]);
                    tile.getStackPane().getChildren().add(label);
                } else if (col == 0 && row != 0 && row != 9 || col == 9 && row != 0 && row != 9) {
                    label.setText(setDigits[row - 1]);
                    tile.getStackPane().getChildren().add(label);
                } else if ((col + row) % 2 == 1 && col != 0 && row != 0 && col != 9 && row != 9) {
                    tile.pane.getChildren().remove(tile.border);
                    tile.border.setFill(Color.BLACK);
                    tile.pane.getChildren().add(tile.border);
                }
                pane.getChildren().add(tile.getStackPane());
                tiles[row][col] = tile;
            }
        }
    }



    public StackPane getStackPane() {
        return pane;
    }

    private class Tile {
        private StackPane pane;
        private Label label;

        public Rectangle border = new Rectangle();

        public Tile() {
            pane = new StackPane();
            pane.setMinSize(60, 60);
            border.setWidth(60);
            border.setHeight(60);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);
        }

        public StackPane getStackPane() {
            return pane;
        }
    }
}
