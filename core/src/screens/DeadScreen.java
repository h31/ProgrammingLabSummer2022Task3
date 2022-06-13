package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.managers.GameScreenManager;

import static screens.GameScreen.*;

public class DeadScreen extends AbstractScreen {
    private Texture tex;
    private Texture earth;
    private int i; // Simple counter create delay to give player some time to realize that game is over

    public DeadScreen(DasherMain app) {
        super(app);
        tex = new Texture("game_over.png");
        earth = new Texture("earthBack.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        i = 0;
    }

    /**
     * Game renewal
     */
    @Override
    public void update(float delta) {
        stage.act(delta);
        if (i++ > 500) {
            world.destroyBody(player.body);
            enemyList.clear();
            // Using nulls to avoid bugs when recreate objects
            player = null;
            enemyList = null;
            app.gsm.setScreen(GameScreenManager.STATES.MAIN_MENU);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.valueOf("709f6e"));
        stage.draw();
        app.batch.begin();
        app.batch.draw(earth, -earth.getWidth() / 2, -earth.getHeight() / 2);
        app.batch.draw(tex, -135, -135, 270, 270);
        app.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
