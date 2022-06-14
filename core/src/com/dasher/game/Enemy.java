package com.dasher.game;

import com.badlogic.gdx.physics.box2d.Body;
import screens.GameScreen;

public class Enemy {
    public final float moveSpeed;
    public final GameScreen.COLLISIONS type;
    public final Body body;
    public boolean isAlive = true;

    public Enemy(GameScreen.COLLISIONS type, Body body) {
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
            default:
                throw new IllegalArgumentException(); // to make fields final
        }
    }
}
