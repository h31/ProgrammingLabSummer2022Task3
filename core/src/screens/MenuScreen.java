package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.managers.GameScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class MenuScreen extends AbstractScreen {
    private ImageButton buttonG, buttonH;
    private Texture texGoblin, texHobgoblin;
    private TextureRegionDrawable drawableGob, drawableHob;

    public MenuScreen(DasherMain app) {
        super(app);
        texGoblin = new Texture("Goblin.png");
        drawableGob = new TextureRegionDrawable(new TextureRegion(texGoblin));
        texHobgoblin = new Texture("Hobgoblin.png");
        drawableHob = new TextureRegionDrawable(new TextureRegion(texHobgoblin));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
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
        app.batch.begin();
        app.font.draw(app.batch, "Choose your character", 570, 500);
        app.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        texGoblin.dispose();
        texHobgoblin.dispose();
    }

    private void initBtn() {
        buttonH = new ImageButton(drawableHob);
        buttonH.setPosition(stage.getWidth() / 2 - 200 - 52, stage.getHeight() / 2 - 32);
        buttonH.addAction(sequence(alpha(0), fadeIn(.5f)));
        buttonH.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.type = GameScreen.CHARACTER_CLASS.HOBGOBLIN;
                app.gsm.setScreen(GameScreenManager.STATES.PLAY_STAGE);
            }
        });

        buttonG = new ImageButton(drawableGob);
        buttonG.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 - 32);
        buttonG.addAction(sequence(alpha(0), fadeIn(.5f)));
        buttonG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.type = GameScreen.CHARACTER_CLASS.GOBLIN;
                app.gsm.setScreen(GameScreenManager.STATES.PLAY_STAGE);
            }
        });

        stage.addActor(buttonG);
        stage.addActor(buttonH);
    }
}
