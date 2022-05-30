package Checkers.logic;

import static java.lang.Math.max;

class Repeater {
    private int result;
    private int activeCheckerRow;
    private int activeCheckerCol;
    private int i;
    private int j;

    public Repeater() {
    }

    public void in(int activeCheckerRow, int activeCheckerCol) {
        this.activeCheckerRow = activeCheckerRow;
        this.activeCheckerCol = activeCheckerCol;
        result = 0;
    }

    public void repeatRightDown(VerifierTurns verifierTurns) {
        i = activeCheckerRow;
        j = activeCheckerCol;
        while (result != 2 && i != 7 && j != 7) {
            i++;
            j++;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void repeatRightUp(VerifierTurns verifierTurns) {
        i = activeCheckerRow;
        j = activeCheckerCol;
        while (result != 2 && i != 0 && j != 7) {
            i--;
            j++;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void repeatLeftUp(VerifierTurns verifierTurns) {
        i = activeCheckerRow;
        j = activeCheckerCol;
        while (result != 2 && i != 0 && j != 0) {
            i--;
            j--;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void repeatLeftDown(VerifierTurns verifierTurns) {
        i = activeCheckerRow;
        j = activeCheckerCol;
        while (result != 2 && i != 7 && j != 0) {
            i++;
            j--;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public int getResult() {
        return result;
    }
}
