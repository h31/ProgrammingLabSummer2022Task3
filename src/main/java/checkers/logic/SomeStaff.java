package checkers.logic;

import checkers.UI.CheckersBoard;
import checkers.UI.InfoCenter;
import checkers.UI.UIConstants;

public class SomeStaff {
    public static String playerTurn = "Black";
    public static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (playerTurn.equals("Black")) {
            playerTurn = "White";
        } else {
            playerTurn = "Black";
        }
        infoCenter.updateMessage(playerTurn + "'s turn");
        Turner.isTurn = false;
        Turner.resultOfLastMove = 0;
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void inline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.labelUp.setBackground(UIConstants.CHOOSEN_CHECKER);
    }

    public static void unline(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        if (checker.color.equals("White")){
            checker.labelUp.setBackground(UIConstants.WHITE_CHECKER);
        } else {
            checker.labelUp.setBackground(UIConstants.BLACK_CHECKER);
        }
    }

    public static void delete(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.labelUp.setBackground(UIConstants.NO_CHECKER);
        checker.labelKing.setBackground(UIConstants.NO_CHECKER);
        checker.labelDown.setBackground(UIConstants.NO_CHECKER);
        checker.color = "No";
        checker.someToEat = false;
    }

    public static void makeAKing(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.labelKing.setBackground(UIConstants.KING);
        checker.isKing = true;
    }
}
