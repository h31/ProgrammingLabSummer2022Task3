package checkers.logic;

import checkers.ui.CheckersBoard;
import checkers.ui.Constants.SIDES;

public class GameStatistic {

    public static SIDES activePlayerSide = SIDES.black;

    static boolean whiteCanEat = false;
    static boolean blackCanEat = false;
    public static boolean thatForTests = false;

    public static int cntBlack = 12;
    public static int cntWhite = 12;

    public static void initRestart() {
        cntWhite = 12;
        cntBlack = 12;
        blackCanEat = false;
        whiteCanEat = false;
        activePlayerSide = SIDES.black;
    }

    public static void declareWinner() {
        if (cntBlack == 0) {
            CheckersBoard.isGame = false;
            if (!thatForTests)
                Turner.infoCenter.updateMessage("White Won!!!");
        }
        if (cntWhite == 0) {
            CheckersBoard.isGame = false;
            if (!thatForTests)
                Turner.infoCenter.updateMessage("Black Won!!!");
        }

        if (!CheckersBoard.isGame) {
            if (!thatForTests)
                Turner.infoCenter.showStartButton();
        }
    }

    public static void declareDraw(boolean itIsDraw) {
        if (itIsDraw) {
            CheckersBoard.isGame = false;
            if (!thatForTests)
                Turner.infoCenter.updateMessage("Draw!!!");
        }

        if (!CheckersBoard.isGame) {
            if (!thatForTests)
                Turner.infoCenter.showStartButton();
        }
    }

    static void changeScore(int row, int col) {
        if (CheckersBoard.checkers[row][col].side.equals(SIDES.black)) {
            GameStatistic.cntBlack--;
        } else {
            GameStatistic.cntWhite--;
        }
    }
}
