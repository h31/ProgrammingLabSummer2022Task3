package com.example.game.core;

import junit.framework.TestCase;

import androidx.core.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameTest extends TestCase {

    /**
     * @return возврщает лист, заполненный числами от a до b
     **/
    private List<Integer> fromAtoB(int a, int b) {
        List<Integer> list = new LinkedList<>();
        while (a != b) list.add(a > b ? --a : a++);
        return list;
    }

    /**
     * Функция для теста функции buildTraversals с разными размерами поля
     **/
    private void testBuildTraversals(int fieldSize) {
        Game game = new Game(fieldSize);
        assertEquals(new Pair<>(fromAtoB(0, fieldSize), fromAtoB(0, fieldSize)),
                game.buildTraversals(Direction.LEFT.getVector()));
        assertEquals(new Pair<>(fromAtoB(fieldSize, 0), fromAtoB(0, fieldSize)),
                game.buildTraversals(Direction.RIGHT.getVector()));
        assertEquals(new Pair<>(fromAtoB(0, fieldSize), fromAtoB(fieldSize, 0)),
                game.buildTraversals(Direction.DOWN.getVector()));
        assertEquals(new Pair<>(fromAtoB(0, fieldSize), fromAtoB(0, fieldSize)),
                game.buildTraversals(Direction.UP.getVector()));
    }

    // Проверка, что функция возращает корректные рельсы на разных размерах поля
    public void testBuildTraversals() {
        for (int i = 3; i < 10; i++) testBuildTraversals(i);
    }

    // Проверка, что функция возвращает корректную новую позицию в разных условиях
    public void testFindNewPosition() {
        final int fieldSize = 5;
        Game game = new Game(fieldSize);
        Coordinate firstSquare = new Coordinate(0, 0);
        Coordinate secondSquare = new Coordinate(0, 3);
        game.setSquare(firstSquare, 2);
        game.setSquare(secondSquare, 2);
        assertEquals(new Coordinate.Move(secondSquare, firstSquare),
                game.findNewPosition(secondSquare, Direction.UP.getVector()));
        assertEquals(new Coordinate.Move(firstSquare, secondSquare),
                game.findNewPosition(firstSquare, Direction.DOWN.getVector()));
        assertEquals(new Coordinate.Move(secondSquare, new Coordinate(secondSquare.x, fieldSize - 1)),
                game.findNewPosition(secondSquare, Direction.DOWN.getVector()));
        assertEquals(new Coordinate.Move(secondSquare, new Coordinate(0, secondSquare.y)),
                game.findNewPosition(secondSquare, Direction.LEFT.getVector()));
        assertEquals(new Coordinate.Move(firstSquare, new Coordinate(fieldSize - 1, firstSquare.y)),
                game.findNewPosition(firstSquare, Direction.RIGHT.getVector()));
        game.setSquare(secondSquare, 4);
        assertEquals(new Coordinate.Move(secondSquare, new Coordinate(0, 1)),
                game.findNewPosition(secondSquare, Direction.UP.getVector()));
    }

    // Проверка что 2 одинаковых числа соединились, а разных нет
    public void testDoMoveMerging() {
        final int fieldSize = 5;
        Game game = new Game(fieldSize);
        // Создание двух цифр 2 и их соеднинение
        Coordinate firstSquare = new Coordinate(0, 0);
        Coordinate secondSquare = new Coordinate(0, 3);
        game.setSquare(firstSquare, 2);
        game.setSquare(secondSquare, 2);
        Set<Coordinate.Move> movesExpectedFirst = new HashSet<>();
        movesExpectedFirst.add(new Coordinate.Move(secondSquare, firstSquare));
        assertEquals(movesExpectedFirst, game.doMove(Direction.UP));
        Map<Coordinate, Integer> exceptedFieldFirst = new HashMap<>();
        exceptedFieldFirst.put(firstSquare, 4);
        assertEquals(exceptedFieldFirst, game.getSquares());
        // Создание цифры 2 и проверка, что она не соединится с 4
        game.setSquare(secondSquare, 2);
        Set<Coordinate.Move> movesExpectedSecond = new HashSet<>();
        movesExpectedSecond.add(new Coordinate.Move(secondSquare,
                firstSquare.moveBack(Direction.UP.getVector())));
        assertEquals(movesExpectedSecond, game.doMove(Direction.UP));
        Map<Coordinate, Integer> exceptedFieldSecond = new HashMap<>();
        exceptedFieldSecond.put(firstSquare, 4);
        exceptedFieldSecond.put(firstSquare.moveBack(Direction.UP.getVector()), 2);
        assertEquals(exceptedFieldSecond, game.getSquares());
    }

    // Проверка, что числа останавливаются у границы
    public void testDoMoveBorders() {
        final int fieldSize = 5;
        Game game = new Game(fieldSize);
        // Создание 2 цифр с перемещением их к границе
        Coordinate firstSquare = new Coordinate(1, 0);
        Coordinate secondSquare = new Coordinate(0, 3);
        game.setSquare(firstSquare, 2);
        game.setSquare(secondSquare, 2);
        Set<Coordinate.Move> movesExpected = new HashSet<>();
        movesExpected.add(new Coordinate.Move(firstSquare, new Coordinate(fieldSize - 1, 0)));
        movesExpected.add(new Coordinate.Move(secondSquare, new Coordinate(fieldSize - 1, 3)));
        assertEquals(movesExpected, game.doMove(Direction.RIGHT));
        Map<Coordinate, Integer> exceptedField = new HashMap<>();
        exceptedField.put(new Coordinate(fieldSize - 1, 0), 2);
        exceptedField.put(new Coordinate(fieldSize - 1, 3), 2);
        assertEquals(exceptedField, game.getSquares());
        // Повтор хода к границе, чтоб проверить, что фигуры не двигались
        assertEquals(new HashSet<>(), game.doMove(Direction.RIGHT));
        assertEquals(exceptedField, game.getSquares());
    }

    // Проверка интересного частного случая, когда фигуры должны объединиться
    // и опуститься до границы поля
    public void testDoMoveOtherFirst() {
        final int fieldSize = 5;
        Game game = new Game(fieldSize);
        Coordinate firstSquare = new Coordinate(1, 0);
        Coordinate secondSquare = new Coordinate(1, 2);
        game.setSquare(firstSquare, 2);
        game.setSquare(secondSquare, 2);
        Set<Coordinate.Move> movesExpected = new HashSet<>();
        movesExpected.add(new Coordinate.Move(firstSquare, new Coordinate(1, fieldSize - 1)));
        movesExpected.add(new Coordinate.Move(secondSquare, new Coordinate(1, fieldSize - 1)));
        assertEquals(movesExpected, game.doMove(Direction.DOWN));
        Map<Coordinate, Integer> exceptedField = new HashMap<>();
        exceptedField.put(new Coordinate(1, fieldSize - 1), 4);
        assertEquals(exceptedField, game.getSquares());
    }

    // Проверка интересного частного случая, когда фигуры должны объединиться
    // и опуститься до границы поля сразу две пары (т.е. 2 2 2 2 -> 0 0 4 4 при движение вправо)
    public void testDoMoveOtherSecond() {
        final int fieldSize = 4;
        Game game = new Game(fieldSize);
        for (int x = 0; x < fieldSize; x++) {
            game.setSquare(new Coordinate(x, 0), 2);
        }
        Set<Coordinate.Move> movesExpected = new HashSet<>();
        movesExpected.add(new Coordinate.Move(new Coordinate(0, 0), new Coordinate(2, 0)));
        movesExpected.add(new Coordinate.Move(new Coordinate(1, 0), new Coordinate(2, 0)));
        movesExpected.add(new Coordinate.Move(new Coordinate(2, 0), new Coordinate(3, 0)));
        assertEquals(movesExpected, game.doMove(Direction.RIGHT));
        Map<Coordinate, Integer> exceptedField = new HashMap<>();
        exceptedField.put(new Coordinate(2, 0), 4);
        exceptedField.put(new Coordinate(3, 0), 4);
        assertEquals(exceptedField, game.getSquares());
    }

    // Проверка функции SpawnSquare на работу
    public void testSpawnSquare() {
        final int fieldSize = 4;
        Game game = new Game(fieldSize);
        for (int i = 0; i < fieldSize * fieldSize; i++) {
            Pair<Coordinate, Integer> spawnedSquare = game.spawnSquare();
            assertNotNull(spawnedSquare);
            assertNotNull(game.getSquares().get(spawnedSquare.first));
            assertEquals(spawnedSquare.second, game.getSquares().get(spawnedSquare.first));
        }
        assertNull(game.spawnSquare());
    }
}