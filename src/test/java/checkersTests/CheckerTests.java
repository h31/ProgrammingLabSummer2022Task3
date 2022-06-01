package checkersTests;

import checkers.UI.CheckersBoard;
import checkers.logic.DuringGameChecks;
import checkers.logic.SomeStaff;
import checkers.logic.Turner;
import checkers.logic.VerifierTurns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckerTests {
    CheckersBoard checkersBoard;
    public void initNewCheckers() {
        checkersBoard = new CheckersBoard();
        DuringGameChecks.cntWhite = 12;
        DuringGameChecks.cntBlack = 12;
        SomeStaff.playerTurn = "Black";
    }


    @Test
    void testVerifierForTurns() {
        initNewCheckers();
        VerifierTurns verifierTurns = new VerifierTurns();
        verifierTurns.init(0, 0); //пустая клетка
        assertFalse(verifierTurns.checkForTurns());
        verifierTurns.init(0, 3); //клетка в окружении других своих
        assertFalse(verifierTurns.checkForTurns());
        verifierTurns.init(2, 1); //клетка на краю
        assertTrue(verifierTurns.checkForTurns());

        CheckersBoard.checkers[0][3].color = "White";
        verifierTurns.init(0, 3); //клетка в окружении других чужих
        assertFalse(verifierTurns.checkForTurns());
    }

    @Test
    void testVerifierForTakes() {
        initNewCheckers();
        VerifierTurns verifierTurns = new VerifierTurns();
        verifierTurns.init(0, 0); //пустая клетка
        assertFalse(verifierTurns.checkForTakes());
        verifierTurns.init(0, 3); //Своя среди своих
        assertFalse(verifierTurns.checkForTakes());
        verifierTurns.init(2, 3); //Своя в первых рядах
        assertFalse(verifierTurns.checkForTakes());

        CheckersBoard.checkers[3][2].color = "White"; //перед первым рядом черных поставил белую
        verifierTurns.init(2, 3);
        assertTrue(verifierTurns.checkForTakes());
        assertEquals(3, verifierTurns.getEatenRow());
        assertEquals(2, verifierTurns.getEatenCol());

    }

    @Test
    void someStaffTests () {
        initNewCheckers();
        try {
            SomeStaff.makeAKing(0, 1);
        } catch (NullPointerException ignored) {
        }
        assertTrue(CheckersBoard.checkers[0][1].isKing);

        CheckersBoard.checkers[0][1].someToEat = true;
        try {
            SomeStaff.delete(0, 1);
        } catch (NullPointerException ignored) {

        }
        assertEquals(CheckersBoard.checkers[0][1].color, "No");
        assertFalse(CheckersBoard.checkers[0][1].someToEat);

        assertFalse(SomeStaff.isWhiteTurn());

        Turner.isTurn = true;

        try {
            SomeStaff.changePlayerTurn();
        } catch (NullPointerException ignored) {
        }
        assertTrue(SomeStaff.isWhiteTurn());
        assertFalse(Turner.isTurn);
        assertEquals("White", SomeStaff.playerTurn);
        try {
            SomeStaff.changePlayerTurn();
        } catch (NullPointerException ignored) {
        }
        assertEquals("Black", SomeStaff.playerTurn);

        try {
            SomeStaff.inline(0, 1);
        } catch (NullPointerException ignored) {
        }

        try {
            SomeStaff.unline(0, 1);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void duringGameChecksTests() {
        CheckersBoard.isGame = true;
        DuringGameChecks.cntWhite = 0;
        try {
            DuringGameChecks.checkForWinner();
        } catch (NullPointerException ignored) {
        }
        assertFalse(CheckersBoard.isGame);


        CheckersBoard.isGame = true;
        DuringGameChecks.cntWhite = 12;
        DuringGameChecks.cntBlack = 0;
        try {
            DuringGameChecks.checkForWinner();
        } catch (NullPointerException ignored) {
        }
        assertFalse(CheckersBoard.isGame);

        CheckersBoard.isGame = true;
        try {
            DuringGameChecks.checkForDraw(false);
        } catch (NullPointerException ignored) {
        }
        assertFalse(CheckersBoard.isGame);

        DuringGameChecks.cntBlack = 12;
        try {
            DuringGameChecks.checkForWinner();
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void turnerTests() {
        initNewCheckers();
        Turner turner = new Turner();
        turner.makeATurn(0, 0); //пробуем выбрать пустое поле
        assertFalse(Turner.isTurn);
        turner.makeATurn(7, 0); //пробуем выбрать белую когда ходят черные
        assertFalse(Turner.isTurn);
        turner.makeATurn(1, 0); //пробуем выбрать черную когда ходят черные
        assertTrue(Turner.isTurn);
        assertEquals(turner.activeRow, 1);
        assertEquals(turner.activeCol, 0);

        turner.makeATurn(2, 1); //пробуем выбрать другую черную
        assertTrue(Turner.isTurn);
        assertEquals(turner.activeRow, 2);
        assertEquals(turner.activeCol, 1);

        turner.makeATurn(2, 1); //пробуем отменить выбор
        assertFalse(Turner.isTurn);

        turner.makeATurn(2, 1);
        turner.makeATurn(2, 2); //пробуем переместить на случайное место
        assertTrue(Turner.isTurn);
        assertEquals(turner.activeRow, 2);
        assertEquals(turner.activeCol, 1);

        turner.makeATurn(3, 2); //пробуем переместить на 1 по диагонали
        assertFalse(Turner.isTurn);
        assertEquals(CheckersBoard.checkers[3][2].color, "Black");
        assertEquals(CheckersBoard.checkers[2][1].color, "No");

        turner.makeATurn(0, 1); //пробуем выбрать черную, когда ходят белые
        assertFalse(Turner.isTurn);

        CheckersBoard.checkers[5][0].isKing = true;
        turner.makeATurn(5, 0); //выбрали белую для нового хода
        turner.makeATurn(4, 1); //проверяем перенос King
        assertTrue(CheckersBoard.checkers[4][1].isKing);


        //NO MORE TESTS DUE TO REQUIRED GRAPHIC, ALL TESTED BY HANDS ANYWAY

    }

    @Test
    void eatTests() {
        initNewCheckers();
        Turner turner = new Turner();
        CheckersBoard.checkers[3][2].color = "White";
        turner.makeATurn(2, 1);
        turner.makeATurn(4, 3);
        assertEquals(11, DuringGameChecks.cntWhite);
        assertEquals("No", CheckersBoard.checkers[3][2].color);
        assertEquals("Black", CheckersBoard.checkers[4][3].color);
    }


}
