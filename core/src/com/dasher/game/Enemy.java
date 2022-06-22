package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;

public class Enemy {
    private byte hp;
    public final float moveSpeed;
    public final DasherMain.Collisions type;
    public final Body body;

    public Enemy(DasherMain.Collisions type, Body body) {
        this.body = body;
        switch (type) {
            case KNIGHT:
                hp = 1;
                moveSpeed = 1.0f;
                this.type = type;
                break;
            case WARRIOR:
                hp = 2;
                moveSpeed = 0.5f;
                this.type = type;
                break;
            default:
                throw new IllegalArgumentException(); // to make fields final
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDmg(int dmg) {
        hp -= dmg;
    }

    /**
     * Only for tests
     */
    byte getHp() {
        return hp;
    }
}
