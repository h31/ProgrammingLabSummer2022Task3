package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;
import screens.GameScreen;

public class Player {
    public byte hp;
    public byte moveSpeed;
    public boolean isAlive = true;
    public boolean isDash = false;
    public Body body;

    public Player(GameScreen.CHARACTER_CLASS type, Body body) {
        this.body = body;
        switch (type) {
            case GOBLIN:
                this.hp = 3;
                this.moveSpeed = 25;
                break;
            case HOBGOBLIN:
                this.hp = 5;
                this.moveSpeed = 16;
                break;
        }
    }
}