package checkers.logic;

import checkers.ui.*;

import static checkers.logic.GameStatistic.activePlayerSide;
import static checkers.logic.Turner.*;
import static checkers.ui.Constants.SIDES;

public class Utils {

    private static final InfoCenter infoCenter = Turner.infoCenter;

    public static void changePlayerTurn() {
        if (activePlayerSide.equals(SIDES.black)) {
            activePlayerSide = SIDES.white;
        } else {
            activePlayerSide = SIDES.black;
        }
        activeCheckerChoosed = false;
        mustContinueEat = false;

        String out = activePlayerSide.toString().substring(0, 1).toUpperCase() +
                activePlayerSide.toString().substring(1);

        if (!GameStatistic.isTest)
            infoCenter.updateMessage(out + "'s turn");
    }

    public static void highlight(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.Highlight();
    }

    public static void removeHighlight(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.unHighlight();
    }

    public static void deleteChecker(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.side = SIDES.no;
        checker.mustEat = false;
        checker.king = false;
        checker.clearGraphic();
    }

    public static void makeKing(int row, int col) {
        CheckersBoard.Checker checker = Turner.checkers[row][col];
        checker.king = true;
        checker.renderKing();
    }
}
