package com.dasher.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.dasher.game.DasherMain.PPM;

public class Player {
    private byte hp; // характеристики персонажа
    private byte dmg;
    private float moveSpeed;
    private String typeName;
    public boolean isAlive = true;
    public boolean isDash = false;

    protected Body body; // тело персонажа

    public Player(String type) {
        switch (type) {
            case "Goblin":
                this.hp = 3;
                this.dmg = 2;
                this.moveSpeed = 25;
                this.typeName = "Goblin.png";
                break;
            case "Hobgoblin":
                this.hp = 5;
                this.dmg = 2;
                this.moveSpeed = 18;
                this.typeName = "Hobgoblin.png";
                break;
        }
    }

    /**
     * инициализация тела персонажа
     */
    public Body createBody() {
        BodyDef definitions = new BodyDef();
        definitions.type = BodyDef.BodyType.DynamicBody;
        definitions.position.set(0.4f, 0.5f);
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

    public String getTypeName() {
        return typeName;
    }
}