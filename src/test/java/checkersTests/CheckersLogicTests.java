package checkersTests;

import checkers.logic.GameStatistic;
import checkers.logic.Turner;
import checkers.logic.Utils;
import checkers.logic.VerifierTurns;
import checkers.ui.CheckersBoard;
import checkers.ui.InfoCenter;
import checkers.ui.Constants.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CheckersLogicTests {
    void initCheckerBoard() {
        new CheckersBoard(new InfoCenter(true), true);
        CheckersBoard.restart();
        CheckersBoard.isGame = true;
    }

    @Test
    public void utilsTests() {
        initCheckerBoard();
        assertEquals(SIDES.black, GameStatistic.activePlayerSide);
        Utils.changePlayerTurn();
        assertEquals(SIDES.white, GameStatistic.activePlayerSide);
        Utils.changePlayerTurn();
        assertEquals(SIDES.black, GameStatistic.activePlayerSide);

        assertEquals(SIDES.black, CheckersBoard.checkers[0][1].side);
        CheckersBoard.checkers[0][1].canEat = true;
        Utils.deleteChecker(0, 1);
        assertEquals(SIDES.no, CheckersBoard.checkers[0][1].side);
        assertFalse(CheckersBoard.checkers[0][1].canEat);

        Utils.makeKing(0, 1);
        assertTrue(CheckersBoard.checkers[0][1].king);

        Utils.highlight(0, 1);
        Utils.unHighlight(0, 1);
    }


    @Test
    public void gameStatisticTests() {
        initCheckerBoard();
        GameStatistic.cntBlack = 0;
        GameStatistic.declareWinner();
        assertFalse(CheckersBoard.isGame);

        initCheckerBoard();
        GameStatistic.cntWhite = 0;
        GameStatistic.declareWinner();
        assertFalse(CheckersBoard.isGame);

        initCheckerBoard();
        GameStatistic.declareDraw(false);
        assertTrue(CheckersBoard.isGame);

        GameStatistic.declareDraw(true);
        assertFalse(CheckersBoard.isGame);
    }

    @Test
    public void verifierTurnsTests() {
        initCheckerBoard();
        VerifierTurns verifierTurns = new VerifierTurns();
        verifierTurns.init(0, 1);
        assertEquals(0, verifierTurns.checkTurn(1, 2));

        verifierTurns.init(2, 1);
        assertEquals(1, verifierTurns.checkTurn(3, 2));

        initCheckerBoard();
        verifierTurns.init(2, 1);
        CheckersBoard.checkers[3][2].side = SIDES.white;
        assertEquals(2, verifierTurns.checkTurn(4, 3));
        assertEquals(3, verifierTurns.getCapturedRow());
        assertEquals(2, verifierTurns.getCapturedCol());
        assertTrue(verifierTurns.moveOrEatAvailable());
        assertTrue(verifierTurns.eatAvailable());
    }

    @Test
    public void turnerTests() {
        initCheckerBoard();
        Turner turner = new Turner(new InfoCenter(true));

        turner.makeATurn(0, 0);
        assertFalse(Turner.activeCheckerChoosed);

        turner.makeATurn(7, 0);
        assertFalse(Turner.activeCheckerChoosed);

        turner.makeATurn(0, 1);
        assertTrue(Turner.activeCheckerChoosed);
        assertEquals(0, turner.activeRow);
        assertEquals(1, turner.activeCol);

        turner.makeATurn(0, 1);
        assertFalse(Turner.activeCheckerChoosed);

        turner.makeATurn(0, 1);

        turner.makeATurn(2, 1);
        assertTrue(Turner.activeCheckerChoosed);
        assertEquals(2, turner.activeRow);
        assertEquals(1, turner.activeCol);

        turner.makeATurn(3, 1);
        assertTrue(Turner.activeCheckerChoosed);
        assertEquals(SIDES.no, CheckersBoard.checkers[3][1].side);
        assertEquals(SIDES.black, CheckersBoard.checkers[2][1].side);

        turner.makeATurn(3, 2);
        assertFalse(Turner.activeCheckerChoosed);
        assertEquals(SIDES.no, CheckersBoard.checkers[2][1].side);
        assertEquals(SIDES.black, CheckersBoard.checkers[3][2].side);

        turner.makeATurn(5, 0);
        turner.makeATurn(4, 1);
        assertFalse(Turner.activeCheckerChoosed);
        assertEquals(SIDES.no, CheckersBoard.checkers[5][0].side);
        assertEquals(SIDES.white, CheckersBoard.checkers[4][1].side);
    }

    @Test
    public void turnerEatTests() {
        initCheckerBoard();
        Turner turner = new Turner(new InfoCenter(true));

        CheckersBoard.checkers[4][3].side = SIDES.black;
        CheckersBoard.checkers[6][1].side = SIDES.no;
        turner.makeATurn(4, 3);
        turner.makeATurn(6, 1);
        assertEquals(SIDES.no, CheckersBoard.checkers[4][3].side);
        assertEquals(SIDES.no, CheckersBoard.checkers[5][2].side);
        assertEquals(SIDES.black, CheckersBoard.checkers[6][1].side);
        assertEquals(11, GameStatistic.cntWhite);

        turner.makeATurn(7, 0);
        turner.makeATurn(5, 2);
        assertEquals(11, GameStatistic.cntBlack);
    }

    @Test
    public void turnerEatTwoTests() {
        initCheckerBoard();
        Turner turner = new Turner(new InfoCenter(true));

        CheckersBoard.checkers[3][4].side = SIDES.black;
        CheckersBoard.checkers[4][3].side = SIDES.white;
        CheckersBoard.checkers[5][2].side = SIDES.no;
        CheckersBoard.checkers[7][4].side = SIDES.no;
        turner.makeATurn(3, 4);
        turner.makeATurn(5, 2);
        turner.makeATurn(7, 0);
        turner.makeATurn(7, 4);
        assertEquals(SIDES.no, CheckersBoard.checkers[3][4].side);
        assertEquals(SIDES.no, CheckersBoard.checkers[4][3].side);
        assertEquals(SIDES.no, CheckersBoard.checkers[6][3].side);
        assertEquals(SIDES.white, CheckersBoard.checkers[7][0].side);
        assertEquals(SIDES.black, CheckersBoard.checkers[7][4].side);
    }

}
