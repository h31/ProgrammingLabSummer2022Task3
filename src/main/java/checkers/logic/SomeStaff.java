package checkers.logic;

import checkers.UI.*;

public class SomeStaff {
    public static String playerTurn;
    public static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (playerTurn.equals("Black")) {
            playerTurn = "White";
        } else {
            playerTurn = "Black";
        }
        Turner.isTurn = false;
        Turner.resultOfLastMove = 0;
        infoCenter.updateMessage(playerTurn + "'s turn");
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void inline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.inline();
    }

    public static void unline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.unline();
    }

    public static void delete(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.color = "No";
        checker.someToEat = false;
        checker.isKing = false;
        checker.clearGraphic();
    }

    public static void makeAKing(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.isKing = true;
        checker.makeAKingGraphic();
    }
}
