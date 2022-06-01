package checkers.logic;


import checkers.ui.Piece;
import checkers.ui.Tile;

import java.util.ArrayDeque;
import java.util.Deque;

import static checkers.logic.Scanning.canKill;
import static checkers.ui.BoardPainter.boardPainter;
import static checkers.ui.ConfirmBox.confirmation;
import static checkers.ui.ChangeContent.changingTurn;

public class Logic {
    public static final int TILE_SIZE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    static Tile[][] board = new Tile[WIDTH][HEIGHT];
    public static int amountOfPieces;


    private static final Deque<Step> stepsStack = new ArrayDeque<>();


    public static Piece.PieceType turn = Piece.PieceType.WHITE;
    /**
     * Какая-то из шашек должна съесть ещё
     */
    private static boolean killNeed = false;
    private static int killCount;
    public static Tile[][] getBoard() {
        return board;
    }
    public static void setBoard(Tile[][] board) {
        Logic.board = board;
    }
    public static void setTurn(Piece.PieceType turn) {
        Logic.turn = turn;
    }
    public static Deque<Step> getStepsStack() {
        return stepsStack;
    }


    public static int getKillCount() {
        return killCount;
    }

    public static void setKillCount(int killCount) {
        Logic.killCount = killCount;
    }


    public static void setKillNeed(boolean b) {
        killNeed = b;
    }

    public static boolean isKillNeed() {
        return killNeed;
    }

    public static void switchTurn() {
        if (turn == Piece.PieceType.WHITE) {
            setTurn(Piece.PieceType.BLACK);
        } else {
            setTurn(Piece.PieceType.WHITE);
        }
    }

    static MoveResult tryMove(Piece piece, int newX, int newY) {
        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT) {
            return new MoveResult(MoveType.NONE);
        } //Избегаем проверок вне поля

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0
                || turn != piece.getPieceType()) {
            return new MoveResult(MoveType.NONE); //Неправильное движения 1) На клетке стоит шашка;
            // 2) Выбрана светлая клетка; 3) и 4) Несоблюдение очереди. Ходит другая сторона
        }

        if (getKillCount() > 0 && stepsStack.peek().getPiece() != piece) { //Если нужны убийства подряд и совершала их другая шашка
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getStartFromX());
        int y0 = toBoard(piece.getStartFromY());


        //Нормальная шашка
        if (!piece.isCrown()) {
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getPieceType().moveDir) {
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && Math.abs(newY - y0) == 2) {
                // x1 и y1 координаты "убитой" шашки. Запоминаем, чтобы стереть
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getPieceType() != piece.getPieceType()) {
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }
            }
        } else { //Дамка
            int piecesOnLineInA_Row = 0; //Количество шашек подряд на диагонали дамки
            int dx = newX - x0;
            int dy = newY - y0;

            int x1 = 0, y1 = 0; //Координаты возможной жертвы. Объявим позже, если найдем
            if (Math.abs(dx) != Math.abs(dy)) {
                return new MoveResult(MoveType.NONE);
            }

            for (int i = 1; i <= Math.abs(dx); i++) {
                int currX = (dx > 0) ? x0 + i : x0 - i; //Если вправо - наращиваем X, и наоборот
                int currY = (dy > 0) ? y0 + i : y0 - i; //Если вниз - наращиваем Y, и наоборот

                // Если на выбранной клетке шашка вашей команды, за неё вы 100% не перешагнете
                if (board[currX][currY].hasPiece() && board[currX][currY].getPiece().getPieceType() == piece.getPieceType()) {
                    return new MoveResult(MoveType.NONE);
                }

                if (board[currX][currY].hasPiece() && piecesOnLineInA_Row == 1) { //Две подряд шашки
                    return new MoveResult(MoveType.NONE);
                }
                if (!board[currX][currY].hasPiece() && piecesOnLineInA_Row == 1 && Math.abs(currX - x1) == 1 &&
                        Math.abs(currY - y1) == 1) {//За шашкой противника
                    // пустое место - шашка съедается. Также возвращаем координаты съеденной шашки
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }

                if (board[currX][currY].hasPiece() &&
                        board[currX][currY].getPiece().getPieceType() != piece.getPieceType()) {  //Считаем шашки врага,
                    // идущие подряд
                    piecesOnLineInA_Row++;
                    x1 = currX;
                    y1 = currY; //Координаты "потенциально убитой" шашки
                }
            }
            //Если на пройденной диагонали не было врагов - обычный ход
            if (piecesOnLineInA_Row == 0) return new MoveResult(MoveType.NORMAL);
            return new MoveResult(MoveType.NONE);
        }
        return new MoveResult(MoveType.NONE);
    }

    public static int toBoard(double pixel) { //Перевод координаты приложения в координаты поля
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    public static void move(Piece piece, int x, int y, MoveResult result) {
        if (result.getMoveType() != MoveType.NONE) {
            getBoard()[toBoard(piece.getStartFromX())][toBoard(piece.getStartFromY())].setPiece(null); // Очищаем предыдущую клетку
            getBoard()[x][y].setPiece(piece); //Ставим на новую
        }

        int y0 = toBoard(piece.getStartFromY());
        int x0 = toBoard(piece.getStartFromX());
        //Запоминаем расположение
        getStepsStack().push(new Step(x0, y0, result, piece));

        if (result.getMoveType() == MoveType.KILL) {
            //Очищаем клетку съеденной шашки
            getBoard()[toBoard(result.getPiece().getStartFromX())][toBoard(result.getPiece().getStartFromY())].setPiece(null);
        }
        //Отрисовка в новой позиции
        piece.drawer(x, y);

        //Проверяем не стала ли дамкой
        if (piece.getPieceType() == Piece.PieceType.BLACK && y == (HEIGHT - 1) && !piece.isCrown() ||
                piece.getPieceType() == Piece.PieceType.WHITE && y == 0 && !piece.isCrown()) {
            crownPiece(piece);
            result.setWasCrowned(true);
        }

    }

    public static void crownPiece(Piece piece) {
        piece.setCrown(true);
        piece.getCrownView().setVisible(true); //Стала дамкой = видно корону

    }


}
