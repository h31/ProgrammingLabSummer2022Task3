package checkers.ui;

import static checkers.logic.Logic.*;
import static checkers.ui.ContentCreator.*;

public class BoardPainter {
    public static void boardPainter() {
        //Отрисовка начальной доски
        getPieceGroup().getChildren().clear();
        getTileGroup().getChildren().clear();

        setKillNeed(false);
        getUnderTopText().setText("");
        getLeft().getChildren().clear();
        getRight().getChildren().clear();


        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                getBoard()[x][y] = tile;

                getTileGroup().getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.BLACK, x, y);
                    amountOfPieces++;
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    getPieceGroup().getChildren().add(piece);
                }
            }
        }
    }
}
