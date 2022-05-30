package Checkers.logic;

import Checkers.UI.CheckBoard;

public class DuringGameChecks {
    public static boolean someToEatAllWhite = false;
    public static boolean someToEatAllBlack = false;

    public static int cntBlack = 12;
    public static int cntWhite = 12;

    public static void checkForWinner() {
        if (cntBlack == 0) {
            Turner.infoCenter.updateMessage("White Won!!!");
            CheckBoard.isGame = false;
        }
        if (cntWhite == 0) {
            Turner.infoCenter.updateMessage("Black Won!!!");
            CheckBoard.isGame = false;
        }

    }

    public static void checkForDraw(boolean isThatNoDraw) {
        if (!isThatNoDraw) {
            Turner.infoCenter.updateMessage("Draw!!!");
            CheckBoard.isGame = false;
        }
    }
}
