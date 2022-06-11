package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;
import screens.GameScreen;


public class Player {
    private byte hp; // характеристики персонажа
    private byte dmg;
    private byte moveSpeed;
    public boolean isAlive = true;
    public boolean isDash = false;

    public Body body; // тело персонажа

    public Player(GameScreen.CHARACTER_CLASS type, Body body) {
        this.body = body;
        switch (type) {
            case GOBLIN:
                this.hp = 3;
                this.dmg = 2;
                this.moveSpeed = 25;
                break;
            case HOBGOBLIN:
                this.hp = 5;
                this.dmg = 2;
                this.moveSpeed = 16;
                break;
        }
    }

    public byte getHp() {
        return hp;
    }

    public byte getDmg() {
        return dmg;
    }

    public byte getMoveSpeed() {
        return moveSpeed;
    }

}