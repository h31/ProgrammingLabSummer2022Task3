package com.example.game.core;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

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
