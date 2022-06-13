package com.dasher.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dasher.game.managers.GameScreenManager;

public class DasherMain extends Game {
    public static final float PPM = 64; // Pixel Per Meter need to convert big bodies to small

    public SpriteBatch batch; // Main texture rendering tool
    public BitmapFont font; // Basic text font
    public GameScreenManager gsm;

    @Override
    public void create() {
        font = new BitmapFont();
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
        font.dispose();
        batch.dispose();
        gsm.dispose();
    }
}