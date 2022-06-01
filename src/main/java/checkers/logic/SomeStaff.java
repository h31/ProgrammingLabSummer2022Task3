package checkers.logic;

import checkers.ui.*;

public class SomeStaff {
    public static String playerTurn = "Black";
    public static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (playerTurn.equals("Black")) {
            playerTurn = "White";
        } else {
            playerTurn = "Black";
        }
        Turner.isTurn = false;
        Turner.resultOfLastMove = 0;
        try {
            infoCenter.updateMessage(playerTurn + "'s turn");
        } catch(NullPointerException ignored) {}
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void inline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.highlight();
    }

    public static void unline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.removeHighlight();
    }

    public static void delete(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.color = "No";
        checker.canEat = false;
        checker.isKing = false;
        checker.clearGraphic();
    }

    public static void makeAKing(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.isKing = true;
        checker.makeKingGraphic();
    }
}
