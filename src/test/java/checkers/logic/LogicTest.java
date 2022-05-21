package checkers.logic;

import checkers.ui.Piece;
import checkers.ui.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static checkers.logic.Logic.*;
import static checkers.logic.Logic.getBoard;
import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    static Tile[][] board = new Tile[Logic.WIDTH][Logic.WIDTH];
    static boolean turn;

    @BeforeAll
    static void setUp() {
        turn = false;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                board[x][y] = tile;

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.BLACK, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = new Piece(Piece.PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                }

            }
        }

        setBoard(board);
    }

    @Test
    void rightColor() {
        assertEquals(board[0][1].getPiece().getPieceType(), Piece.PieceType.BLACK);
        assertEquals(board[0][7].getPiece().getPieceType(), Piece.PieceType.WHITE);
    }
}