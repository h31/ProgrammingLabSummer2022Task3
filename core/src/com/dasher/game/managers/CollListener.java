package com.dasher.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;

import com.dasher.game.Enemy;

import static screens.GameScreen.enemyList;
import static screens.GameScreen.player;

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
            player.isAlive = false;
        }
        // Damage system
        if (isAttack(fA, fB)) {
            if (!player.isDash) {
                damageSound.play(0.4f);
                switch (fB.getBody().getUserData().toString()) {
                    case "KNIGHT":
                        player.hp -= 1;
                        break;
                    case "WARRIOR":
                        player.hp -= 2;
                        break;
                }
            } else {
                for (Enemy enemy : enemyList) {
                    if (enemy.body.equals(fB.getBody())) enemy.isAlive = false;
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
        return fA.getBody().getUserData() == "PLAYER" && fB.getBody().getUserData() == "DEATHZONE";
    }

    private boolean isAttack(Fixture fA, Fixture fB) {
        return fA.getBody().getUserData() == "PLAYER" &&
                (fB.getBody().getUserData() == "KNIGHT" || fB.getBody().getUserData() == "WARRIOR");
    }
}
