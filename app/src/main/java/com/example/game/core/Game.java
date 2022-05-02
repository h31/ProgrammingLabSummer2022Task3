package com.example.game.core;

import androidx.core.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private final int FIELD_SIZE;
    private final HashMap<Coordinate, Integer> squares = new HashMap<>();

    public Game(int fieldSize) {
        FIELD_SIZE = fieldSize;
    }

    /**
     * Возвращает способ обхода поля
     *
     * @param vector направление обхода
     * @return Пару из координат по x и y, по которым обходить поле
     **/
    Pair<List<Integer>, List<Integer>> buildTraversals(Vector vector) {
        Pair<List<Integer>, List<Integer>> traversals =
                new Pair<>(new LinkedList<>(), new LinkedList<>());
        for (int i = 0; i < FIELD_SIZE; i++) {
            traversals.first.add(vector.x != 1 ? i : (FIELD_SIZE - i - 1));
            traversals.second.add(vector.y != 1 ? i : (FIELD_SIZE - i - 1));
        }
        return traversals;
    }

    /**
     * Возвращает перемещение клетки до препядствия/конца карты
     *
     * @param square клетка, которая передвигается
     * @param vector вектор перемещения
     * @return Перемещение (если остается на месте, то в перемещении 2 одинаковые координаты)
     **/
    Coordinate.Move findNewPosition(Coordinate square, Vector vector) {
        Coordinate newSquare = square.copy();
        while ((newSquare = newSquare.move(vector)).checkCorrect(FIELD_SIZE)) {
            Integer targetNum = squares.get(newSquare);
            if (targetNum == null) continue;
            if (targetNum.equals(squares.get(square)))
                return new Coordinate.Move(square, newSquare);
            break;
        }
        return new Coordinate.Move(square, newSquare.moveBack(vector));
    }

    /**
     * TODO
     *
     * @param direction TODO
     * @return TODO
     **/
    public List<Coordinate.Move> doMove(Direction direction) {
        Vector vector = direction.getVector();
        Pair<List<Integer>, List<Integer>> traversals = buildTraversals(vector);
        List<Coordinate.Move> moves = new LinkedList<>();
        for (int y : traversals.second)
            for (int x : traversals.first) {
                Coordinate square = new Coordinate(x, y);
                if (squares.get(square) != null) {
                    //TODO
                }
            }
        return moves;
    }

    /**
     * Функция исключительно для тестирования, добавляет новую цифру на поле
     *
     * @param square координаты цифры
     * @param number значение цифры
     **/
    void setSquare(Coordinate square, int number) {
        squares.put(square, number);
    }
}
