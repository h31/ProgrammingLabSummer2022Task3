package com.example.game.core;

import java.util.Objects;

class Square {
    public Integer first;
    public Boolean second;

    public Square(Integer first, Boolean second) {
        this.first = first;
        this.second = second;
    }

    static public Square create(Integer first, Boolean second) {
        return new Square(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(first, square.first) && Objects.equals(second, square.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Square{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
