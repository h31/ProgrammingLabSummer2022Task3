package checkers.logic;

import checkers.ui.*;

import static checkers.logic.GameStatistic.changeScore;
import static checkers.ui.Constants.SIDES;
import static checkers.ui.Constants.MOVECHECKRESULT;
import static checkers.logic.GameStatistic.activePlayerSide;

public class Turner {

    static boolean lastActionIsEat = false;
    static InfoCenter infoCenter;
    public static boolean activeCheckerChoosed = false;
    static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    private int selectedRow; //Ряд выбранной клетки
    private int selectedCol; //Столбец выбранной клетки
    private SIDES selectedSide; //Цвет фигуры в выбранной клетке
    private boolean selectedCanEat; //Может ли выбранная фигура съесть
    private boolean selectedKing; //Является ли выбранная фигура королём
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    public int activeRow;
    public int activeCol;
    private SIDES activeSide;
    private boolean activeCanEat;
    private boolean activeKing;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода
    private static CheckersBoard.Checker selectedChecker;
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedRow, int selectedCol){
        selectedChecker = checkers[selectedRow][selectedCol];
        this.selectedRow = selectedRow;
        this.selectedCol = selectedCol;
        this.selectedSide = selectedChecker.side;
        this.selectedCanEat = selectedChecker.canEat;
        this.selectedKing = selectedChecker.king;

        boolean activePlayerCanEat;
        if (activePlayerSide.equals(SIDES.black)) {
            activePlayerCanEat = GameStatistic.blackCanEat;
        } else {
            activePlayerCanEat = GameStatistic.whiteCanEat;
        }

        if (activeCheckerChoosed) {
            boolean itIsAttemptToMove = true;
            if (!lastActionIsEat) {
                if (activeRow == selectedRow && activeCol == selectedCol) {
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
            if (activePlayerSide.equals(selectedSide) && selectedCanEat == activePlayerCanEat) {
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
            MOVECHECKRESULT move;
            switch (verifierTurns.checkTurn(selectedRow, selectedCol)) {
                case(1) -> move = MOVECHECKRESULT.itMove;
                case(2) -> move = MOVECHECKRESULT.itEat;
                default -> move = MOVECHECKRESULT.itNotPossible;
            }
            if (!move.equals(MOVECHECKRESULT.itNotPossible)) {
                //если можно походить без взятия и взять шашка никого не может
                if (move.equals(MOVECHECKRESULT.itMove) && !activeCanEat) {
                    selectedChecker.side = activeSide; //переназначаем сторону у пустого поля
                    moveChecker();
                    Utils.deleteChecker(activeRow, activeCol);
                    Utils.changePlayerTurn(); //меняем ход
                } else if (move.equals(MOVECHECKRESULT.itEat)) { //все случаи, когда шашка кого-то берёт
                    lastActionIsEat = true; //для запрета переключения при поедании подряд
                    selectedChecker.side = activeSide; //переназначаем цвет у пустого поля
                    moveChecker();
                    changeScore(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    Utils.deleteChecker(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    Utils.deleteChecker(activeRow, activeCol); //удаляем старую
                    verifierTurns.init(selectedRow, selectedCol);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.eatAvailable() || activeKing != selectedKing) {
                        Utils.changePlayerTurn();
                    } else {
                        selectedChecker.highlightGraphic();
                        activeRow = selectedRow;
                        activeCol = selectedCol;
                        activeKing = selectedKing;
                    }
                }
            }
        }
    }

    private void checkAfterTurn() {
        boolean isDraw = false;
        boolean whiteHaveMoves = false;
        boolean blackHaveMoves = false;
        GameStatistic.whiteCanEat = false;
        GameStatistic.blackCanEat = false;
        for (byte i = 0; i < Constants.SIZE; i ++) {
            for(byte j = 0; j < Constants.SIZE; j++) {
                if (!checkers[i][j].side.equals(SIDES.no)) {
                    verifierTurns.init(i, j);
                    if (verifierTurns.moveOrEatAvailable()) {
                        if (checkers[i][j].side.equals(SIDES.white)) {
                            whiteHaveMoves = true;
                        } else {
                            blackHaveMoves = true;
                        }
                    }
                    if (verifierTurns.eatAvailable()) {
                        checkers[i][j].canEat = true;
                        if (checkers[i][j].side.equals(SIDES.white)) {
                            GameStatistic.whiteCanEat = true;
                        } else {
                            GameStatistic.blackCanEat = true;
                        }
                    } else checkers[i][j].canEat = false;
                }
            }
        }
        if (activePlayerSide.equals(SIDES.black) && !blackHaveMoves ||
                activePlayerSide.equals(SIDES.white) && !whiteHaveMoves) {
            isDraw = true;
        }
        GameStatistic.declareWinner();
        GameStatistic.declareDraw(isDraw);
    }

    private void moveChecker() {
        selectedChecker.setSideColorGraphic(); //перекрашиваем (передвигаем шашку)

        if ((activePlayerSide.equals(SIDES.white) && selectedRow == 0 || activePlayerSide.equals(SIDES.black) &&
                selectedRow == 7) || activeKing) { //Ставим\переносим статус дамки
            Utils.makeKing(selectedRow, selectedCol);
            selectedKing = selectedChecker.king;
        }
    }
}
