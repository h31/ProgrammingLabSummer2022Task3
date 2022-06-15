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

    /**
     * This method activates when some fixtures collide
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        if (isEdge(fA, fB)) {
            damageSound.play(0.4f);
            gsm.app.player.takeDmg((byte) 5);
        }
        // Damage system
        if (isAttack(fA, fB)) {
            if (!gsm.app.player.isDash) {
                damageSound.play(0.4f);
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
