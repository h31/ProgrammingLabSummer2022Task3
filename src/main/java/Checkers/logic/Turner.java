package Checkers.logic;

import javafx.scene.paint.Color;
import Checkers.UI.CheckBoard.*;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Turner {
    int checkerTurnRow;
    int checkerTurnCol;
    int row;
    int col;
    Color color;
    Color checkerTurnColor;
    Color checkerEnemyColor;

    public void initEnemyColor(Color x) {
        if (x.equals(Color.GREEN)) this.checkerEnemyColor = Color.RED;
        else this.checkerEnemyColor = Color.GREEN;

    }

    public Turner(int checkerTurnRow, int checkerTurnCol, int row, int col, Color color, Color checkerTurnColor) {
        this.col = col;
        this.row = row;
        this.color = color;
        this.checkerTurnCol = checkerTurnCol;
        this.checkerTurnRow = checkerTurnRow;


    }

    public byte checkTurn(Check[][] checks) {
        this.checkerTurnColor = checks[checkerTurnRow][checkerTurnCol].color;
        initEnemyColor(checkerTurnColor);
        int difRow = abs(row - checkerTurnRow);
        int difCol = abs(col - checkerTurnCol);
        if (checkerTurnColor.equals(Color.GREEN) && color.equals(Color.TRANSPARENT)) {
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
                if (checks[row][col].color.equals(checkerEnemyColor)) return 2;
            }

        } else if (checkerTurnColor.equals(Color.RED) && color.equals(Color.TRANSPARENT)) {
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
                if (checks[row][col].color.equals(checkerEnemyColor)) return 2;
            }
        }
        return 0;
    }

    public boolean checkAll(Check[][] checks, int checkerTurnCol, int checkerTurnRow) {
        boolean result = false;
        this.checkerTurnRow = checkerTurnRow;
        this.checkerTurnCol = checkerTurnCol;
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
        return result;
    }

    public int eatenRow() {
        return row;
    }

    public int eatenCol() {
        return col;
    }

}
