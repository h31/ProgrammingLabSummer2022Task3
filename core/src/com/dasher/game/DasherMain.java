package com.dasher.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dasher.game.managers.GameScreenManager;
import screens.GameScreen;

import java.util.ArrayList;

public class DasherMain extends Game {
    public static final float PPM = 64; // Pixel Per Meter need to convert big bodies to small
    public static GameScreenManager gsm;

    public SpriteBatch batch; // Main texture rendering tool
    public Player player;
    public GameScreen.CHARACTER_CLASS type;
    public ArrayList<Enemy> enemyList;
    public Preferences prefs;
    public int score;
    public int highScore;


    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("GamePreferences");
        batch = new SpriteBatch();
        gsm = new GameScreenManager(this);
    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gsm.dispose();
    }
}