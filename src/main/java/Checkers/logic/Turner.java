package Checkers.logic;

import Checkers.UI.CheckBoard;
import Checkers.UI.InfoCenter;
import Checkers.UI.UIConstants;

import java.util.Objects;

public class Turner {
    int resultOfLastTurn = 0;
    static InfoCenter infoCenter;
    public static boolean isTurn = false;
    public static CheckBoard.Check[][] checkers = CheckBoard.checks;
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
    VerifierTurns verifierTurns = new VerifierTurns();

    static CheckBoard.Check selectedChecker;


    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedCellRow, int selectedCellCol, CheckBoard.Check[][] checkers){
        Turner.checkers = checkers;
        selectedChecker = checkers[selectedCellRow][selectedCellCol];
        this.selectedCellRow = selectedChecker.row;
        this.selectedCellCol = selectedChecker.col;
        this.selectedCellColor = selectedChecker.color;
        this.selectedCellSomeToEat = selectedChecker.someToEat;
        this.selectedCellKing = selectedChecker.isKing;


        if (!isTurn && !selectedCellColor.equals("No") && resultOfLastTurn != 2 &&
                (SomeStaff.isWhiteTurn() && selectedCellColor.equals("White") && (selectedCellSomeToEat ||
                        !DuringGameChecks.someToEatAllWhite) || !SomeStaff.isWhiteTurn() && selectedCellColor.equals("Black") &&
                        (selectedCellSomeToEat || !DuringGameChecks.someToEatAllBlack))) {
            chooseActiveChecker();
        }  else if (isTurn && activeCheckerRow == selectedCellRow && activeCheckerCol == selectedCellCol && resultOfLastTurn != 2) {
            canselChoose();
        }  else if (isTurn && activeCheckerColor.equals(selectedCellColor) && resultOfLastTurn != 2 && activeCheckerSomeToEat == selectedCellSomeToEat) {
            chooseAnotherChecker();
        } else if (isTurn) {
                tryToMakeThisTurn();
            }

        selectedChecker.color = selectedCellColor;
        checkAfterTurn();
    }



    public void chooseActiveChecker() {
        isTurn = true;
        activeCheckerRow = selectedCellRow;
        activeCheckerCol = selectedCellCol;
        activeCheckerColor = selectedCellColor;
        activeCheckerSomeToEat = selectedCellSomeToEat;
        activeCheckerKing = selectedCellKing;
        SomeStaff.inline(selectedCellRow, selectedCellCol);

    }

    public void canselChoose() {
        isTurn = false;
        SomeStaff.unline(activeCheckerRow, activeCheckerCol);
    }

    public void chooseAnotherChecker() {
        SomeStaff.unline(activeCheckerRow, activeCheckerCol);
        activeCheckerRow = selectedCellRow;
        activeCheckerCol = selectedCellCol;
        activeCheckerKing = selectedCellKing;
        SomeStaff.inline(selectedCellRow, selectedCellCol);
    }

    public void tryToMakeThisTurn() {
        if (Objects.equals(selectedCellColor, "No")) {
            verifierTurns.init(activeCheckerRow, activeCheckerCol);
            int x = verifierTurns.checkTurn(selectedCellRow, selectedCellCol);
            if (x != 0) {
                if (x == 1 && !activeCheckerSomeToEat) {
                    if (activeCheckerColor.equals("White")) {
                        selectedChecker.label.setBackground(UIConstants.backWhite);
                    } else {
                        selectedChecker.label.setBackground(UIConstants.backBlack);
                    }
                    selectedCellColor = activeCheckerColor;
                    selectedCellKing = activeCheckerKing;
                    if (selectedCellKing) {
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    SomeStaff.delete(activeCheckerRow, activeCheckerCol);
                    isTurn = false;
                    if ((SomeStaff.isWhiteTurn() && selectedCellRow == 0 || !SomeStaff.isWhiteTurn() &&
                            selectedCellRow == 7) && !activeCheckerKing) {
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    SomeStaff.changePlayerTurn();
                } else if (x == 2) {
                    selectedChecker.label.setBackground(UIConstants.backGo);
                    selectedCellColor = activeCheckerColor;
                    selectedCellKing = activeCheckerKing;
                    selectedChecker.isKing = activeCheckerKing;
                    selectedChecker.color = activeCheckerColor;
                    if (activeCheckerKing) {
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    selectedCellKing = activeCheckerKing;
                    if (SomeStaff.isWhiteTurn() && selectedCellRow == 0 ||
                            !SomeStaff.isWhiteTurn() && selectedCellRow == 7) {
                        SomeStaff.makeAKing(selectedCellRow, selectedCellCol);
                    }
                    selectedCellKing = selectedChecker.isKing;
                    SomeStaff.delete(activeCheckerRow, activeCheckerCol);
                    if (checkers[verifierTurns.getEatenRow()][verifierTurns.getEatenCol()].color.equals("Black")) {
                        DuringGameChecks.cntBlack--;
                    } else {
                        DuringGameChecks.cntWhite--;
                    }
                    SomeStaff.delete(verifierTurns.getEatenRow(), verifierTurns.getEatenCol());
                    activeCheckerRow = selectedCellRow;
                    activeCheckerCol = selectedCellCol;
                    activeCheckerKing = selectedCellKing;

                    resultOfLastTurn = 2;
                    verifierTurns.init(activeCheckerRow, activeCheckerCol);
                    if (!verifierTurns.checkForTakes()) {
                        if (activeCheckerColor.equals("White")) {
                            selectedChecker.label.setBackground(UIConstants.backWhite);
                        } else {
                            selectedChecker.label.setBackground(UIConstants.backBlack);
                        }
                        isTurn = false;
                        SomeStaff.changePlayerTurn();
                        resultOfLastTurn = 0;
                    }
                }
            }
        }
    }

    public void checkAfterTurn() {
        boolean tWhite = false;
        boolean tBlack = false;
        for (byte i = 0; i < 8; i ++) {
            for(byte j = 0; j < 8; j++) {
                if (!checkers[i][j].color.equals("No")) {
                    verifierTurns.init(i, j);
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


    }
}
