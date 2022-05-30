package Checkers.logic;

import Checkers.UI.CheckBoard;
import Checkers.UI.InfoCenter;
import Checkers.UI.UIConstants;

import java.util.Objects;

public class SomeStaff {
    public static String playerTurn = "Black";
    public static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (playerTurn.equals("Black")) {
            playerTurn = "White";
        } else {
            playerTurn = "Black";
        }
        infoCenter.updateMessage("Player " + playerTurn + "'s turn");
        Turner.isTurn = false;
        Turner.resultOfLastTurn = 0;
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void inline(int row, int col) {
        CheckBoard.Checker checker = Turner.checkers[row][col];
        checker.label.setBackground(UIConstants.BACK_GOLD);
    }

    public static void unline(int row, int col) {
        CheckBoard.Checker checker = Turner.checkers[row][col];
        if (checker.color.equals("White")){
            checker.label.setBackground(UIConstants.BACK_WHITE);
        } else {
            checker.label.setBackground(UIConstants.BACK_BLACK);
        }
    }

    public static void delete(int row, int col) {
        CheckBoard.Checker checker = Turner.checkers[row][col];
        checker.label.setBackground(UIConstants.BACK_NO);
        checker.label.setText("");
        checker.color = "No";
        checker.someToEat = false;
    }

    public static void makeAKing(int row, int col) {
        CheckBoard.Checker checker = Turner.checkers[row][col];
        checker.label.setText("X");
        checker.isKing = true;
    }
}
