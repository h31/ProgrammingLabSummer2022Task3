package com.dasher.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dasher.game.DasherMain;
import com.dasher.game.Enemy;

import java.util.logging.Level;

import static com.dasher.game.DasherMain.logger;


public class CollListener implements ContactListener {
    private final Sound damageSound = Gdx.audio.newSound(Gdx.files.internal("damage.mp3"));
    private final Vector2 addTarget = new Vector2();
    private final DasherMain app;

    public CollListener(DasherMain app){
        this.app = app;
    }
    /**
     * This method activates when some fixtures collide
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        if (isEdge(fA, fB)) {
            damageSound.play(0.2f);
            app.player.takeDmg(app.player.getHp());
            logger.log(Level.INFO, "Player touched edge");
        }
        // Damage system
        if (isAttack(fA, fB)) {
            if (!app.player.isDash) {
                damageSound.play(0.2f);
                switch ((DasherMain.Collisions) fB.getBody().getUserData()) {
                    case KNIGHT:
                        app.player.takeDmg(DasherMain.Collisions.KNIGHT.dmg);
                        logger.log(Level.INFO, "Player take " + DasherMain.Collisions.KNIGHT.dmg +
                                " dmg, Hp remains: " + app.player.getHp());
                        break;
                    case WARRIOR:
                        app.player.takeDmg(DasherMain.Collisions.WARRIOR.dmg);
                        logger.log(Level.INFO, "Player take " + DasherMain.Collisions.WARRIOR.dmg +
                                " dmg, Hp remains: " + app.player.getHp());
                        break;
                }
            } else {
                for (Enemy enemy : app.enemyList) {
                    if (enemy.body.equals(fB.getBody())) {
                        addTarget.set(app.target).nor();
                        addTarget.set(addTarget.x * 100f, addTarget.y * 100f);
                        app.player.body.applyForceToCenter(addTarget, true);
                        enemy.takeDmg(1);
                        app.score += enemy.type.dmg;
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
        return fA.getBody().getUserData() == DasherMain.Collisions.PLAYER
                && fB.getBody().getUserData() == DasherMain.Collisions.DEATHZONE;
    }

    private boolean isAttack(Fixture fA, Fixture fB) {
        return fA.getBody().getUserData() == DasherMain.Collisions.PLAYER
                && (fB.getBody().getUserData() == DasherMain.Collisions.KNIGHT
                || fB.getBody().getUserData() == DasherMain.Collisions.WARRIOR);
    }
}
