package checkers.logic;

import checkers.ui.*;

import static checkers.logic.GameSituation.playerTurn;
import static checkers.logic.Turner.activeCheckerChoosed;
import static checkers.logic.Turner.resultOfLastMove;

public class Utils {

    public static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (playerTurn.equals("Black")) {
            playerTurn = "White";
        } else {
            playerTurn = "Black";
        }
        activeCheckerChoosed = false;
        resultOfLastMove = 0;

        infoCenter.updateMessage(playerTurn + "'s turn");
    }

    public static boolean isWhiteTurn() {
        return playerTurn.equals("White");
    }

    public static void highlight(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.highlight();
    }

    public static void removeHighlight(int row, int col) {
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
