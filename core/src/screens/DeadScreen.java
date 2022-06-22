package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.managers.GameScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class DeadScreen extends AbstractScreen {
    private final Image gameOverText;
    private Image back;
    private int i; // Simple counter create delay to give player some time to realize that game is over

    public DeadScreen(final DasherMain app) {
        super(app);
        gameOverText = new Image(new Texture("game_over.png"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.clear();
        back = app.score > 10 ? new Image(new Texture("waterBack.png")) : new Image(new Texture("earthBack.png"));
        back.setPosition(0, 0);
        stage.addActor(back);

        gameOverText.setScale(0.5f);
        gameOverText.setPosition(400, 100);
        gameOverText.addAction(sequence(alpha(0f), fadeIn(1f)));
        stage.addActor(gameOverText);
        i = 0;
    }

    /**
     * Game renewal
     */
    @Override
    public void update(float delta) {
        stage.act(delta);
        if (i++ > 150) {
            app.gsm.setScreen(GameScreenManager.States.MAIN_MENU);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.valueOf("709f6e"));
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
