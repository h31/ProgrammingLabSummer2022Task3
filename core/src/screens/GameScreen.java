package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.Enemy;
import com.dasher.game.Player;
import com.dasher.game.managers.CollListener;
import com.dasher.game.managers.GameScreenManager;

import java.util.ArrayList;
import java.util.Iterator;

import static com.dasher.game.DasherMain.PPM;
import static com.dasher.game.DasherMain.gsm;

public class GameScreen extends AbstractScreen {
    public enum COLLISIONS {
        PLAYER, KNIGHT((byte) 1), WARRIOR((byte) 2), DEATHZONE;

        public byte dmg;

        COLLISIONS() {

        }

        COLLISIONS(byte dmg) {
            this.dmg = dmg;
        }
    }

    public enum CHARACTER_CLASS {
        GOBLIN, HOBGOBLIN
    }
    public World world;
    // Basic counters
    private long lastDashTime, lastEnemySpawn;
    private byte enemyCounter;
    // libGDX objects
    private final OrthographicCamera camera;
    private Body deathZoneLeft, deathZoneRight, deathZoneTop, deathZoneBottom;
    private Texture pTex, kTex, wTex, earth;
    private BitmapFont text;
    private final Sound dashSound;
    // All vectors
    private final Vector3 touchPos;
    private final Vector2 target;
    private final Vector2 enemyTarget;
    private final Vector2 enemyDistance;

    public GameScreen(final DasherMain app) {
        super(app);
        // Camera, to see something
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        camera.position.set(0, 0, 0);
        camera.update();
        // Assets setting
        earth = new Texture("earthBack.png");
        kTex = new Texture("Knight.png");
        wTex = new Texture("Warrior.png");
        dashSound = Gdx.audio.newSound(Gdx.files.internal("dash.mp3"));
        // Movement vectors
        touchPos = new Vector3();
        target = new Vector2();
        enemyTarget = new Vector2();
        enemyDistance = new Vector2();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        lastDashTime = 0;
        enemyCounter = 0;
        gsm.app.score = 0;

        // Setting world and some modifications
        world = new World(new Vector2(0f, 0f), false);
        app.batch.setProjectionMatrix(camera.combined);

        // Enemies and player creation
        app.enemyList = new ArrayList<>();
        app.player = new Player(app.type, createBox
                (0.4f, 0.5f, 22, 28, COLLISIONS.PLAYER, BodyDef.BodyType.DynamicBody, 10f, false));
        pTex = app.type.equals(CHARACTER_CLASS.GOBLIN) ? new Texture("Goblin.png") : new Texture("Hobgoblin.png");

        // Map edges
        deathZoneTop = createBox
                (0.295f, 4.8f, 547, 16, COLLISIONS.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneBottom = createBox
                (0.295f, -5.4f, 547, 16, COLLISIONS.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneLeft = createBox
                (-8f, -0.43f, 16, 350, COLLISIONS.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);
        deathZoneRight = createBox
                (8.6f, -0.43f, 16, 350, COLLISIONS.DEATHZONE, BodyDef.BodyType.StaticBody, 0f, true);

        text = new BitmapFont();
        text.setColor(Color.valueOf("010101"));
        world.setContactListener(new CollListener());
    }

    /**
     * Game logic
     */
    @Override
    public void update(float delta) {
        stage.act(delta);
        world.step(1 / 120f, 12, 4);

        playerAlive();
        enemyMove();

        // Enemy spawner
        if (TimeUtils.nanoTime() - lastEnemySpawn > 800000000 && enemyCounter <= 14) {
            enemySpawner();
            enemyCounter++;
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
        app.batch.draw(earth, -earth.getWidth() / 2f, -earth.getHeight() / 2f);
        app.batch.draw(pTex, app.player.body.getPosition().x * PPM - (pTex.getWidth() / 2f),
                app.player.body.getPosition().y * PPM - (pTex.getHeight() / 2f));

        for (Enemy enemy : app.enemyList) {
            switch (enemy.type) {
                case KNIGHT:
                    app.batch.draw(kTex, enemy.body.getPosition().x * PPM - 26,
                            enemy.body.getPosition().y * PPM - 32);
                    break;
                case WARRIOR:
                    app.batch.draw(wTex, enemy.body.getPosition().x * PPM - 26,
                            enemy.body.getPosition().y * PPM - 32);
                    break;
            }
        }
        text.draw(app.batch, "Score : " + gsm.app.score, -640f, 360f);
        app.batch.end();
    }

    /**
     * Memory optimization
     */
    @Override
    public void dispose() {
        super.dispose();
        earth.dispose();
        pTex.dispose();
        kTex.dispose();
        wTex.dispose();
        dashSound.dispose();
        world.dispose();
    }

    /**
     * Is player alive check
     */
    private void playerAlive() {
        if (app.player.isAlive()) inputUpdate();
        else {
            app.enemyList.clear();
            world.destroyBody(app.player.body);
            gsm.setScreen(GameScreenManager.STATES.DEAD_STAGE);
        }
    }

    /**
     * Player movement
     */
    private void inputUpdate() {
        long DASH_DELAY = 800000000; // dash delay 0.8 sec
        app.player.isDash = !(TimeUtils.nanoTime() - lastDashTime >= 250000000); // 0.25 sec
        if (TimeUtils.nanoTime() - lastDashTime > DASH_DELAY) {
            if (Gdx.input.isTouched()) {
                lastDashTime = TimeUtils.nanoTime();
                app.player.isDash = true;
                dashSound.play(0.4f);
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                target.set(touchPos.x / PPM - app.player.body.getPosition().x, touchPos.y / PPM - app.player.body.getPosition().y);
                target.nor();
                target.set(target.x * app.player.moveSpeed, target.y * app.player.moveSpeed);
                app.player.body.setLinearVelocity(target);
                lastDashTime = TimeUtils.nanoTime();
            }
        }
    }

    /**
     * Universal body (box) creator with definitions and fixtures
     */
    private Body createBox(float x, float y, float width, float height,
                           COLLISIONS ctg, BodyDef.BodyType type, float damping, boolean isSensor) {
        Body body;
        BodyDef definitions = new BodyDef();
        definitions.type = type;
        definitions.position.set(x, y);
        definitions.fixedRotation = true;
        definitions.linearDamping = type == BodyDef.BodyType.DynamicBody ? damping : 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;

        body = world.createBody(definitions);
        body.createFixture(fixtureDef);
        body.setUserData(ctg);
        return body;
    }

    /**
     * Creates enemies around player
     */
    private void enemySpawner() {
        // Calculating distance
        int type = MathUtils.random(1, 2);
        enemyDistance.set(MathUtils.random(-7f, 8f), MathUtils.random(-4.5f, 3.5f));
        while (app.player.body.getPosition().dst(enemyDistance) < 3.5f) {
            enemyDistance.set(MathUtils.random(-7f, 8f), MathUtils.random(-4.5f, 3.5f));
        }
        // Creating enemy
        Enemy enemy = new Enemy(type == 2 ? COLLISIONS.WARRIOR : COLLISIONS.KNIGHT,
                createBox(enemyDistance.x, enemyDistance.y, 22, 28, type == 2 ? COLLISIONS.WARRIOR : COLLISIONS.KNIGHT,
                        BodyDef.BodyType.DynamicBody, 10f, true));

        app.enemyList.add(enemy);
        lastEnemySpawn = TimeUtils.nanoTime();
    }

    /**
     * Enemy movement and hit points check
     */
    private void enemyMove() {
        for (Iterator<Enemy> i = app.enemyList.iterator(); i.hasNext(); ) {
            Enemy enemy = i.next();
            enemyTarget.set(app.player.body.getPosition().sub(enemy.body.getPosition()));
            enemyTarget.nor();
            enemyTarget.set(enemyTarget.x * enemy.moveSpeed, enemyTarget.y * enemy.moveSpeed);
            enemy.body.setLinearVelocity(enemyTarget);
            if (!enemy.isAlive) {
                i.remove();
                world.destroyBody(enemy.body);
                enemyCounter--;
            }
        }
    }
}
