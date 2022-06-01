package checkers.logic;

import checkers.ui.*;
import static checkers.ui.Constants.SIDES;
import static checkers.ui.Constants.WAYTOMOVE;
import static checkers.logic.GameStatistic.activePlayer;

public class Turner {

    public static boolean lastActionIsEat = false;
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
    public boolean activePlayerCanEat = false;
    SIDES activeSide;
    boolean activeCanEat;
    boolean activeKing;
    boolean itIsAttemptToMove;
    VerifierTurns verifierTurns = new VerifierTurns(); //Проверка хода

    static CheckersBoard.Checker selectedChecker;
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

        if (activePlayer.equals(SIDES.black)) {
            this.activePlayerCanEat = GameStatistic.blackCanEat;
        } else {
            this.activePlayerCanEat = GameStatistic.whiteCanEat;
        }


        if (activeCheckerChoosed) {
            itIsAttemptToMove = true;
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
            if (activePlayer.equals(selectedSide) && selectedCanEat == activePlayerCanEat) {
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
            WAYTOMOVE move;
            switch (verifierTurns.checkTurn(selectedRow, selectedCol)) {
                case(1) -> move = WAYTOMOVE.move;
                case(2) -> move = WAYTOMOVE.eat;
                default -> move = WAYTOMOVE.no;
            }

            if (!move.equals(WAYTOMOVE.no)) {
                if (move.equals(WAYTOMOVE.move) && !activeCanEat) { //если можно походить без взятия и взять шашка никого не может
                    selectedChecker.side = activeSide; //переназначаем сторону у пустого поля
                    moveChecker();
                    Utils.deleteChecker(activeRow, activeCol);
                    Utils.changePlayerTurn(); //меняем ход
                } else if (move.equals(WAYTOMOVE.eat)) { //все случаи, когда кого-то шашка берёт
                    lastActionIsEat = true; //для запрета переключения при поедании подряд
                    selectedChecker.side = activeSide; //опять переназначаем цвет у пустого поля

                    moveChecker();

                    //проверяем, какой цвет взяли
                    if (checkers[verifierTurns.getCapturedRow()][verifierTurns.getCapturedCol()].side.equals(SIDES.black)) {
                        GameStatistic.cntBlack--;
                    } else {
                        GameStatistic.cntWhite--;
                    }

                    Utils.deleteChecker(verifierTurns.getCapturedRow(), verifierTurns.getCapturedCol());


                    verifierTurns.init(selectedRow, selectedCol);
                    //проверяем, продолжатся ли взятия на следующем ходу или не произошло ли смены на дамку
                    if (!verifierTurns.eatAvailable() || activeKing != selectedChecker.king) {
                        Utils.changePlayerTurn();
                    } else {
                        selectedChecker.paintInGold();
                    }

                    Utils.deleteChecker(activeRow, activeCol); //удаляем старую
                    activeRow = selectedRow;
                    activeCol = selectedCol;
                    activeKing = selectedKing;


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
                    if (verifierTurns.movementAvailable()) {
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
        if (activePlayer.equals(SIDES.black) && !blackHaveMoves ||
                activePlayer.equals(SIDES.white) && !whiteHaveMoves) {
            isDraw = true;
        }
        GameStatistic.declareWinner();
        GameStatistic.declareDraw(isDraw);
    }

    private void moveChecker() {
        selectedChecker.paintInNormalColor(); //перекрашиваем (передвигаем шашку)

        if ((activePlayer.equals(SIDES.white) && selectedRow == 0 || activePlayer.equals(SIDES.black) &&
                selectedRow == 7) || activeKing) { //Ставим\переносим статус дамки
            Utils.makeKing(selectedRow, selectedCol);
            selectedKing = selectedChecker.king;
        }
    }
}
