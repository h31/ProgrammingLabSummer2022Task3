package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;

public class Enemy {
    public float moveSpeed;
    public ENEMY_TYPE type;
    public boolean isAlive = true;
    public Body body;

    public enum ENEMY_TYPE {
        KNIGHT,
        WARRIOR
    }

    public Enemy(ENEMY_TYPE type, Body body) {
        this.body = body;
        switch (type) {
            case KNIGHT:
                this.moveSpeed = 1.1f;
                this.type = type;
                break;
            case WARRIOR:
                this.moveSpeed = 0.8f;
                this.type = type;
                break;
        }
    }
}
