package com.example.game.core;

/**
 * Направления, куда передвигать фигуры
 */
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    /**
     * @return единичный вектор в указаном направлении
     */
    Vector getVector() {
        switch (this) {
            case LEFT:
                return new Vector(-1, 0);
            case RIGHT:
                return new Vector(1, 0);
            case UP:
                return new Vector(0, -1);
            case DOWN:
                return new Vector(0, 1);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}

/**
 * Класс для хранения направления куда перемещаются фигуры
 */
class Vector {
    public int x;
    public int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }
}