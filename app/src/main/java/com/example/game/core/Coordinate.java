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

    public Coordinate(@NonNull Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public Coordinate copy() {
        return new Coordinate(this);
    }

    public Coordinate move(@NonNull Vector vector) {
        return new Coordinate(x + vector.x, y + vector.y);
    }

    public Coordinate moveBack(@NonNull Vector vector) {
        return new Coordinate(x - vector.x, y - vector.y);
    }

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