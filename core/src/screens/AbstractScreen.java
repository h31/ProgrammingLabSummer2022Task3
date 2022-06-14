package screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dasher.game.DasherMain;

public abstract class AbstractScreen implements Screen {
    protected final DasherMain app;
    Stage stage;

    protected AbstractScreen(final DasherMain app) {
        this.app = app;
        this.stage = new Stage(new ScreenViewport());
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
