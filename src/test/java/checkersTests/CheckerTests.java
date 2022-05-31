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
        try {
            turner.makeATurn(0, 0); //попытка выбрать пустую клетку для хода
        } catch (NullPointerException ignored) {
        }
        assertFalse(Turner.isTurn);
        try {
            turner.makeATurn(7, 0); //попытка выбрать белую когда ходят черные
        } catch (NullPointerException ignored) {
        }
        assertFalse(Turner.isTurn);
        try {
            turner.makeATurn(0, 1); //попытка выбрать черную когда ходят черные
        } catch (NullPointerException ignored) {
        }
        assertTrue(Turner.isTurn);
        try {
            turner.makeATurn(0, 3); //сменить активную шашку на соседнюю
        } catch (NullPointerException ignored) {
        }
        assertTrue(Turner.isTurn);
        try {
            turner.makeATurn(0, 3); //попытка убрать активацию хода(выбор той же шашки)
        } catch (NullPointerException ignored) {
        }



        try {
            turner.makeATurn(2, 1);
        } catch (NullPointerException ignored) {
        }
        assertTrue(Turner.isTurn); //Начался ли ход?

        try {
            turner.makeATurn(3, 2); //Попытка переместиться в эту клетку
        } catch (NullPointerException ignored) {
        }

        CheckersBoard.checkers[3][6].color = "White";
        try {
            turner.makeATurn(2, 5); //Выбираю новую шашку
        } catch (NullPointerException ignored) {
        }

        try {
            turner.makeATurn(4, 7); //Пытаюсь съесть белую
        } catch (NullPointerException ignored) {
        }
        assertEquals(2, Turner.resultOfLastMove);

        //NO MORE TESTS DUE TO REQUIRED GRAPHIC, ALL TESTED BY HANDS ANYWAY

    }


}
