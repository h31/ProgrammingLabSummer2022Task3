package checkers.logic;

import checkers.ui.Piece;

import static checkers.logic.Logic.*;
import static checkers.ui.BoardPainter.boardPainter;
import static checkers.ui.ChangeContent.changingTurn;
import static checkers.ui.ConfirmBox.confirmation;

public class Scanning {

    /**
     *
     * @param piece шашка и её координаты
     * @return Можно съесть кого-то этой шашкой
     * Проверяет ОДНУ шашку
     */
    public static boolean canKill(Piece piece, int x, int y) { //Проверяет возможность съесть кого-то
        if (!piece.isCrown()) {
            return tryMove(piece, x + 2, y + 2).getMoveType() == MoveType.KILL ||
                    tryMove(piece, x + 2, y - 2).getMoveType() == MoveType.KILL ||
                    tryMove(piece, x - 2, y - 2).getMoveType() == MoveType.KILL ||
                    tryMove(piece, x - 2, y + 2).getMoveType() == MoveType.KILL;
        } else {
            for (int i = 1; i < HEIGHT; i++) {
                if (tryMove(piece, x + i, y + i).getMoveType() == MoveType.KILL ||
                        tryMove(piece, x + i, y - i).getMoveType() == MoveType.KILL ||
                        tryMove(piece, x - i, y + i).getMoveType() == MoveType.KILL ||
                        tryMove(piece, x - i, y - i).getMoveType() == MoveType.KILL) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Применяет canKill на ВСЕХ шашках
     * Проверка нужна, так как есть шашки обязательно
     */
    public static void anyThreat() { //Проверяет может ли шашка съесть кого-то в этом ходу
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Piece piece;
                if (board[x][y].hasPiece()) {
                    piece = board[x][y].getPiece();
                    if (canKill(piece, x, y)
                            && (piece.getPieceType() == turn)) {
                        setKillNeed(true);

                        piece.setKiller(true);

                    } else piece.setKiller(false);
                }
            }
        }
    }

    /**
     *
     * @param piece - шашка
     * @param x Координата
     * @param y Координата
     * @return Может ли ОДНА конкретная шашка двигаться
     */
    public static boolean canMove(Piece piece, int x, int y) {
        if (!piece.isCrown()) {
            return tryMove(piece, x + 1, y + piece.getPieceType().moveDir).getMoveType() == MoveType.NORMAL ||
                    tryMove(piece, x - 1, y + piece.getPieceType().moveDir).getMoveType() == MoveType.NORMAL;

        } else {
            for (int i = 1; i < HEIGHT; i++) {
                if (tryMove(piece, x + i, y + i).getMoveType() == MoveType.NORMAL ||
                        tryMove(piece, x + i, y - i).getMoveType() == MoveType.NORMAL ||
                        tryMove(piece, x - i, y + i).getMoveType() == MoveType.NORMAL ||
                        tryMove(piece, x - i, y - i).getMoveType() == MoveType.NORMAL)
                    return true;
            }
        }
        //Если до этого не вернуло true и ещё и есть не должна шашка, то игра закончена.
        //Так как ходящая сторона обездвижена
        return canKill(piece,x,y);
    }

    /**
     * Применяет canMove на всех шашках. Если шашки
     * в свой ход не могут двигаться - поражение
     */
    public static void allowedMovements() {
        boolean gameOver = true;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Piece piece;
                if (board[x][y].hasPiece()) {
                    piece = board[x][y].getPiece();
                    if ((canMove(piece, x, y)  ||
                            canKill(piece, x, y)) && (piece.getPieceType() == turn)) {
                        gameOver = false;

                    }
                }
            }
        }
        if (gameOver) {
            String message = turn == Piece.PieceType.BLACK ? "\n      Поражение чёрных" :
                    "\n      Поражение белых";
            if (confirmation("Движение невозможно. Хотите перезапустить игру?", message)) {
                setTurn(Piece.PieceType.WHITE); //Чтобы при перезапуске белые ходили всегда первыми
                boardPainter();
                changingTurn();
                getStepsStack().clear();
            }
        }
    }
}
