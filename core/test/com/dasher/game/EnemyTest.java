package com.dasher.game;

import org.junit.jupiter.api.Test;
import screens.GameScreen;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    @Test
    public void deathTestKnight() {
        Enemy enemy = new Enemy(GameScreen.COLLISIONS.KNIGHT, null);
        enemy.takeDmg(enemy.getHp());
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());
    }

    @Test
    public void noDeathTestKnight() {
        Enemy enemy = new Enemy(GameScreen.COLLISIONS.KNIGHT, null);
        enemy.takeDmg(enemy.getHp() - 1);
        assertEquals(1, enemy.getHp());
        assertTrue(enemy.isAlive());
    }

    @Test
    public void deathTestWarrior() {
        Enemy enemy = new Enemy(GameScreen.COLLISIONS.WARRIOR, null);
        enemy.takeDmg(enemy.getHp());
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());
    }

    @Test
    public void noDeathTestWarrior() {
        Enemy enemy = new Enemy(GameScreen.COLLISIONS.WARRIOR, null);
        enemy.takeDmg(enemy.getHp() - 1);
        assertEquals(1, enemy.getHp());
        assertTrue(enemy.isAlive());
    }
}