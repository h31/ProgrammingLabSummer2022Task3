package com.example.game.core;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Цифра и информация, перемещалась ли клетка на итерации
 */
class Square {
    public Integer number;
    public Boolean isUsed;

    public Square(Integer number, Boolean isUsed) {
        this.number = number;
        this.isUsed = isUsed;
    }

    /**
     * Создаем новый квадрат
     */
    static public Square create(Integer number, Boolean isUsed) {
        return new Square(number, isUsed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(number, square.number) && Objects.equals(isUsed, square.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, isUsed);
    }

    @NonNull
    @Override
    public String toString() {
        return "Square{" +
                "first=" + number +
                ", second=" + isUsed +
                '}';
    }
}
