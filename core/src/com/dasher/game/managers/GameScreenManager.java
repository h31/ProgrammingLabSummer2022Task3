package com.dasher.game.managers;

import com.dasher.game.DasherMain;
import screens.*;

import java.util.EnumMap;
import java.util.Map;

public class GameScreenManager {
    public final DasherMain app;
    private final Map<States, AbstractScreen> gameScreens = new EnumMap<>(States.class);

    /**
     * Keys of screens in EnumMap
     */
    public enum States {
        SPLASH,
        MAIN_MENU,
        FOREST_STAGE,
        WATER_STAGE,
        DEAD_STAGE
    }

    public GameScreenManager(final DasherMain app) {
        this.app = app;
        initScreens();
        setScreen(States.SPLASH); // First screen
    }

    /**
     * Screens initialization
     */
    private void initScreens() {
        gameScreens.put(States.SPLASH, new SplashScreen(app));
        gameScreens.put(States.MAIN_MENU, new MenuScreen(app));
        gameScreens.put(States.FOREST_STAGE, new ForestScreen(app));
        gameScreens.put(States.WATER_STAGE, new WaterScreen(app));
        gameScreens.put(States.DEAD_STAGE, new DeadScreen(app));
    }

    /**
     * Set screen from EnumMap
     */
    public void setScreen(States next) {
        app.setScreen(gameScreens.get(next));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }
}
