package com.dasher.game;

public class Hobgoblin extends Player {
    public Hobgoblin() {
        this.hp = 5;
        this.dmg = 1;
        this.moveSpeed = 20;
        playerStartPosition.set(0.4f, 0.5f, 0);
    }
}
