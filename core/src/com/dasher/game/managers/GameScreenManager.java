package com.dasher.game.managers;

import com.dasher.game.DasherMain;
import screens.*;

import java.util.EnumMap;

public class GameScreenManager {
    public final DasherMain app;
    private final EnumMap<STATES, AbstractScreen> gameScreens = new EnumMap<>(STATES.class);;

    /**
     * Keys of screens in EnumMap
     */
    public enum STATES {
        SPLASH,
        MAIN_MENU,
        PLAY_STAGE,
        DEAD_STAGE
    }

    public GameScreenManager(final DasherMain app) {
        this.app = app;
        initScreens();
        setScreen(STATES.SPLASH); // First screen
    }

    /**
     * Screens initialization
     */
    private void initScreens() {
        gameScreens.put(STATES.SPLASH, new SplashScreen(app));
        gameScreens.put(STATES.MAIN_MENU, new MenuScreen(app));
        gameScreens.put(STATES.PLAY_STAGE, new GameScreen(app));
        gameScreens.put(STATES.DEAD_STAGE, new DeadScreen(app));
    }

    /**
     * Set screen from EnumMap
     */
    public void setScreen(STATES next) {
        app.setScreen(gameScreens.get(next));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }
}
