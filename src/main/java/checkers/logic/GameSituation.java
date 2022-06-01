package checkers.logic;

import checkers.ui.CheckersBoard;
import checkers.ui.Constants.SIDES;

public class GameSituation {

    public static SIDES activePlayer = SIDES.black;

    static boolean whiteCanEat = false;
    static boolean blackCanEat = false;

    static int cntBlack = 12;
    static int cntWhite = 12;

    public static void initRestart() {
        cntWhite = 12;
        cntBlack = 12;
        blackCanEat = false;
        whiteCanEat = false;
        activePlayer = SIDES.black;
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
