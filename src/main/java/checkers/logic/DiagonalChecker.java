package checkers.logic;

import static java.lang.Math.max;

class DiagonalChecker {
    private int result;
    private int activeRow;
    private int activeCol;
    private int i;
    private int j;
    private int cnt;

    public DiagonalChecker() {
    }

    public void init(int activeCheckerRow, int activeCheckerCol) {
        this.activeRow = activeCheckerRow;
        this.activeCol = activeCheckerCol;
        result = 0;
    }

    public void checkRightDown(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (result != 2 && i != 7 && j != 7 && cnt != 2) {
            cnt++;
            i++;
            j++;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void checkRightUp(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (result != 2 && i != 0 && j != 7 && cnt != 2) {
            cnt++;
            i--;
            j++;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void checkLeftUp(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (result != 2 && i != 0 && j != 0 && cnt != 2) {
            cnt++;
            i--;
            j--;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public void checkLeftDown(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (result != 2 && i != 7 && j != 0 && cnt != 2) {
            cnt++;
            i++;
            j--;
            result = max(result, verifierTurns.checkTurn(i, j));
        }
    }

    public int getResult() {
        return result;
    }
}
