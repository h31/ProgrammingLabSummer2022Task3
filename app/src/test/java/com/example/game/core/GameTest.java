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

    // Проверка, что функция возращает корректные рельсы
    public void testBuildTraversals() {
        for (int i = 3; i < 10; i++) testBuildTraversals(i);
    }
}