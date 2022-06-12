package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;

public class Enemy {
    public byte hp; // характеристики противника
    private byte dmg;
    private float moveSpeed;
    public ENEMY_TYPE type;
    public boolean isAlive = true;
    public Body body; // тело противника

    public enum ENEMY_TYPE {
        KNIGHT,
        WARRIOR
    }

    public Enemy(ENEMY_TYPE type, Body body) {
        this.body = body;
        switch (type) {
            case KNIGHT:
                this.hp = 2;
                this.type = type;
                break;
            case WARRIOR:
                this.hp = 1;
                this.moveSpeed = 1.5f;
                this.type = type;
                break;
        }
    }

    public byte getHp() {
        return hp;
    }

    public byte getDmg() {
        return dmg;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public String getType() {
        return type.toString();
    }
}
