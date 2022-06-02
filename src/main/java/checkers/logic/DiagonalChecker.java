package checkers.logic;


import static checkers.ui.Constants.MOVERESULT;

class DiagonalChecker {
    private MOVERESULT result;
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
        result = MOVERESULT.itNotPossible;
    }

    public void checkRightDown(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (!result.equals(MOVERESULT.itEat) && i != 7 && j != 7 && cnt != 2) {
            cnt++;
            i++;
            j++;
            MOVERESULT x = verifierTurns.checkTurn(i, j);
            if (!x.equals(MOVERESULT.itNotPossible)) result = x;
        }
    }

    public void checkRightUp(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (!result.equals(MOVERESULT.itEat) && i != 0 && j != 7 && cnt != 2) {
            cnt++;
            i--;
            j++;
            MOVERESULT x = verifierTurns.checkTurn(i, j);
            if (!x.equals(MOVERESULT.itNotPossible)) result = x;
        }
    }

    public void checkLeftUp(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (!result.equals(MOVERESULT.itEat) && i != 0 && j != 0 && cnt != 2) {
            cnt++;
            i--;
            j--;
            MOVERESULT x = verifierTurns.checkTurn(i, j);
            if (!x.equals(MOVERESULT.itNotPossible)) result = x;
        }
    }

    public void checkLeftDown(VerifierTurns verifierTurns) {
        cnt = 0;
        i = activeRow;
        j = activeCol;
        while (!result.equals(MOVERESULT.itEat) && i != 7 && j != 0 && cnt != 2) {
            cnt++;
            i++;
            j--;
            MOVERESULT x = verifierTurns.checkTurn(i, j);
            if (!x.equals(MOVERESULT.itNotPossible)) result = x;
        }
    }

    public MOVERESULT getResult() {
        return result;
    }
}
