package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.managers.GameScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.dasher.game.DasherMain.gsm;


public class MenuScreen extends AbstractScreen {
    private ImageButton buttonG, buttonH;
    private final TextureRegionDrawable drawableGob, drawableHob;
    private final Sound hitSound;
    private final Label text;

    public MenuScreen(final DasherMain app) {
        super(app);
        drawableGob = new TextureRegionDrawable(new TextureRegion(new Texture("Goblin.png")));
        drawableHob = new TextureRegionDrawable(new TextureRegion(new Texture("Hobgoblin.png")));
        text = new Label("Choose your character:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        text.setPosition(stage.getWidth() / 2 - text.getWidth() / 2, stage.getHeight() / 2 + 100);
        text.addAction(sequence(alpha(0), fadeIn(.5f)));
        stage.addActor(text);
        initBtn();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
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

    /**
     * Character selection buttons
     */
    private void initBtn() {
        buttonH = new ImageButton(drawableHob);
        buttonH.setPosition(stage.getWidth() / 2 - 200 - 52, stage.getHeight() / 2 - 32);
        buttonH.addAction(sequence(alpha(0), fadeIn(.5f)));
        buttonH.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hitSound.play(0.2f);
                app.type = GameScreen.CHARACTER_CLASS.HOBGOBLIN;
                gsm.setScreen(GameScreenManager.STATES.PLAY_STAGE);
            }
        });

        buttonG = new ImageButton(drawableGob);
        buttonG.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 - 32);
        buttonG.addAction(sequence(alpha(0), fadeIn(.5f)));
        buttonG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hitSound.play(0.2f);
                app.type = GameScreen.CHARACTER_CLASS.GOBLIN;
                gsm.setScreen(GameScreenManager.STATES.PLAY_STAGE);
            }
        });

        stage.addActor(buttonG);
        stage.addActor(buttonH);
    }
}
