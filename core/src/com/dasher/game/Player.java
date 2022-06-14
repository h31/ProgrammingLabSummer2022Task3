package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;
import screens.GameScreen;

public class Player {
    private byte hp;
    public final Body body;
    public final byte moveSpeed;
    public boolean isDash = false;

    public Player(GameScreen.CHARACTER_CLASS type, Body body) {
        this.body = body;
        switch (type) {
            case GOBLIN:
                hp = 3;
                moveSpeed = 25;
                break;
            case HOBGOBLIN:
                hp = 5;
                moveSpeed = 16;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDmg(int dmg) {
        hp -= dmg;
    }
}