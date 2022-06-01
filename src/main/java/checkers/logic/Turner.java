package checkers.logic;

import checkers.ui.*;
import static checkers.ui.Constants.SIDES;

public class Turner {
    public static int resultOfLastMove = 0;
    static InfoCenter infoCenter;
    public static boolean activeCheckerChoosed = false;
    static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    int selectedRow; //Ряд выбранной клетки
    int selectedCol; //Столбец выбранной клетки
    SIDES selectedSide; //Цвет фигуры в выбранной клетке
    boolean selectedCanEat; //Может ли выбранная фигура съесть
    boolean selectedKing; //Является ли выбранная фигура королём
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    public int activeRow;
    public int activeCol;
    public boolean thisPlayerCanEat = false;
    SIDES activeSide;
    boolean activeCanEat;
    boolean activeKing;
    boolean itIsAttemptToMove;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода

    static CheckersBoard.Checker selectedChecker;
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedCellRow, int selectedCellCol){
        selectedChecker = checkers[selectedCellRow][selectedCellCol];
        this.selectedRow = selectedChecker.row;
        this.selectedCol = selectedChecker.col;
        this.selectedSide = selectedChecker.side;
        this.selectedCanEat = selectedChecker.canEat;
        this.selectedKing = selectedChecker.isKing;
        if (GameSituation.activePlayer.equals(SIDES.black)) {
            this.thisPlayerCanEat = GameSituation.blackCanEat;
        } else {
            this.thisPlayerCanEat = GameSituation.whiteCanEat;
        }


        if (activeCheckerChoosed) {
            itIsAttemptToMove = true;
            if (resultOfLastMove != 2) {
                if (activeRow == selectedCellRow && activeCol == selectedCellCol) {
                    itIsAttemptToMove = false;
                    canselChoose();
                } else if (activeSide.equals(selectedSide) && activeCanEat == selectedCanEat) {
                    itIsAttemptToMove = false;
                    chooseAnotherChecker();
                }
            }
            if (itIsAttemptToMove) {
                tryToMakeThisTurn();
                checkAfterTurn();
            }
        } else {
            if (GameSituation.activePlayer.equals(selectedSide) && selectedCanEat == thisPlayerCanEat) {
                chooseActiveChecker();
            }
        }

    }



    private void chooseActiveChecker() {
        activeCheckerChoosed = true;
        activeRow = selectedRow;
        activeCol = selectedCol;
        activeSide = selectedSide;
        activeCanEat = selectedCanEat;
        activeKing = selectedKing;
        Utils.highlight(selectedRow, selectedCol);
    }

    private void canselChoose() {
        activeCheckerChoosed = false;

        Utils.unHighlight(activeRow, activeCol);
    }

    private void chooseAnotherChecker() {
        Utils.unHighlight(activeRow, activeCol);

        activeRow = selectedRow;
        activeCol = selectedCol;
        activeKing = selectedKing;

        Utils.highlight(selectedRow, selectedCol);
    }

    private void tryToMakeThisTurn() {
        if (selectedSide.equals(SIDES.no)) {
            verifierTurns.init(activeRow, activeCol);
            int x = verifierTurns.checkTurn(selectedRow, selectedCol);
            if (x != 0) {
                if (x == 1 && !activeCanEat) { //если можно походить без взятия и взять шашка никого не может
                    selectedChecker.side = activeSide; //переназначаем цвет у пустого поля
                    selectedChecker.paintInNormalColor(activeSide); //перекрашиваем (передвигаем шашку)

                    if ((Utils.isWhiteTurn() && selectedRow == 0 || !Utils.isWhiteTurn() &&
                            selectedRow == 7) || activeKing) { //Ставим\переносим статус дамки
                        Utils.makeAKing(selectedRow, selectedCol);
                    }

                    Utils.delete(activeRow, activeCol); //Удаляем старую шашку
                    Utils.changePlayerTurn(); //меняем ход
                } else if (x == 2) { //все случаи, когда кого-то шашка берёт
                    resultOfLastMove = 2; //для запрета переключения при поедании подряд
                    selectedChecker.side = activeSide; //опять переназначаем цвет у пустого поля
                    selectedSide = activeSide; //для работы в одном цикле

                    if (activeKing || (Utils.isWhiteTurn() && selectedRow == 0 ||
                            !Utils.isWhiteTurn() && selectedRow == 7)) { //ставим\переносим дамку
                        Utils.makeAKing(selectedRow, selectedCol);
                        selectedKing = selectedChecker.isKing;
                    }

                    //проверяем, какой цвет взяли
                    if (checkers[verifierTurns.getCapturedRow()][verifierTurns.getCapturedCol()].side.equals(SIDES.black)) {
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
                            selectedChecker.paintInNormalColor(activeSide);
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
                    activeSide = selectedSide;


                }
            }
        }
    }

    private void checkAfterTurn() {
        boolean isDraw = true;
        GameSituation.whiteCanEat = false;
        GameSituation.blackCanEat = false;
        for (byte i = 0; i < Constants.SIZE; i ++) {
            for(byte j = 0; j < Constants.SIZE; j++) {
                if (!checkers[i][j].side.equals(SIDES.no)) {
                    verifierTurns.init(i, j);
                    if (verifierTurns.movementAvailable()) isDraw = false;
                    if (verifierTurns.eatAvailable()) {
                        checkers[i][j].canEat = true;
                        if (checkers[i][j].side.equals(SIDES.white)) {
                            GameSituation.whiteCanEat = true;
                        } else {
                            GameSituation.blackCanEat = true;
                        }
                    } else checkers[i][j].canEat = false;
                }
            }
        }
        GameSituation.declareWinner();
        GameSituation.declareDraw(isDraw);
    }
}
