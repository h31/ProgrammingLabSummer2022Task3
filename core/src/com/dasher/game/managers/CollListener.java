package com.dasher.game.managers;

import com.badlogic.gdx.physics.box2d.*;
import com.dasher.game.Enemy;

import static screens.GameScreen.enemyList;
import static screens.GameScreen.player;

public class CollListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        if (isEdge(fA, fB)) {
            player.isAlive = false;
        }
        if (isAttack(fA, fB)) {
            if (player.isDash == false) {
                switch (fB.getBody().getUserData().toString()) {
                    case "KNIGHT":
                        player.hp -= 1;
                        break;
                    case "WARRIOR":
                        player.hp -= 2;
                        break;
                }
            }
            else {
                for (Enemy enemy : enemyList) {
                    if (enemy.body.equals(fB.getBody())) enemy.hp -= player.getDmg();
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
