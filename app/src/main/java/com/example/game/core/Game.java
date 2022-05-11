package com.example.game.core;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Основсное игровое поле
 */
public class Game {
    private final int FIELD_SIZE;
    // Пары {Координата фигуры, Клетка}
    private final Map<Coordinate, Square> squares = new HashMap<>();
    // Количество очков
    private int score;

    // Тег для логирования
    private final String TAG = this.getClass().getSimpleName();

    public Game(int fieldSize) {
        FIELD_SIZE = fieldSize;
    }

    /**
     * Возвращает способ обхода поля
     *
     * @param vector направление обхода
     * @return Пару из координат по x и y, по которым обходить поле
     **/
    Pair<List<Integer>, List<Integer>> buildTraversals(@NonNull Vector vector) {
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
    Coordinate.Move findNewPosition(@NonNull Coordinate square, @NonNull Vector vector) {
        Coordinate newSquare = square.copy();
        int numOnSquare = Objects.requireNonNull(squares.get(square)).number;
        while ((newSquare = newSquare.move(vector)).checkCorrect(FIELD_SIZE)) {
            Square targetNum = squares.get(newSquare);
            if (targetNum == null) continue;
            if (targetNum.number.equals(numOnSquare) && !targetNum.isUsed)
                return new Coordinate.Move(square, newSquare);
            break;
        }
        return new Coordinate.Move(square, newSquare.moveBack(vector));
    }

    /**
     * Функция для перемещения всех цифр
     *
     * @param direction направление перемещения
     * @return set с перемещениями цифр
     **/
    public List<Coordinate.Move> doMove(@NonNull Direction direction) {
        Vector vector = direction.getVector();
        Pair<List<Integer>, List<Integer>> traversals = buildTraversals(vector);
        // Все перемещения. ВАЖЕН ПОРЯДОК!
        List<Coordinate.Move> moves = new ArrayList<>();
        for (int y : traversals.second)
            for (int x : traversals.first) {
                // Получаем фигуру на координатах
                Coordinate square = new Coordinate(x, y);
                Square number = squares.get(square);
                // Проверка, что в клетке фигура существует
                if (number == null) continue;
                Coordinate.Move move = findNewPosition(square, vector);
                // Проверка, что фигура двигается
                if (move.from.equals(move.to)) continue;
                moves.add(move);
                // Смена цифры в клетке, если в новой координате есть цифра
                Square targetNumber = squares.get(move.to);
                squares.put(move.to, Square.create(
                        number.number + (targetNumber == null ? 0 : targetNumber.number),
                        targetNumber != null));
                if (targetNumber != null) score += number.number + targetNumber.number;
                // Удаление фигуры с координаты исходника
                squares.remove(square);
            }
        Log.d(TAG, "\tBoard after move:");
        // Возвращаем информацию о перемещениях в false
        for (Map.Entry<Coordinate, Square> i : squares.entrySet()) {
            i.getValue().isUsed = false;
            Log.d(TAG, "\t\t" + i.getKey() + " = " + i.getValue().number);
        }
        Log.d(TAG, "\t\t" + squares.size());
        return moves;
    }

    /**
     * @return возвращает лист пустых координат
     **/
    @NonNull
    private List<Coordinate> getEmptySquares() {
        List<Coordinate> emptySquares = new LinkedList<>();
        for (int y = 0; y < FIELD_SIZE; y++)
            for (int x = 0; x < FIELD_SIZE; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (squares.get(coordinate) != null) continue;
                emptySquares.add(coordinate);
            }
        return emptySquares;
    }

    /**
     * Функция для создания новой клеточки (90% - 2, 10% - 4)
     *
     * @return коориданты созданной клетки и значение,
     * null если места для создания нет.
     */
    public Pair<Coordinate, Integer> spawnSquare() {
        List<Coordinate> emptySquares = getEmptySquares();
        if (emptySquares.size() == 0) return null;
        Coordinate coordinateNewSquare = emptySquares.get((int) (Math.random() * emptySquares.size()));
        int number = Math.random() < 0.9 ? 2 : 4;
        squares.put(coordinateNewSquare, Square.create(number, false));
        return Pair.create(coordinateNewSquare, number);
    }

    /**
     * Функция для получения текущих очков
     */
    public int getScore() {
        return score;
    }

    /**
     * Функция, проверяющая, проиграна ли игра
     *
     * @return true если програна false если можно продолжать
     */
    public boolean gameIsLost() {
        if (squares.size() != FIELD_SIZE * FIELD_SIZE) return false;
        for (int y = 0; y < FIELD_SIZE - 1; y++)
            for (int x = 0; x < FIELD_SIZE - 1; x++) {
                int numInCoordinate = Objects.requireNonNull(squares.get(new Coordinate(x, y))).number;
                // Если снизу или справа от клетки совпадает число, тогда их можно совместить
                // А значит игра не проиграна
                if (numInCoordinate == Objects.requireNonNull(Objects.requireNonNull(squares.get(new Coordinate(x, y + 1))).number)
                        || numInCoordinate == Objects.requireNonNull(squares.get(new Coordinate(x + 1, y))).number)
                    return false;
            }
        return true;
    }

    /**
     * Функция исключительно для тестирования, добавляет новую цифру на поле
     *
     * @param square координаты цифры
     * @param number значение цифры
     **/
    void setSquare(@NonNull Coordinate square, int number) {
        squares.put(square, Square.create(number, false));
    }

    /**
     * Функция исключительно для тестирования, удаляет цифру с поля
     *
     * @param square координаты цифры
     **/
    void removeSquare(@NonNull Coordinate square) {
        squares.remove(square);
    }

    /**
     * Функция исключительно для тестирования
     *
     * @return мапа из (координата -> значение)
     **/
    Map<Coordinate, Integer> getSquares() {
        Map<Coordinate, Integer> map = new HashMap<>();
        for (Map.Entry<Coordinate, Square> i : squares.entrySet()) {
            map.put(i.getKey(), i.getValue().number);
        }
        return map;
    }
}
