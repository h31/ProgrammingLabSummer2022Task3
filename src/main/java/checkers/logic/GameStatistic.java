package checkers.logic;

import checkers.ui.CheckersBoard;
import checkers.ui.Constants.SIDES;

public class GameStatistic {

    public static SIDES activePlayerSide = SIDES.black;

    static boolean whiteMustEat = false;
    static boolean blackMustEat = false;
    public static boolean isTest = false;

    public static int cntBlack = 12;
    public static int cntWhite = 12;

    public static void initRestart() {
        cntWhite = 12;
        cntBlack = 12;
        blackMustEat = false;
        whiteMustEat = false;
        activePlayerSide = SIDES.black;
    }

    public static void declareWinner() {
        if (cntBlack == 0) {
            CheckersBoard.isGame = false;
            if (!isTest) Turner.infoCenter.updateMessage("White Won!!!");
        }
        if (cntWhite == 0) {
            CheckersBoard.isGame = false;
            if (!isTest) Turner.infoCenter.updateMessage("Black Won!!!");
        }

        if (!CheckersBoard.isGame) {
            if (!isTest) Turner.infoCenter.showStartButton();
        }
    }

    public static void declareDraw(boolean itIsDraw) {
        if (itIsDraw && CheckersBoard.isGame) {
            CheckersBoard.isGame = false;
            if (!isTest) Turner.infoCenter.updateMessage("Draw!!!");
        }

        if (!CheckersBoard.isGame) {
            if (!isTest) Turner.infoCenter.showStartButton();
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
