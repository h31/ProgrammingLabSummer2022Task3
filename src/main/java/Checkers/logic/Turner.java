package Checkers.logic;

import javafx.scene.paint.Color;
import Checkers.UI.CheckBoard.*;

import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Turner {
    int checkerTurnRow;
    int checkerTurnCol;
    int row;
    int col;
    String color;
    String checkerTurnColor;
    String checkerEnemyColor;
    int cnt = 0;
    int col1 = -1;
    int row1 = -1;
    boolean isKing;

    public void initEnemyColor(String x) {
        if (x.equals("White")) this.checkerEnemyColor = "Black";
        else this.checkerEnemyColor = "White";

    }

    public Turner(int checkerTurnRow, int checkerTurnCol, int row, int col, String color, String checkerTurnColor) {
        this.col = col;
        this.row = row;
        this.color = color;
        this.checkerTurnCol = checkerTurnCol;
        this.checkerTurnRow = checkerTurnRow;


    }

    public byte checkTurn(Check[][] checks) {
        this.isKing = checks[checkerTurnRow][checkerTurnCol].isKing;
        this.checkerTurnColor = checks[checkerTurnRow][checkerTurnCol].color;
        initEnemyColor(checkerTurnColor);
        int difRow = abs(row - checkerTurnRow);
        int difCol = abs(col - checkerTurnCol);
        if (!isKing) {
            if (checkerTurnColor.equals("White") && color.equals("No")) {
                if (row < checkerTurnRow && difCol == 1 && difRow == 1) return 1;
                if (difCol == 2 && difRow == 2) {
                    if (row > checkerTurnRow) {
                        row--;
                    } else {
                        row++;
                    }
                    if (col > checkerTurnCol) {
                        col--;
                    } else {
                        col++;
                    }
                    row1 = row;
                    col1 = col;
                    if (checks[row][col].color.equals(checkerEnemyColor)) return 2;
                }

            } else if (checkerTurnColor.equals("Black") && color.equals("No")) {
                if (row > checkerTurnRow && difCol == 1 && difRow == 1) return 1;
                if (difCol == 2 && difRow == 2) {
                    if (row > checkerTurnRow) {
                        row--;
                    } else {
                        row++;
                    }
                    if (col > checkerTurnCol) {
                        col--;
                    } else {
                        col++;
                    }
                    row1 = row;
                    col1 = col;
                    if (checks[row][col].color.equals(checkerEnemyColor)) return 2;
                }
            }
        } else {
            if (difCol == difRow && checks[row][col].color.equals("No")) {
                cnt = 0;
                difRow = (row - checkerTurnRow) / difRow;
                difCol = (col - checkerTurnCol) / difCol;
                int i = checkerTurnRow;
                int j = checkerTurnCol;
                while (i != row) {
                    i += difRow;
                    j += difCol;
                    if (!checks[i][j].color.equals("No")) {
                        if (checks[i][j].color.equals(checkerEnemyColor)) {
                            cnt++;
                            row1 = i;
                            col1 = j;
                            if (cnt > 1) return 0;
                        }
                    }
                }
                if (cnt == 1) return 2;
                return 1;
            }
        }
        return 0;
    }

    public boolean checkAll(Check[][] checks, int checkerTurnCol, int checkerTurnRow) {
        boolean result = false;
        this.isKing = checks[checkerTurnRow][checkerTurnCol].isKing;
        this.checkerTurnRow = checkerTurnRow;
        this.checkerTurnCol = checkerTurnCol;
        if (!isKing) {
            try {
                row = checkerTurnRow + 2;
                col = checkerTurnCol + 2;
                color = checks[row][col].color;
                result = result || checkTurn(checks) == 2;
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                row = checkerTurnRow + 2;
                col = checkerTurnCol - 2;
                color = checks[row][col].color;
                result = result || checkTurn(checks) == 2;
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                row = checkerTurnRow - 2;
                col = checkerTurnCol + 2;
                color = checks[row][col].color;
                result = result || checkTurn(checks) == 2;
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                row = checkerTurnRow - 2;
                col = checkerTurnCol - 2;
                color = checks[row][col].color;
                result = result || checkTurn(checks) == 2;
            } catch (IndexOutOfBoundsException ignored) {
            }
        } else {
            row = checkerTurnRow;
            col = checkerTurnCol;
            color = checks[row][col].color;
            while (row != 7 && col != 7) {
                row++;
                col++;
                result = result || checkTurn(checks) == 2;
            }
            row = checkerTurnRow;
            col = checkerTurnCol;
            color = checks[row][col].color;
            while (row != 7 && col != 0) {
                row++;
                col--;
                result = result || checkTurn(checks) == 2;
            }
            row = checkerTurnRow;
            col = checkerTurnCol;
            color = checks[row][col].color;
            while (row != 0 && col != 7) {
                row--;
                col++;
                result = result || checkTurn(checks) == 2;
            }
            row = checkerTurnRow;
            col = checkerTurnCol;
            color = checks[row][col].color;
            while (row != 0 && col != 0) {
                row--;
                col--;
                result = result || checkTurn(checks) == 2;
            }
        }
        return result;
    }

    public int eatenRow() {
        return row1;
    }

    public int eatenCol() {
        return col1;
    }

}
