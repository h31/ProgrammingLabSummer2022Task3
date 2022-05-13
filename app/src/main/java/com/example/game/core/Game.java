package com.example.game.core;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.example.game.ui.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Основсное игровое поле
 */
public class Game {
    private final int fieldSize;
    // Пары {Координата фигуры, Клетка}
    private final Map<Coordinate, Integer> squares = new HashMap<>();
    // Количество очков
    private int score;

    // Тег для логирования
    private final String TAG = this.getClass().getSimpleName();

    public Game(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    /**
     * Функция для перемещения всех цифр
     *
     * @param direction направление перемещения
     * @return set с перемещениями цифр
     **/
    public List<Coordinate.Move> doMove(@NonNull Direction direction) {
        Vector vector = direction.getVector();
        List<Integer> traversalX = buildTraversal(vector.x);
        List<Integer> traversalY = buildTraversal(vector.y);
        List<Coordinate> combinedSquares = new ArrayList<>();
        // Все перемещения. ВАЖЕН ПОРЯДОК!
        List<Coordinate.Move> moves = new ArrayList<>();
        for (int y : traversalY)
            for (int x : traversalX) {
                // Получаем фигуру на координатах
                Coordinate square = new Coordinate(x, y);
                Integer movedNumber = squares.get(square);
                // Проверка, что в клетке фигура существует
                if (movedNumber == null) continue;
                Coordinate.Move move = findNewPosition(square, vector, combinedSquares);
                // Проверка, что фигура двигается
                if (move.from.equals(move.to)) continue;
                moves.add(move);
                // Смена цифры в клетке, если в новой координате есть цифра
                Integer targetNumber = squares.get(move.to);
                squares.put(move.to, movedNumber + (targetNumber == null ? 0 : targetNumber));
                if (targetNumber != null) {
                    score += movedNumber + targetNumber;
                    combinedSquares.add(move.to);
                }
                // Удаление фигуры с координаты исходника
                squares.remove(square);
            }
        Log.d(TAG, "\tBoard after move:");
        // Возвращаем информацию о перемещениях в false
        for (Map.Entry<Coordinate, Integer> i : squares.entrySet()) {
            Log.d(TAG, "\t\t" + i.getKey() + " = " + i.getValue());
        }
        Log.d(TAG, "\t\t" + squares.size());
        return moves;
    }

    /**
     * Возвращает способ обхода поля
     *
     * @param coefficient направление обхода по оси
     * @return Рельсу (лист) по которому необходимо обходить поле
     **/
    List<Integer> buildTraversal(int coefficient) {
        List<Integer> traversal = new ArrayList<>();
        for (int i = 0; i < fieldSize; i++)
            traversal.add(i);
        if (coefficient == 1) Collections.reverse(traversal);
        return traversal;
    }

    /**
     * Возвращает перемещение клетки до препядствия/конца карты
     *
     * @param square          клетка, которая передвигается
     * @param vector          вектор перемещения
     * @param combinedSquares лист с клетками, которые уже объединялись
     * @return Перемещение (если остается на месте, то в перемещении 2 одинаковые координаты)
     */
    Coordinate.Move findNewPosition(@NonNull Coordinate square, @NonNull Vector vector, List<Coordinate> combinedSquares) {
        Coordinate newSquare = square;
        int numOnSquare = Objects.requireNonNull(squares.get(square));
        while ((newSquare = newSquare.move(vector)).checkCorrect(fieldSize)) {
            Integer targetNum = squares.get(newSquare);
            if (targetNum == null) continue;
            if (targetNum == numOnSquare && !combinedSquares.contains(newSquare))
                return new Coordinate.Move(square, newSquare);
            break;
        }
        return new Coordinate.Move(square, newSquare.moveBack(vector));
        // TODO - избавиться от moveBack()
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
        squares.put(coordinateNewSquare, number);
        return Pair.create(coordinateNewSquare, number);
    }

    /**
     * @return возвращает лист пустых координат
     **/
    @NonNull
    private List<Coordinate> getEmptySquares() {
        List<Coordinate> emptySquares = new LinkedList<>();
        for (int y = 0; y < fieldSize; y++)
            for (int x = 0; x < fieldSize; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (squares.get(coordinate) != null) continue;
                emptySquares.add(coordinate);
            }
        return emptySquares;
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
        if (squares.size() != fieldSize * fieldSize) return false;
        for (int y = 0; y < fieldSize - 1; y++)
            for (int x = 0; x < fieldSize - 1; x++) {
                int numInCoordinate = Objects.requireNonNull(squares.get(new Coordinate(x, y)));
                // Если снизу или справа от клетки совпадает число, тогда их можно совместить
                // А значит игра не проиграна
                if (numInCoordinate == Objects.requireNonNull(squares.get(new Coordinate(x, y + 1)))
                        || numInCoordinate == Objects.requireNonNull(squares.get(new Coordinate(x + 1, y))))
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
        squares.put(square, number);
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
        return squares;
    }
}
