package checkers.logic;

import checkers.ui.*;

import static java.lang.Math.abs;
import static checkers.ui.Constants.SIDES;

public class VerifierTurns {
    private final CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    private int activeRow;
    private int activeCol;
    private boolean activeCheckerKing;
    private SIDES enemySide;
    private int capturedRow;
    private int capturedCol;
    private final DiagonalChecker diagonalChecker = new DiagonalChecker();
    private SIDES activeSide;



    public VerifierTurns() {
    }



    public void init(int activeCheckerRow, int activeCheckerCol) {
        this.activeRow = activeCheckerRow;
        this.activeCol = activeCheckerCol;
        this.activeCheckerKing = checkers[activeCheckerRow][activeCheckerCol].king;
        this.activeSide = checkers[activeCheckerRow][activeCheckerCol].side;

        enemySide = activeSide.equals(SIDES.white) ? SIDES.black : SIDES.white;
    }



    public int checkTurn(int selectedRow, int selectedCol) {
        int difRow = abs(selectedRow - activeRow);
        int difCol = abs(selectedCol - activeCol);
        SIDES selectedSide = checkers[selectedRow][selectedCol].side;

        if (selectedSide.equals(SIDES.no) && difRow == difCol) {
            int dif = difRow;
            difRow = (selectedRow - activeRow) / difRow;
            difCol = (selectedCol - activeCol) / difCol;
            int i = activeRow + difRow;
            int j = activeCol + difCol;

            if (activeCheckerKing || directionRightForActiveColor(selectedRow)) {
                switch (dif) {
                    case (1) -> {
                        return 1; //просто ход
                    }
                    case (2) -> {
                        if (checkers[i][j].side.equals(enemySide)) {
                            capturedRow = i;
                            capturedCol = j;
                            return 2; //в результате будет взятие
                        }
                    }
                }
            }
        }

        return 0; //This turn is impossible
    }

    private boolean directionRightForActiveColor(int selectedCellRow) {
        return selectedCellRow < activeRow && activeSide.equals(SIDES.white) ||
                selectedCellRow > activeRow && activeSide.equals(SIDES.black);
    }


    private int isAnyTurnAvailable() {
        diagonalChecker.init(activeRow, activeCol);
        diagonalChecker.checkRightUp(this);
        diagonalChecker.checkLeftUp(this);
        diagonalChecker.checkLeftDown(this);
        diagonalChecker.checkRightDown(this);
        return diagonalChecker.getResult();
    }

    public boolean moveOrEatAvailable() {
        return isAnyTurnAvailable() != 0;
    }

    public boolean eatAvailable() {
        return isAnyTurnAvailable() == 2;
    }

    public int getCapturedRow() {
        return capturedRow;
    }

    public int getCapturedCol() {
        return capturedCol;
    }


}
