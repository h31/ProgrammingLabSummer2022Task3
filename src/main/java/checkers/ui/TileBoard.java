package checkers.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {

    private final StackPane pane;

    public TileBoard() {
        pane = new StackPane();
        pane.setMinSize(Constants.APP_WIDTH, Constants.TILE_BOARD_HEIGHT);
        pane.setTranslateX((double) Constants.APP_WIDTH / 2);
        pane.setTranslateY((double) (Constants.TILE_BOARD_HEIGHT / 2) + Constants.INFO_CENTER_HEIGHT);

        addAllTiles();
    }

    private void addAllTiles() {

        int size = Constants.SIZE;
        for (int row = 0; row < size; row ++) {
            for(int col = 0; col < size; col++) {
                Label label = new Label("");
                label.setAlignment(Pos.CENTER);
                label.setFont(Font.font(24));
                Tile tile = new Tile(row, col);
                tile.getStackPane().setTranslateX((col * 60) - 210);
                tile.getStackPane().setTranslateY((row * 60) - 210);
                pane.getChildren().add(tile.getStackPane());
            }
        }
    }



    public StackPane getStackPane() {
        return pane;
    }

    private static class Tile {
        private final StackPane pane;

        private Tile(int row, int col) {
            pane = new StackPane();
            pane.setMinSize(60, 60);
            Rectangle border = new Rectangle();
            border.setWidth(60);
            border.setHeight(60);
            if ((row + col) % 2 == 1) border.setFill(Color.rgb(84, 137, 28));
            else border.setFill(Color.rgb(255, 239, 188));
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);
        }

        private StackPane getStackPane() {
            return pane;
        }
    }
}
