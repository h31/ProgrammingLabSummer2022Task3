package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.Enemy;
import com.dasher.game.Player;
import com.dasher.game.managers.CollListener;
import com.dasher.game.managers.GameScreenManager;

import java.util.ArrayList;

import static com.dasher.game.DasherMain.PPM;

public class ForestScreen extends AbstractScreen {
    // libGDX objects
    private World world;
    private final Image earth;
    private Body deathZoneLeft, deathZoneRight, deathZoneTop, deathZoneBottom;

    public ForestScreen(final DasherMain app) {
        super(app);
        // Assets setting
        earth = new Image(new Texture("earthBack.png"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        app.text.setColor(Color.WHITE);
        app.lastDashTime = 0;
        app.enemyCounter = 0;

        app.highScore = app.prefs.getInteger("highScore");
        app.score = 0;

        // Setting world and some modifications
        world = new World(new Vector2(0f, 0f), false);
        app.batch.setProjectionMatrix(app.camera.combined);

        // Enemies and player creation
        app.enemyList = new ArrayList<>();
        app.player = new Player(app.type, app.createBox
                (world, 0.4f, 0.5f, 22, 28, DasherMain.Collisions.PLAYER, BodyDef.BodyType.DynamicBody, 10f, true));
        app.playerTex = app.type.equals(DasherMain.CharacterClass.GOBLIN) ? new Texture("Goblin.png") : new Texture("Hobgoblin.png");

        // Map edges
        deathZoneTop = app.createBox
                (world,0.295f, 4.8f, 547, 16, DasherMain.Collisions.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneBottom = app.createBox
                (world,0.295f, -5.4f, 547, 16, DasherMain.Collisions.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneLeft = app.createBox
                (world,-8f, -0.43f, 16, 350, DasherMain.Collisions.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneRight = app.createBox
                (world,8.6f, -0.43f, 16, 350, DasherMain.Collisions.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);

        world.setContactListener(new CollListener(app));
        stage.addActor(earth);
    }

    /**
     * Game logic
     */
    @Override
    public void update(float delta) {
        stage.act(delta);
        world.step(1 / 60f, 12, 4);

        app.playerAlive(world);
        app.enemyMove(world);

        // Enemy spawner
        if (TimeUtils.nanoTime() - app.lastEnemySpawn > 800000000 && app.enemyCounter <= 12) {
            app.enemySpawner(world);
            app.enemyCounter++;
        }

        if (app.score > 10) {
            app.enemyList.clear();
            world.destroyBody(app.player.body);
            app.gsm.setScreen(GameScreenManager.States.WATER_STAGE);
        }
    }

    /**
     * Rendering... Draw textures and background
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.valueOf("709f6e"));

        stage.draw();
        app.batch.begin();
        app.batch.draw(app.playerTex, app.player.body.getPosition().x * PPM - (app.playerTex.getWidth() / 2f),
                app.player.body.getPosition().y * PPM - (app.playerTex.getHeight() / 2f));

        for (Enemy enemy : app.enemyList) {
            switch (enemy.type) {
                case KNIGHT:
                    app.batch.draw(app.knightTex, enemy.body.getPosition().x * PPM - (app.knightTex.getWidth() / 2f),
                            enemy.body.getPosition().y * PPM - (app.knightTex.getHeight() / 2f));
                    break;
                case WARRIOR:
                    app.batch.draw(app.warriorTex, enemy.body.getPosition().x * PPM - (app.warriorTex.getWidth() / 2f),
                            enemy.body.getPosition().y * PPM - (app.warriorTex.getHeight() / 2f));
                    break;
            }
        }
        app.text.draw(app.batch, "Score : " + app.score, -640f, 360f);
        app.text.draw(app.batch, "High Score : " + app.highScore, -640f, 340f);
        app.text.draw(app.batch, "Hit points : " + app.player.getHp(), -640f, 320f);
        app.batch.end();
    }

    /**
     * Memory optimization
     */
    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }
}
