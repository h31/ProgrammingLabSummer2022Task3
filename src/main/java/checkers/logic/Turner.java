package checkers.logic;

import checkers.ui.*;

public class Turner {
    public static int resultOfLastMove = 0;
    static InfoCenter infoCenter;
    public static boolean activeCheckerChoosed = false;
    static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    int selectedRow; //Ряд выбранной клетки
    int selectedCol; //Столбец выбранной клетки
    String selectedColor; //Цвет фигуры в выбранной клетке
    boolean selectedSomeToEat; //Может ли выбранная фигура съесть
    boolean selectedKing; //Является ли выбранная фигура королём
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    public int activeRow;
    public int activeCol;
    String activeColor;
    boolean activeSomeToEat;
    boolean activeKing;
    boolean itAttemptRelocateActiveChecker;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода

    static CheckersBoard.Checker selectedChecker;
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedCellRow, int selectedCellCol){
        selectedChecker = checkers[selectedCellRow][selectedCellCol];
        this.selectedRow = selectedChecker.row;
        this.selectedCol = selectedChecker.col;
        this.selectedColor = selectedChecker.color;
        this.selectedSomeToEat = selectedChecker.canEat;
        this.selectedKing = selectedChecker.isKing;


        if (activeCheckerChoosed) {
            itAttemptRelocateActiveChecker = true;
            if (resultOfLastMove != 2) {
                if (activeRow == selectedCellRow && activeCol == selectedCellCol) {
                    itAttemptRelocateActiveChecker = false;
                    canselChoose();
                } else if (activeColor.equals(selectedColor) && activeSomeToEat == selectedSomeToEat) {
                    itAttemptRelocateActiveChecker = false;
                    chooseAnotherChecker();
                }
            }
            if (itAttemptRelocateActiveChecker) {
                tryToMakeThisTurn();
                checkAfterTurn();
            }
        } else {
            if (Utils.isWhiteTurn() && selectedColor.equals("White") && (selectedSomeToEat ||
                    !GameSituation.someToEatAllWhite) || !Utils.isWhiteTurn() &&
                    selectedColor.equals("Black") && (selectedSomeToEat ||
                    !GameSituation.someToEatAllBlack)) {
                chooseActiveChecker();
            }
        }

    }



    private void chooseActiveChecker() {
        activeCheckerChoosed = true;
        activeRow = selectedRow;
        activeCol = selectedCol;
        activeColor = selectedColor;
        activeSomeToEat = selectedSomeToEat;
        activeKing = selectedKing;
        Utils.highlight(selectedRow, selectedCol);
    }

    private void canselChoose() {
        activeCheckerChoosed = false;

        Utils.removeHighlight(activeRow, activeCol);
    }

    private void chooseAnotherChecker() {
        Utils.removeHighlight(activeRow, activeCol);

        activeRow = selectedRow;
        activeCol = selectedCol;
        activeKing = selectedKing;

        Utils.highlight(selectedRow, selectedCol);
    }

    private void tryToMakeThisTurn() {
        if (selectedColor.equals("No")) {
            verifierTurns.init(activeRow, activeCol);
            int x = verifierTurns.checkTurn(selectedRow, selectedCol);
            if (x != 0) {
                if (x == 1 && !activeSomeToEat) { //если можно походить без взятия и взять шашка никого не может
                    selectedChecker.color = activeColor; //переназначаем цвет у пустого поля
                    selectedChecker.paintInNormalColor(activeColor); //перекрашиваем (передвигаем шашку)

                    if ((Utils.isWhiteTurn() && selectedRow == 0 || !Utils.isWhiteTurn() &&
                            selectedRow == 7) || activeKing) { //Ставим\переносим статус дамки
                        Utils.makeAKing(selectedRow, selectedCol);
                    }

                    Utils.delete(activeRow, activeCol); //Удаляем старую шашку
                    Utils.changePlayerTurn(); //меняем ход
                } else if (x == 2) { //все случаи, когда кого-то шашка берёт
                    resultOfLastMove = 2; //для запрета переключения при поедании подряд
                    selectedChecker.color = activeColor; //опять переназначаем цвет у пустого поля
                    selectedColor = activeColor; //для работы в одном цикле

                    if (activeKing || (Utils.isWhiteTurn() && selectedRow == 0 ||
                            !Utils.isWhiteTurn() && selectedRow == 7)) { //ставим\переносим дамку
                        Utils.makeAKing(selectedRow, selectedCol);
                        selectedKing = selectedChecker.isKing;
                    }

                    //проверяем, какой цвет взяли
                    if (checkers[verifierTurns.getCapturedRow()][verifierTurns.getCapturedCol()].color.equals("Black")) {
                        GameSituation.cntBlack--;
                    } else {
                        GameSituation.cntWhite--;
                    }

                    try {
                        //удаляем съеденную
                        Utils.delete(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    } catch (NullPointerException ignored) {
                    }



                    verifierTurns.init(selectedRow, selectedCol);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.eatAvailable() || activeKing != selectedChecker.isKing) {
                        try {
                            selectedChecker.paintInNormalColor(activeColor);
                        } catch (NullPointerException ignored) {
                        }
                        Utils.changePlayerTurn();
                    } else {
                        try {
                            selectedChecker.paintInGold();
                        } catch (NullPointerException ignored) {
                        }
                    }

                    try {
                        Utils.delete(activeRow, activeCol); //удаляем старую
                    } catch (NullPointerException ignored) {
                    }
                    activeRow = selectedRow;
                    activeCol = selectedCol;
                    activeKing = selectedKing;
                    activeColor = selectedColor;


                }
            }
        }
    }

    private void checkAfterTurn() {
        boolean isDraw = true;
        GameSituation.someToEatAllWhite = false;
        GameSituation.someToEatAllBlack = false;
        for (byte i = 0; i < Constants.SIZE; i ++) {
            for(byte j = 0; j < Constants.SIZE; j++) {
                if (!checkers[i][j].color.equals("No")) {
                    verifierTurns.init(i, j);
                    if (verifierTurns.movementAvailable()) isDraw = false;
                    if (verifierTurns.eatAvailable()) {
                        checkers[i][j].canEat = true;
                        if (checkers[i][j].color.equals("White")) {
                            GameSituation.someToEatAllWhite = true;
                        } else {
                            GameSituation.someToEatAllBlack = true;
                        }
                    } else checkers[i][j].canEat = false;
                }
            }
        }
        GameSituation.declareWinner();
        GameSituation.declareDraw(isDraw);


    }
}
