package com.dasher.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void deathTestGoblin() {
        Player player = new Player(DasherMain.CharacterClass.GOBLIN, null);
        player.takeDmg(player.getHp());
        assertEquals(0, player.getHp());
        assertFalse(player.isAlive());
    }

    @Test
    public void noDeathTestGoblin() {
        Player player = new Player(DasherMain.CharacterClass.GOBLIN, null);
        player.takeDmg(player.getHp() - 1);
        assertEquals(1, player.getHp());
        assertTrue(player.isAlive());
    }

    @Test
    public void deathTestHobgoblin() {
        Player player = new Player(DasherMain.CharacterClass.HOBGOBLIN, null);
        player.takeDmg(player.getHp());
        assertEquals(0, player.getHp());
        assertFalse(player.isAlive());
    }

    @Test
    public void noDeathTestHobgoblin() {
        Player player = new Player(DasherMain.CharacterClass.HOBGOBLIN, null);
        player.takeDmg(player.getHp() - 1);
        assertEquals(1, player.getHp());
        assertTrue(player.isAlive());
    }
}
