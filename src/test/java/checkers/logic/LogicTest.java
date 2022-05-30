package checkers.logic;

import checkers.ui.Piece;
import checkers.ui.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static checkers.logic.Logic.*;
import static checkers.logic.Logic.getBoard;
import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    static Tile[][] board = new Tile[Logic.WIDTH][Logic.WIDTH];


    @BeforeEach
    void setUp() {
        setTurn(Piece.PieceType.WHITE);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
                Tile tile = new Tile();
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
        setKillCount(0);
    }

    @Test
    void rightColor() {
        assertEquals(board[0][1].getPiece().getPieceType(), Piece.PieceType.BLACK);
        assertEquals(board[0][7].getPiece().getPieceType(), Piece.PieceType.WHITE);
        assertEquals(board[4][5].getPiece().getPieceType(), Piece.PieceType.WHITE);
    }

    @Test
    void legalMove() {
        MoveResult result = tryMove(board[4][5].getPiece(), 5, 4);
        assertEquals(MoveType.NORMAL, result.getMoveType());
        move(board[4][5].getPiece(), 5, 4, result);
        assertTrue(board[5][4].hasPiece());
        assertFalse(board[4][5].hasPiece());

    }

    @Test
    void eatMove() {
        move(board[7][2].getPiece(), 6, 3, new MoveResult(MoveType.NORMAL));
        move(board[4][5].getPiece(),5,4,new MoveResult(MoveType.NORMAL));
        MoveResult result = tryMove(board[5][4].getPiece(), 7, 2);
        assertEquals(MoveType.KILL, result.getMoveType());
    }
}