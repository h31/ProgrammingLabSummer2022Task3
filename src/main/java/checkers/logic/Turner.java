package checkers.logic;

import checkers.ui.*;

import static checkers.logic.GameStatistic.changeScore;
import static checkers.ui.Constants.SIDES;
import static checkers.ui.Constants.MOVERESULT;
import static checkers.logic.GameStatistic.activePlayerSide;

public class Turner {

    static boolean lastActionIsEat = false;
    static InfoCenter infoCenter;
    public static boolean activeCheckerChoosed = false;
    static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода
    private static CheckersBoard.Checker selectedChecker;
    public static CheckersBoard.Checker activeChecker;
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void makeATurn(int selectedRow, int selectedCol){
        selectedChecker = checkers[selectedRow][selectedCol];

        boolean activePlayerCanEat = activePlayerSide.equals(SIDES.black)
                ? GameStatistic.blackCanEat
                : GameStatistic.whiteCanEat;

        if (activeCheckerChoosed) {
            boolean itIsAttemptToMove = true;
            if (!lastActionIsEat) {
                if (activeChecker.row == selectedChecker.row && activeChecker.col == selectedChecker.col) {
                    itIsAttemptToMove = false;
                    canselChoose();
                } else if (activeChecker.side.equals(selectedChecker.side)
                        && activeChecker.canEat == selectedChecker.canEat) {
                    itIsAttemptToMove = false;
                    chooseAnotherChecker();
                }
            }
            if (itIsAttemptToMove) {
                tryToMakeThisTurn();
                checkAfterTurn();
            }
        } else {
            if (activePlayerSide.equals(selectedChecker.side) && selectedChecker.canEat
                    == activePlayerCanEat) {
                chooseActiveChecker();
            }
        }

    }

    private void chooseActiveChecker() {
        activeCheckerChoosed = true;
        activeChecker = selectedChecker;
        Utils.highlight(selectedChecker.row, selectedChecker.col);
    }

    private void canselChoose() {
        activeCheckerChoosed = false;
        Utils.unHighlight(activeChecker.row, activeChecker.col);
    }

    private void chooseAnotherChecker() {
        Utils.unHighlight(activeChecker.row, activeChecker.col);
        activeChecker = selectedChecker;
        Utils.highlight(selectedChecker.row, selectedChecker.col);
    }

    private void tryToMakeThisTurn() {
        if (selectedChecker.side.equals(SIDES.no)) {
            verifierTurns.init(activeChecker.row, activeChecker.col);
            MOVERESULT move;
            switch (verifierTurns.checkTurn(selectedChecker.row, selectedChecker.col)) {
                case(1) -> move = MOVERESULT.itMove;
                case(2) -> move = MOVERESULT.itEat;
                default -> move = MOVERESULT.itNotPossible;
            }
            if (!move.equals(MOVERESULT.itNotPossible)) {
                //если можно походить без взятия и взять шашка никого не может
                if (move.equals(MOVERESULT.itMove) && !activeChecker.canEat) {
                    selectedChecker.side = activeChecker.side; //переназначаем сторону у пустого поля
                    moveChecker();
                    Utils.deleteChecker(activeChecker.row, activeChecker.col);
                    Utils.changePlayerTurn(); //меняем ход
                } else if (move.equals(MOVERESULT.itEat)) { //все случаи, когда шашка кого-то берёт
                    lastActionIsEat = true; //для запрета переключения при поедании подряд
                    selectedChecker.side = activeChecker.side; //переназначаем цвет у пустого поля
                    moveChecker();
                    changeScore(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    Utils.deleteChecker(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    boolean activeKing = activeChecker.king;
                    Utils.deleteChecker(activeChecker.row, activeChecker.col); //удаляем старую
                    verifierTurns.init(selectedChecker.row, selectedChecker.col);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.eatAvailable() || activeKing != selectedChecker.king) {
                        Utils.changePlayerTurn();
                    } else {
                        selectedChecker.highlightGraphic();
                        activeChecker = selectedChecker;
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

        if ((activePlayerSide.equals(SIDES.white) && selectedChecker.row == 0
                || activePlayerSide.equals(SIDES.black) &&
                selectedChecker.row == 7) || activeChecker.king) { //Ставим\переносим статус дамки
            Utils.makeKing(selectedChecker.row, selectedChecker.col);
        }
    }
}
