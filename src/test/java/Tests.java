import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import terraIncognita.App;
import terraIncognita.Model.Game;
import terraIncognita.Model.MovementDirection;
import terraIncognita.Model.Player;
import terraIncognita.Model.Tiles.StartTile;
import terraIncognita.Model.Tiles.WormholeTile;
import terraIncognita.Utils.Exceptions.ExceptionWrapper;
import terraIncognita.Utils.Exceptions.MultipleUsageOfSingleTileException;
import terraIncognita.Utils.Exceptions.NoNeededTileException;
import terraIncognita.Utils.Exceptions.NothingThrownException;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.TestUtils;
import java.io.FileNotFoundException;

public class Tests {

    private static final String TEST_LABYRINTHS_DIR = "src/test/resources/labyrinths/";

    private static void setupTest() {
        TestUtils.setIsConsoleOutput(true);
    }

    private static void rethrowWithoutWrapping(Runnable runnable) throws Throwable {
        Throwable res = null;
        try {
            runnable.run();
        } catch (RuntimeException e) {
            if (e instanceof ExceptionWrapper) {
                throw e.getCause();
            }
            throw e;
        }
        throw new NothingThrownException();
    }

    private static void assertThrowsWithUnwrapping(Class<? extends Throwable> expected, Runnable runnable) {
        assertThrows(
                expected,
                () -> rethrowWithoutWrapping(runnable)
        );
    }

    @Test
    void startGameTest() {
        setupTest();
        Player player = App.game.startGame(
                App.resourceLoader.getInputStreamOf(App.LABYRINTHS_RELATIVE_DIR + "lab1.txt")
        );
        Point startPos = new Point(1, 3);
        assertEquals(player.getPosition(), startPos);
        assertTrue(player.getDesk().getTileAt(startPos) instanceof StartTile);
        assertEquals(player, App.game.getActivePlayer());
    }

    @Test
    void freeMovementTest() {
        setupTest();
        Player player = App.game.startGame(
                App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test1.txt")
        );
        Point startPos = player.getPosition();

        player.move(MovementDirection.UP);
        assertEquals(new Point(startPos.x, startPos.y - 1), player.getPosition());
        player.move(MovementDirection.DOWN);
        assertEquals(startPos, player.getPosition());
        player.move(MovementDirection.RIGHT);
        assertEquals(new Point(startPos.x + 1, startPos.y), player.getPosition());
        player.move(MovementDirection.LEFT);
        assertEquals(startPos, player.getPosition());
    }

    @Test
    void wallsMovementTest() {
        setupTest();
        Player player = App.game.startGame(
                App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test2.txt")
        );
        Point startPos = player.getPosition();

        player.move(MovementDirection.UP);
        assertEquals(startPos, player.getPosition());
        player.move(MovementDirection.DOWN);
        assertEquals(startPos, player.getPosition());
        player.move(MovementDirection.LEFT);
        assertEquals(startPos, player.getPosition());
        player.move(MovementDirection.RIGHT);
        assertEquals(startPos, player.getPosition());
    }

    @Test
    void inc_dec_playerAmountTest() {
        setupTest();
        assertEquals(3, App.game.incPlayerAmount(1));
        assertEquals(2, App.game.decPlayerAmount(1));
        assertEquals(Game.MAX_PLAYER_AMOUNT, App.game.incPlayerAmount(Integer.MAX_VALUE));
        assertEquals(Game.MIN_PLAYER_AMOUNT, App.game.decPlayerAmount(Integer.MAX_VALUE));
    }

    @Test
    void notFoundLabyrinthTest() {
        setupTest();

        assertThrowsWithUnwrapping(
                FileNotFoundException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "asdasdsad.txt")
                )
        );
    }

    @Test
    void invalidLabyrinthTest() {
        setupTest();
        assertThrowsWithUnwrapping(
                MultipleUsageOfSingleTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test3.txt") // two starts
                )
        );

        assertThrowsWithUnwrapping(
                MultipleUsageOfSingleTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test4.txt") // two ends
                )
        );

        assertThrowsWithUnwrapping(
                MultipleUsageOfSingleTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test5.txt") // two treasures
                )
        );


        assertThrowsWithUnwrapping(
                NoNeededTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test6.txt") // no start
                )
        );

        assertThrowsWithUnwrapping(
                NoNeededTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test7.txt") // no end
                )
        );

        assertThrowsWithUnwrapping(
                NoNeededTileException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test8.txt") // no treasure
                )
        );

        assertThrowsWithUnwrapping(
                IllegalArgumentException.class,
                () -> App.game.startGame(
                        App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test10.txt")
                )
        );
    }

    @Test
    void wormholeTest() {
        setupTest();
        Player player = App.game.startGame(
                App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test9.txt")
        );
        Point startPos = player.getPosition();
        var unlockedTiles = player.move(MovementDirection.RIGHT);
        assertEquals(player.getPosition(), new Point(1, 3));
        assertEquals(2, unlockedTiles.size());

        assertThrowsWithUnwrapping(
                IllegalArgumentException.class,
                () -> WormholeTile.getInstance('p')
        );
        assertThrowsWithUnwrapping(
                IndexOutOfBoundsException.class,
                () -> App.game.getNextWormholePosition(4)
        );
    }

    @Test
    void conditionsTest() {
        setupTest();
        Player player = App.game.startGame(
                App.resourceLoader.getInputStreamOf(TEST_LABYRINTHS_DIR + "test11.txt")
        );
        assertFalse(player.isEndGame());
        assertFalse(player.isHasTreasure());

        player.move(MovementDirection.RIGHT);
        assertFalse(player.isEndGame());
        assertTrue(player.isHasTreasure());

        player.move(MovementDirection.DOWN);
        assertTrue(player.isEndGame());
    }

}
