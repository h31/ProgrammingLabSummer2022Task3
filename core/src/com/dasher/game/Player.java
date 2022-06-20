package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;
import screens.GameScreen;

public class Player {
    private byte hp;
    public final Body body;
    public final byte moveSpeed;
    public final long dashDelay;
    public final long dashLength;
    public boolean isDash = false;

    public Player(GameScreen.CHARACTER_CLASS type, Body body) {
        this.body = body;
        switch (type) {
            case GOBLIN:
                hp = 3;
                moveSpeed = 25;
                dashDelay = 800000000; // dash delay 0.8 sec
                dashLength = 250000000; // 0.25 sec frames of invulnerability
                break;
            case HOBGOBLIN:
                hp = 5;
                moveSpeed = 18;
                dashDelay = 650000000; //dash delay 0.65 sec
                dashLength = 170000000;
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

    public byte getHp() {
        return hp;
    }
}