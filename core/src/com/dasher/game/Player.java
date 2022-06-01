package com.dasher.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.dasher.game.DasherMain.PPM;

public abstract class Player {
    protected byte hp; // характеристики персонажа
    protected byte dmg;
    protected float moveSpeed;
    protected boolean isAlive = true;
    protected boolean isDash = false;

    protected Body body; // тело персонажа

    protected final Vector3 playerStartPosition = new Vector3(); // начальные координаты

    /**
     * инициализация тела персонажа
     */
    protected Body createBody() {

        BodyDef definitions = new BodyDef();
        definitions.type = BodyDef.BodyType.DynamicBody;
        definitions.position.set(playerStartPosition.x, playerStartPosition.y);
        definitions.fixedRotation = true;
        definitions.linearDamping = 10f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(26 / PPM, 32 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = DasherMain.PLAYER;
        fixtureDef.filter.maskBits = DasherMain.BOX;

        return DasherMain.world.createBody(definitions).createFixture(fixtureDef).getBody();
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
}
