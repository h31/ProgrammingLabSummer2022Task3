package com.dasher.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dasher.game.managers.GameScreenManager;
import screens.GameScreen;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DasherMain extends Game {
    public static final float PPM = 64; // Pixel Per Meter need to convert big bodies to small

    public SpriteBatch batch; // Main texture rendering tool

    public Player player;
    public GameScreen.CHARACTER_CLASS type;
    public ArrayList<Enemy> enemyList;
    public int score;

    public static GameScreenManager gsm;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameScreenManager(this);
    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit(); // For ease of use
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gsm.dispose();
    }
}