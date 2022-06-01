package com.dasher.game;

public class Goblin extends Player {
    public Goblin() {
        this.hp = 3;
        this.dmg = 2;
        this.moveSpeed = 25;
        playerStartPosition.set(0.4f, 0.5f, 0);
    }
}
