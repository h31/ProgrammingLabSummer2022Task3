package checkers.logic;

import checkers.UI.*;

public class Turner {
    public static int resultOfLastMove = 0;
    static InfoCenter infoCenter;
    public static boolean isTurn = false;
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
    boolean isItATryToMove;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода

    static CheckersBoard.Checker selectedChecker;

    public Turner() { //THIS IS ONLY FOR TESTS!!!!!! DO NOT USE IT
    }
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedCellRow, int selectedCellCol){
        selectedChecker = checkers[selectedCellRow][selectedCellCol];
        this.selectedRow = selectedChecker.row;
        this.selectedCol = selectedChecker.col;
        this.selectedColor = selectedChecker.color;
        this.selectedSomeToEat = selectedChecker.someToEat;
        this.selectedKing = selectedChecker.isKing;


        if (isTurn) {
            isItATryToMove = true;
            if (resultOfLastMove != 2) {
                if (activeRow == selectedCellRow && activeCol == selectedCellCol) {
                    isItATryToMove = false;
                    canselChoose();
                } else if (activeColor.equals(selectedColor) && activeSomeToEat == selectedSomeToEat) {
                    isItATryToMove = false;
                    chooseAnotherChecker();
                }
            }
            if (isItATryToMove) {
                tryToMakeThisTurn();
                checkAfterTurn();
            }
        } else {
            if (SomeStaff.isWhiteTurn() && selectedColor.equals("White") && (selectedSomeToEat ||
                    !DuringGameChecks.someToEatAllWhite) || !SomeStaff.isWhiteTurn() &&
                    selectedColor.equals("Black") && (selectedSomeToEat ||
                    !DuringGameChecks.someToEatAllBlack)) {
                chooseActiveChecker();
            }
        }

    }



    private void chooseActiveChecker() {
        isTurn = true;
        activeRow = selectedRow;
        activeCol = selectedCol;
        activeColor = selectedColor;
        activeSomeToEat = selectedSomeToEat;
        activeKing = selectedKing;
        SomeStaff.inline(selectedRow, selectedCol);
    }

    private void canselChoose() {
        isTurn = false;
        SomeStaff.unline(activeRow, activeCol);
    }

    private void chooseAnotherChecker() {
        int a = activeRow;
        int b = activeCol;

        activeRow = selectedRow;
        activeCol = selectedCol;
        activeKing = selectedKing;
        SomeStaff.unline(a, b);
        SomeStaff.inline(selectedRow, selectedCol);
    }

    private void tryToMakeThisTurn() {
        if (selectedColor.equals("No")) {
            verifierTurns.init(activeRow, activeCol);
            int x = verifierTurns.checkTurn(selectedRow, selectedCol);
            if (x != 0) {
                if (x == 1 && !activeSomeToEat) { //если можно походить без взятия и взять шашка никого не может
                    selectedChecker.color = activeColor; //переназначаем цвет у пустого поля
                    selectedChecker.paintInNormalColor(activeColor); //перекрашиваем (передвигаем шашку)
                    if ((SomeStaff.isWhiteTurn() && selectedRow == 0 || !SomeStaff.isWhiteTurn() &&
                            selectedRow == 7) || activeKing) { //Ставим\переносим статус дамки
                        SomeStaff.makeAKing(selectedRow, selectedCol);
                    }
                    SomeStaff.delete(activeRow, activeCol); //Удаляем старую шашку
                    SomeStaff.changePlayerTurn(); //меняем ход
                } else if (x == 2) { //все случаи, когда кого-то шашка берёт
                    resultOfLastMove = 2; //для запрета переключения при поедании подряд
                    selectedChecker.color = activeColor; //опять переназначаем цвет у пустого поля
                    selectedColor = activeColor; //для работы в одном цикле

                    if (activeKing || (SomeStaff.isWhiteTurn() && selectedRow == 0 ||
                            !SomeStaff.isWhiteTurn() && selectedRow == 7)) { //ставим\переносим дамку
                        SomeStaff.makeAKing(selectedRow, selectedCol);
                        selectedKing = selectedChecker.isKing;
                    }

                    //проверяем, какой цвет взяли
                    if (checkers[verifierTurns.getEatenRow()][verifierTurns.getEatenCol()].color.equals("Black")) {
                        DuringGameChecks.cntBlack--;
                    } else {
                        DuringGameChecks.cntWhite--;
                    }

                    //удаляем съеденную
                    SomeStaff.delete(verifierTurns.getEatenRow(), verifierTurns.getEatenCol());


                    verifierTurns.init(selectedRow, selectedCol);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.checkForTakes() || activeKing != selectedChecker.isKing) {
                        selectedChecker.paintInNormalColor(activeColor);
                        SomeStaff.changePlayerTurn();
                    } else {
                        selectedChecker.paintInGold();
                    }

                    SomeStaff.delete(activeRow, activeCol); //удаляем старую
                    activeRow = selectedRow;
                    activeCol = selectedCol;
                    activeKing = selectedKing;
                    activeColor = selectedColor;


                }
            }
        }
    }

    private void checkAfterTurn() {
        boolean thereIsNoDraw = false;
        boolean tWhite = false;
        boolean tBlack = false;
        int size = UIConstants.SIZE;
        for (byte i = 0; i < size; i ++) {
            for(byte j = 0; j < size; j++) {
                if (!checkers[i][j].color.equals("No")) {
                    verifierTurns.init(i, j);
                    thereIsNoDraw = thereIsNoDraw || verifierTurns.checkForTurns();
                    if (verifierTurns.checkForTakes()) {
                        checkers[i][j].someToEat = true;
                        if (checkers[i][j].color.equals("White")) {
                            DuringGameChecks.someToEatAllWhite = true;
                            tWhite = true;
                        } else {
                            DuringGameChecks.someToEatAllBlack = true;
                            tBlack = true;
                        }
                    } else checkers[i][j].someToEat = false;
                }
            }
        }
        DuringGameChecks.someToEatAllWhite = tWhite;
        DuringGameChecks.someToEatAllBlack = tBlack;
        DuringGameChecks.checkForWinner();
        DuringGameChecks.checkForDraw(thereIsNoDraw);


    }
}
