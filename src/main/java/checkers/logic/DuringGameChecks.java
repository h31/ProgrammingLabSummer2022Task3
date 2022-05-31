package checkers.logic;

import checkers.UI.CheckersBoard;

public class DuringGameChecks {
    public static boolean someToEatAllWhite = false;
    public static boolean someToEatAllBlack = false;

    public static int cntBlack = 12;
    public static int cntWhite = 12;

    public static void checkForWinner() {
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

    public static void checkForDraw(boolean isThatNoDraw) {
        if (!isThatNoDraw) {
            CheckersBoard.isGame = false;
            Turner.infoCenter.updateMessage("Draw!!!");
        }
    }
}
