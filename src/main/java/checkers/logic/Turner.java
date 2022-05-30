package checkers.logic;

import checkers.UI.CheckersBoard;
import checkers.UI.InfoCenter;
import checkers.UI.UIConstants;

public class Turner {
    static int resultOfLastTurn = 0;
    static InfoCenter infoCenter;
    public static boolean isTurn = false;
    public static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    int selectedCellRow; //Ряд выбранной клетки
    int selectedCellCol; //Столбец выбранной клетки
    String selectedCellColor; //Цвет фигуры в выбранной клетке
    boolean selectedCellSomeToEat; //Может ли выбранная фигура съесть
    boolean selectedCellKing; //Является ли выбранная фигура королём
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    int activeCheckerRow;
    int activeCheckerCol;
    String activeCheckerColor;
    boolean activeCheckerSomeToEat;
    boolean activeCheckerKing;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода

    static CheckersBoard.Checker selectedChecker;


    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedCellRow, int selectedCellCol){
        selectedChecker = checkers[selectedCellRow][selectedCellCol];
        this.selectedCellRow = selectedChecker.row;
        this.selectedCellCol = selectedChecker.col;
        this.selectedCellColor = selectedChecker.color;
        this.selectedCellSomeToEat = selectedChecker.someToEat;
        this.selectedCellKing = selectedChecker.isKing;


        if (!isTurn && !selectedCellColor.equals("No") && resultOfLastTurn != 2 &&
                (SomeStaff.isWhiteTurn() && selectedCellColor.equals("White") && (selectedCellSomeToEat ||
                        !DuringGameChecks.someToEatAllWhite) || !SomeStaff.isWhiteTurn() &&
                        selectedCellColor.equals("Black") && (selectedCellSomeToEat ||
                        !DuringGameChecks.someToEatAllBlack))) {
            chooseActiveChecker();
        }  else if (isTurn && activeCheckerRow == selectedCellRow && activeCheckerCol
                == selectedCellCol && resultOfLastTurn != 2) {
            canselChoose();
        }  else if (isTurn && activeCheckerColor.equals(selectedCellColor) && resultOfLastTurn
                != 2 && activeCheckerSomeToEat == selectedCellSomeToEat) {
            chooseAnotherChecker();
        } else if (isTurn) {
                tryToMakeThisTurn();
            }

        checkAfterTurn();
    }



    private void chooseActiveChecker() {
        isTurn = true;
        activeCheckerRow = selectedCellRow;
        activeCheckerCol = selectedCellCol;
        activeCheckerColor = selectedCellColor;
        activeCheckerSomeToEat = selectedCellSomeToEat;
        activeCheckerKing = selectedCellKing;
        SomeStaff.inline(selectedCellRow, selectedCellCol);
    }

    private void canselChoose() {
        isTurn = false;
        SomeStaff.unline(activeCheckerRow, activeCheckerCol);
    }

    private void chooseAnotherChecker() {
        SomeStaff.unline(activeCheckerRow, activeCheckerCol);
        activeCheckerRow = selectedCellRow;
        activeCheckerCol = selectedCellCol;
        activeCheckerKing = selectedCellKing;
        SomeStaff.inline(selectedCellRow, selectedCellCol);
    }

    private void tryToMakeThisTurn() {
        if (selectedCellColor.equals("No")) {
            verifierTurns.init(activeCheckerRow, activeCheckerCol);
            int x = verifierTurns.checkTurn(selectedCellRow, selectedCellCol);
            if (x != 0) {
                if (x == 1 && !activeCheckerSomeToEat) { //если можно походить без взятия и взять шашка никого не может
                    selectedChecker.labelDown.setBackground(UIConstants.BLACK_BACK); //Ставим нижний слой шашки
                    if (activeCheckerColor.equals("White")) { //Ставим верхний слой шашки
                        selectedChecker.labelUp.setBackground(UIConstants.WHITE_CHECKER);
                    } else {
                        selectedChecker.labelUp.setBackground(UIConstants.BLACK_CHECKER);
                    }
                    selectedChecker.color = activeCheckerColor;
                    if ((SomeStaff.isWhiteTurn() && selectedCellRow == 0 || !SomeStaff.isWhiteTurn() &&
                            selectedCellRow == 7) || activeCheckerKing) { //Ставим\переносим статус дамки
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    SomeStaff.delete(activeCheckerRow, activeCheckerCol); //Удаляем старую шашку
                    SomeStaff.changePlayerTurn();
                } else if (x == 2) { //все случаи, когда кого-то шашка берёт
                    selectedChecker.labelDown.setBackground(UIConstants.BLACK_BACK); //ставим нижний слой
                    selectedChecker.labelUp.setBackground(UIConstants.CHOOSEN_CHECKER); //подсвечиваем
                    selectedChecker.color = activeCheckerColor;

                    if (activeCheckerKing || (SomeStaff.isWhiteTurn() && selectedCellRow == 0 ||
                            !SomeStaff.isWhiteTurn() && selectedCellRow == 7)) { //ставим\переносим дамку
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    SomeStaff.delete(activeCheckerRow, activeCheckerCol); //удаляем старую

                    //проверяем, какой цвет взяли
                    if (checkers[verifierTurns.getEatenRow()][verifierTurns.getEatenCol()].color.equals("Black")) {
                        DuringGameChecks.cntBlack--;
                    } else {
                        DuringGameChecks.cntWhite--;
                    }

                    //удаляем съеденную
                    SomeStaff.delete(verifierTurns.getEatenRow(), verifierTurns.getEatenCol());

                    activeCheckerRow = selectedCellRow;
                    activeCheckerCol = selectedCellCol;

                    resultOfLastTurn = 2;
                    verifierTurns.init(activeCheckerRow, activeCheckerCol);
                    //проверяем, продолжатся ли взятия на следующем ходу
                    if (!verifierTurns.checkForTakes() || activeCheckerKing != selectedChecker.isKing) {
                        if (activeCheckerColor.equals("White")) {
                            selectedChecker.labelUp.setBackground(UIConstants.WHITE_CHECKER);
                        } else {
                            selectedChecker.labelUp.setBackground(UIConstants.BLACK_CHECKER);
                        }
                        SomeStaff.changePlayerTurn();
                    }
                    activeCheckerKing = selectedChecker.isKing;
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
