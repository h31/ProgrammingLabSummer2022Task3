package checkers.logic;

import checkers.ui.CheckersBoard;

public class GameSituation {

    public static String playerTurn = "Black";

    static boolean someToEatAllWhite = false;
    static boolean someToEatAllBlack = false;

    static int cntBlack = 12;
    static int cntWhite = 12;

    public static void initRestart() {
        cntWhite = 12;
        cntBlack = 12;
        someToEatAllBlack = false;
        someToEatAllWhite = false;
    }

    public static void declareWinner() {
        if (cntBlack == 0) {
            CheckersBoard.isGame = false;
            Turner.infoCenter.updateMessage("White Won!!!");
        }
        if (cntWhite == 0) {
            CheckersBoard.isGame = false;
            Turner.infoCenter.updateMessage("Black Won!!!");
        }

        if (!CheckersBoard.isGame) {
            Turner.infoCenter.showStartButton();
        }
    }

    public static void declareDraw(boolean itIsDraw) {
        if (itIsDraw) {
            CheckersBoard.isGame = false;
            Turner.infoCenter.updateMessage("Draw!!!");
        }

        if (!CheckersBoard.isGame) {
            Turner.infoCenter.showStartButton();
        }
    }
}
