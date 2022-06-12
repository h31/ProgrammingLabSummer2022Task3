package com.dasher.game.managers;

import com.dasher.game.DasherMain;
import screens.AbstractScreen;
import screens.GameScreen;
import screens.MenuScreen;
import screens.SplashScreen;

import java.util.EnumMap;

public class GameScreenManager {
    private final DasherMain app;
    private EnumMap<STATES, AbstractScreen> gameScreens;

    public enum STATES {
        SPLASH,
        MAIN_MENU,
        PLAY_STAGE
    }

    public GameScreenManager(final DasherMain app) {
        this.app = app;
        initScreens();
        setScreen(STATES.SPLASH);
    }

    private void initScreens() {
        gameScreens = new EnumMap<STATES, AbstractScreen>(STATES.class);
        gameScreens.put(STATES.SPLASH, new SplashScreen(app));
        gameScreens.put(STATES.MAIN_MENU, new MenuScreen(app));
        gameScreens.put(STATES.PLAY_STAGE, new GameScreen(app));
    }

    public void setScreen(STATES next) {
        app.setScreen(gameScreens.get(next));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }
}
