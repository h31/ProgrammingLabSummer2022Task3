package Checkers.logic;

import Checkers.UI.CheckBoard;
import Checkers.UI.InfoCenter;
import Checkers.UI.UIConstants;

import java.util.Objects;

public class SomeStaff {
    public static String playerTurn = "White";
    public static final InfoCenter infoCenter = Turner.infoCenter;
    public static void changePlayerTurn() {
        if (Objects.equals(playerTurn, "White")) {
            playerTurn = "Black";
        } else {
            playerTurn = "White";
        }
        infoCenter.updateMessage("Player " + playerTurn + "'s turn");
        Turner.isTurn = false;
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void inline(int row, int col) {
        CheckBoard.Check check = Turner.checkers[row][col];
        check.label.setBackground(UIConstants.backGo);
    }

    public static void unline(int row, int col) {
        CheckBoard.Check check = Turner.checkers[row][col];
        if (check.color.equals("White")){
            check.label.setBackground(UIConstants.backWhite);
        } else {
            check.label.setBackground(UIConstants.backBlack);
        }
    }

    public static void delete(int row, int col) {
        CheckBoard.Check check = Turner.checkers[row][col];
        check.label.setBackground(UIConstants.backNo);
        check.label.setText("");
        check.color = "No";
        check.someToEat = false;
    }

    public static void makeAKing(int row, int col) {
        CheckBoard.Check check = Turner.checkers[row][col];
        check.label.setText("X");
        check.isKing = true;
    }
}
