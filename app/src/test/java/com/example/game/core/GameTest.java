package com.example.game.core;

import junit.framework.TestCase;

import androidx.core.util.Pair;

import java.util.LinkedList;
import java.util.List;

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
}