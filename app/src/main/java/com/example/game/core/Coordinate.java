package com.example.game.core;

public class Coordinate {
    public int x;
    public int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static class Move {
        public Coordinate from;
        public Coordinate to;

        public Move(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }
    }
}