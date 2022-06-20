package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.managers.GameScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.dasher.game.DasherMain.gsm;


public class SplashScreen extends AbstractScreen {
    private final Sound startSound = Gdx.audio.newSound(Gdx.files.internal("startSound.mp3"));
    private final Image image;
    private final Label text;
    private int i = 1;

    public SplashScreen(final DasherMain app) {
        super(app);
        image = new Image(new Texture("Goblin.png"));
        text = new Label("Task 3", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        text.setPosition(stage.getWidth() / 2 - text.getWidth() / 2, stage.getHeight() / 2 - 55);
        text.addAction(sequence(alpha(0f), fadeIn(1.5f), fadeOut(1f)));
        image.setPosition(stage.getWidth() / 2 - 28, stage.getHeight() / 2 - 32);
        image.addAction(sequence(alpha(0f), fadeIn(1.5f), fadeOut(1f)));
        stage.addActor(image);
        stage.addActor(text);
        startSound.play(0.4f);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        if (i++ > 300) gsm.setScreen(GameScreenManager.STATES.MAIN_MENU);
        if (Gdx.input.isTouched()) gsm.setScreen(GameScreenManager.STATES.MAIN_MENU);
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
