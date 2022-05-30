package Checkers.logic;

import Checkers.UI.CheckBoard;

import static java.lang.Math.abs;

public class VerifierTurns {
    private final CheckBoard.Check[][] checkers = CheckBoard.checks;
    private int activeCheckerRow;
    private int activeCheckerCol;
    private String activeCheckerColor;
    private boolean activeCheckerIsKing;
    private String enemyCheckerColor;
    private int cnt;
    private final Repeater repeater = new Repeater();

    private int eatenCheckerRow;
    private int eatenCheckerCol;



    public VerifierTurns() {
    }



    public void init(int activeCheckerRow, int activeCheckerCol) {

        this.activeCheckerRow = activeCheckerRow;
        this.activeCheckerCol = activeCheckerCol;
        this.activeCheckerColor = checkers[activeCheckerRow][activeCheckerCol].color;
        this.activeCheckerIsKing = checkers[activeCheckerRow][activeCheckerCol].isKing;

        initEnemyColor(activeCheckerColor);
    }



    private void initEnemyColor(String activeCheckerColor) {
        if (activeCheckerColor.equals("White")) enemyCheckerColor = "Black";
        else enemyCheckerColor = "White";
    }



    public int checkTurn(int selectedCellRow, int selectedCellCol) {

        int difRow = abs(selectedCellRow - activeCheckerRow);
        int difCol = abs(selectedCellCol - activeCheckerCol);
        String selectedCellColor = checkers[selectedCellRow][selectedCellCol].color;


        if (selectedCellColor.equals("No") && difRow == difCol) {
            int dif = difRow;
            difRow = (selectedCellRow - activeCheckerRow) / difRow;
            difCol = (selectedCellCol - activeCheckerCol) / difCol;
            int i = activeCheckerRow;
            int j = activeCheckerCol;

            if (activeCheckerIsKing) {
                int cntEnemies = 0;
                while (i != selectedCellRow) {
                    i += difRow;
                    j += difCol;
                    if (!checkers[i][j].color.equals("No")) {
                        if (checkers[i][j].color.equals(enemyCheckerColor)) {
                            cntEnemies++;
                            eatenCheckerRow = i;
                            eatenCheckerCol = j;
                        } else {
                            return 0; //This turn is impossible
                        }
                    }
                }
                return switch (cntEnemies) {
                    case (0) -> 1; //You can make a turn, but it is not eat
                    case (1) -> 2; //You can eat
                    default -> 0; //This turn is impossible
                };

            } else {
                switch (dif) {
                    case (1): {
                        if (activeCheckerColor.equals("White") && selectedCellRow < activeCheckerRow ||
                                activeCheckerColor.equals("Black") && selectedCellRow > activeCheckerRow) {
                            return 1; //You can make a turn, but it is not eat
                        }
                    }
                    case (2): {
                        i += difRow;
                        j += difCol;
                        if (checkers[i][j].color.equals(enemyCheckerColor)) {
                            eatenCheckerRow = i;
                            eatenCheckerCol = j;
                            return 2; //You can eat
                        }
                    }
                    default: {
                        return 0; //This turn is impossible
                    }
                }
            }
        }

        return 0; //This turn is impossible
    }


    public int checkAllTurns() {
        repeater.in(activeCheckerRow, activeCheckerCol);
        if (activeCheckerIsKing) {
            repeater.repeatRightUp(this);
            repeater.repeatLeftUp(this);
            repeater.repeatLeftDown(this);
            repeater.repeatRightDown(this);
        } else {
            while (cnt != 2) {
                cnt++;
                repeater.repeatRightUp(this);
            }
            cnt = 0;
            while (cnt != 2) {
                cnt++;
                repeater.repeatLeftUp(this);
            }
            cnt = 0;
            while (cnt != 2) {
                cnt++;
                repeater.repeatLeftDown(this);
            }
            cnt = 0;
            while (cnt != 2) {
                cnt++;
                repeater.repeatRightDown(this);
            }
            cnt = 0;
        }
        return repeater.getResult();
    }

    public boolean checkForTurns() {
        checkAllTurns();
        return checkAllTurns() != 0;
    }

    public boolean checkForTakes() {
        checkAllTurns();
        return checkAllTurns() == 2;
    }

    public int getEatenRow() {
        return eatenCheckerRow;
    }

    public int getEatenCol() {
        return eatenCheckerCol;
    }
}
