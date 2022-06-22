package com.dasher.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    @Test
    public void deathTestKnight() {
        Enemy enemy = new Enemy(DasherMain.Collisions.KNIGHT, null);
        enemy.takeDmg(enemy.getHp());
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());
    }

    @Test
    public void noDeathTestKnight() {
        Enemy enemy = new Enemy(DasherMain.Collisions.KNIGHT, null);
        enemy.takeDmg(enemy.getHp() - 1);
        assertEquals(1, enemy.getHp());
        assertTrue(enemy.isAlive());
    }

    @Test
    public void deathTestWarrior() {
        Enemy enemy = new Enemy(DasherMain.Collisions.WARRIOR, null);
        enemy.takeDmg(enemy.getHp());
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());
    }

    @Test
    public void noDeathTestWarrior() {
        Enemy enemy = new Enemy(DasherMain.Collisions.WARRIOR, null);
        enemy.takeDmg(enemy.getHp() - 1);
        assertEquals(1, enemy.getHp());
        assertTrue(enemy.isAlive());
    }
}