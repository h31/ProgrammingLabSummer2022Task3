package checkers.logic;

import checkers.ui.*;

import static checkers.logic.GameStatistic.changeScore;
import static checkers.ui.Constants.SIDES;
import static checkers.ui.Constants.MOVERESULT;
import static checkers.logic.GameStatistic.activePlayerSide;

public class Turner {


    static boolean mustContinueEat = false;
    static InfoCenter infoCenter;
    public static boolean activeCheckerChoosed = false;
    static CheckersBoard.Checker[][] checkers = CheckersBoard.checkers;
    // всё ниже аналогично тому, что выше, только для выбранной для хода шашки
    VerifierTurns verifierTurns; //Проверка хода
    private static CheckersBoard.Checker selectedChecker;
    public static CheckersBoard.Checker activeChecker;
    public Turner(InfoCenter infoCenter) {
        Turner.infoCenter = infoCenter;
    }

    public void reactOnUserClick(int selectedRow, int selectedCol){
        selectedChecker = checkers[selectedRow][selectedCol];

        boolean activePlayerCanEat = activePlayerSide.equals(SIDES.black)
                ? GameStatistic.blackMustEat
                : GameStatistic.whiteMustEat;

        if (activeCheckerChoosed) {
            boolean itIsAttemptToMove = true;
            if (!mustContinueEat) {
                if (activeChecker.row == selectedChecker.row && activeChecker.col == selectedChecker.col) {
                    itIsAttemptToMove = false;
                    canselChoose();
                } else if (activeChecker.side.equals(selectedChecker.side)
                        && activeChecker.mustEat == selectedChecker.mustEat) {
                    itIsAttemptToMove = false;
                    chooseAnotherChecker();
                }
            }
            if (itIsAttemptToMove) {
                tryToMakeTurn();
                checkAfterTurn();
            }
        } else {
            if (activePlayerSide.equals(selectedChecker.side) && selectedChecker.mustEat
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
        Utils.removeHighlight(activeChecker.row, activeChecker.col);
    }

    private void chooseAnotherChecker() {
        Utils.removeHighlight(activeChecker.row, activeChecker.col);
        activeChecker = selectedChecker;
        Utils.highlight(selectedChecker.row, selectedChecker.col);
    }

    private void tryToMakeTurn() {
        if (selectedChecker.side.equals(SIDES.no)) {
            verifierTurns = new VerifierTurns(activeChecker.row, activeChecker.col);
            MOVERESULT move = verifierTurns.checkTurn(selectedChecker.row, selectedChecker.col);
            if (!move.equals(MOVERESULT.itNotPossible)) {
                //если можно походить без взятия и взять шашка никого не может
                if (move.equals(MOVERESULT.itMove) && !activeChecker.mustEat) {
                    selectedChecker.side = activeChecker.side; //переназначаем сторону у пустого поля
                    createCheckerAtSelectedPos();
                    Utils.deleteChecker(activeChecker.row, activeChecker.col);
                    Utils.changePlayerTurn(); //меняем ход
                } else if (move.equals(MOVERESULT.itEat)) { //все случаи, когда шашка кого-то берёт
                    mustContinueEat = true; //для запрета переключения при поедании подряд
                    selectedChecker.side = activeChecker.side; //переназначаем цвет у пустого поля
                    createCheckerAtSelectedPos();
                    changeScore(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    Utils.deleteChecker(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());
                    boolean activeKing = activeChecker.king;
                    Utils.deleteChecker(activeChecker.row, activeChecker.col); //удаляем старую
                    verifierTurns = new VerifierTurns(selectedChecker.row, selectedChecker.col);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.eatAvailable() || activeKing != selectedChecker.king) {
                        Utils.changePlayerTurn();
                    } else {
                        selectedChecker.Highlight();
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
        GameStatistic.whiteMustEat = false;
        GameStatistic.blackMustEat = false;
        for (byte i = 0; i < Constants.SIZE; i ++) {
            for(byte j = 0; j < Constants.SIZE; j++) {
                if (!checkers[i][j].side.equals(SIDES.no)) {
                    verifierTurns = new VerifierTurns(i, j);
                    if (verifierTurns.moveOrEatAvailable()) {
                        if (checkers[i][j].side.equals(SIDES.white)) {
                            whiteHaveMoves = true;
                        } else {
                            blackHaveMoves = true;
                        }
                    }
                    if (verifierTurns.eatAvailable()) {
                        checkers[i][j].mustEat = true;
                        if (checkers[i][j].side.equals(SIDES.white)) {
                            GameStatistic.whiteMustEat = true;
                        } else {
                            GameStatistic.blackMustEat = true;
                        }
                    } else checkers[i][j].mustEat = false;
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

    private void createCheckerAtSelectedPos() {
        selectedChecker.renderShadowAndColor(); //перекрашиваем (передвигаем шашку)

        if ((activePlayerSide.equals(SIDES.white) && selectedChecker.row == 0
                || activePlayerSide.equals(SIDES.black) &&
                selectedChecker.row == 7) || activeChecker.king) { //Ставим\переносим статус дамки
            Utils.makeKing(selectedChecker.row, selectedChecker.col);
        }
    }
}
