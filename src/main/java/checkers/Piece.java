package checkers;

import static checkers.Checkers.TILE_SIZE;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {
    private PieceType pieceType;

    public PieceType getPieceType() {
        return pieceType;
    }

    public Piece(PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;
        relocate(x * TILE_SIZE, y * TILE_SIZE);
        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(pieceType == PieceType.RED ? Color.RED : Color.WHITE);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().add(ellipse);
    }

}