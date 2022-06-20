package com.dasher.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dasher.game.Enemy;
import screens.GameScreen;

import static com.dasher.game.DasherMain.gsm;


public class CollListener implements ContactListener {
    private final Sound damageSound = Gdx.audio.newSound(Gdx.files.internal("damage.mp3"));
    private final Vector2 addTarget = new Vector2();

    /**
     * This method activates when some fixtures collide
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        if (isEdge(fA, fB)) {
            damageSound.play(0.2f);
            gsm.app.player.takeDmg((byte) 10);
        }
        // Damage system
        if (isAttack(fA, fB)) {
            if (!gsm.app.player.isDash) {
                damageSound.play(0.2f);
                switch ((GameScreen.COLLISIONS) fB.getBody().getUserData()) {
                    case KNIGHT:
                        gsm.app.player.takeDmg(GameScreen.COLLISIONS.KNIGHT.dmg);
                        break;
                    case WARRIOR:
                        gsm.app.player.takeDmg(GameScreen.COLLISIONS.WARRIOR.dmg);
                        break;
                }
            } else {
                for (Enemy enemy : gsm.app.enemyList) {
                    if (enemy.body.equals(fB.getBody())) {
                        addTarget.set(GameScreen.target).nor();
                        addTarget.set(addTarget.x * 100f, addTarget.y * 100f);
                        gsm.app.player.body.applyForceToCenter(addTarget, true);
                        enemy.takeDmg(1);
                        gsm.app.score += enemy.type.dmg;
                    }
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isEdge(Fixture fA, Fixture fB) {
        return fA.getBody().getUserData() == GameScreen.COLLISIONS.PLAYER
                && fB.getBody().getUserData() == GameScreen.COLLISIONS.DEATHZONE;
    }

    private boolean isAttack(Fixture fA, Fixture fB) {
        return fA.getBody().getUserData() == GameScreen.COLLISIONS.PLAYER
                && (fB.getBody().getUserData() == GameScreen.COLLISIONS.KNIGHT
                || fB.getBody().getUserData() == GameScreen.COLLISIONS.WARRIOR);
    }
}
