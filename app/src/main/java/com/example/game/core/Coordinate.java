package com.example.game.core;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private Coordinate(@NonNull Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    /**
     * Создает копию объекта
     * */
    public Coordinate copy() {
        return new Coordinate(this);
    }

    /**
     * Функция для перемещения на vector
     *
     * @param vector вектор перемещения
     * @return новая координата
     * */
    public Coordinate move(@NonNull Vector vector) {
        return new Coordinate(x + vector.x, y + vector.y);
    }

    /**
     * Функция для перемещения в противоположную сторону от vector
     *
     * @param vector вектор перемещения
     * @return новая координата
     * */
    public Coordinate moveBack(@NonNull Vector vector) {
        return new Coordinate(x - vector.x, y - vector.y);
    }

    /**
     * Функция проверки корректности координаты
     *
     * @param fieldSize размеры поля, для проверки, что координата не за пределами поля
     * @return true/false если координата корретна/не корректна
     * */
    public boolean checkCorrect(int fieldSize) {
        return x < fieldSize && x >= 0 &&
                y < fieldSize && y >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @NonNull
    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static class Move {
        public Coordinate from;
        public Coordinate to;

        public Move(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return Objects.equals(from, move.from) && Objects.equals(to, move.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @NonNull
        @Override
        public String toString() {
            return "Move{" +
                    "from=" + from +
                    ", to=" + to +
                    '}';
        }
    }
}