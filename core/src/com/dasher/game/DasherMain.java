package com.dasher.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.dasher.game.managers.GameScreenManager;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DasherMain extends Game {
    public static final Logger logger = Logger.getLogger(DasherMain.class.getName());
    public static final float PPM = 64; // Pixel Per Meter need to convert big bodies to small
    public enum Collisions {
        PLAYER, KNIGHT((byte) 1), WARRIOR((byte) 2), DEATHZONE;
        public byte dmg;
        Collisions() {
        }
        Collisions(byte dmg) {
            this.dmg = dmg;
        }
    }
    public enum CharacterClass {
        GOBLIN, HOBGOBLIN
    }

    public GameScreenManager gsm;
    public SpriteBatch batch; // Main texture rendering tool
    public Player player;
    public CharacterClass type;
    public List<Enemy> enemyList;
    public Texture playerTex;
    public Texture knightTex;
    public Texture warriorTex;
    public Preferences prefs;
    public OrthographicCamera camera;
    public BitmapFont text;
    private Sound dashSound;
    public int score;
    public int highScore;
    public long lastDashTime, lastEnemySpawn;
    public byte enemyCounter;

    // All vectors
    private Vector3 touchPos;
    private Vector2 enemyDistance, enemyTarget;
    public final Vector2 target = new Vector2();


    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("GamePreferences");
        batch = new SpriteBatch();
        gsm = new GameScreenManager(this);

        // Camera, to see something
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        camera.position.set(0, 0, 0);
        camera.update();

        text = new BitmapFont();
        knightTex = new Texture("Knight.png");
        warriorTex = new Texture("Warrior.png");
        dashSound = Gdx.audio.newSound(Gdx.files.internal("dash.mp3"));
        // Movement vectors
        touchPos = new Vector3();
        enemyTarget = new Vector2();
        enemyDistance = new Vector2();

    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gsm.dispose();
        text.dispose();
        playerTex.dispose();
        knightTex.dispose();
        warriorTex.dispose();
        dashSound.dispose();
    }

    /**
     * Is player alive check
     */
    public void playerAlive(World world) {
        if (player.isAlive()) inputUpdate();
        else {
            logger.log(Level.INFO, "Player is dead");
            if (score > highScore) {
                prefs.putInteger("highScore", score);
                prefs.flush();
            }
            enemyList.clear();
            world.destroyBody(player.body);
            gsm.setScreen(GameScreenManager.States.DEAD_STAGE);
        }
    }

    /**
     * Player movement
     */
    public void inputUpdate() {
        player.isDash = !(TimeUtils.nanoTime() - lastDashTime >= player.dashLength);
        if (TimeUtils.nanoTime() - lastDashTime > player.dashDelay) {
            if (Gdx.input.isTouched()) {
                lastDashTime = TimeUtils.nanoTime();
                player.isDash = true;
                dashSound.play(0.2f);
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                target.set
                        (touchPos.x / PPM - player.body.getPosition().x, touchPos.y / PPM - player.body.getPosition().y).nor();
                target.set(target.x * player.moveSpeed, target.y * player.moveSpeed);
                player.body.setLinearVelocity(target);
                lastDashTime = TimeUtils.nanoTime();
            }
        }
    }

    /**
     * Universal body (box) creator with definitions and fixtures
     */
    public Body createBox(World world , float x, float y, float width, float height,
                          Collisions ctg, BodyDef.BodyType type, float damping, boolean isSensor) {
        Body body;
        BodyDef definitions = new BodyDef();
        definitions.type = type;
        definitions.position.set(x, y);
        definitions.fixedRotation = true;
        definitions.linearDamping = type == BodyDef.BodyType.DynamicBody ? damping : 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.groupIndex = 1;
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
    public void enemySpawner(World world) {
        // Calculating distance
        double type = MathUtils.random();
        do {
            enemyDistance.set(MathUtils.random(-7f, 8f), MathUtils.random(-4.5f, 3.5f));
        } while (player.body.getPosition().dst(enemyDistance) < 3.5f);

        // Creating enemy
        Enemy enemy = new Enemy(type < 0.25 ? Collisions.WARRIOR : Collisions.KNIGHT,
                createBox(world ,enemyDistance.x, enemyDistance.y, 22, 28, type < 0.25 ? Collisions.WARRIOR : Collisions.KNIGHT,
                        BodyDef.BodyType.DynamicBody, 10f, false));

        enemyList.add(enemy);
        lastEnemySpawn = TimeUtils.nanoTime();
    }

    /**
     * Enemy movement and hit points check
     */
    public void enemyMove(World world) {
        for (Iterator<Enemy> i = enemyList.iterator(); i.hasNext(); ) {
            Enemy enemy = i.next();
            enemyTarget.set(player.body.getPosition().sub(enemy.body.getPosition()));
            enemyTarget.nor();
            enemyTarget.set(enemyTarget.x * enemy.moveSpeed, enemyTarget.y * enemy.moveSpeed);
            enemy.body.setLinearVelocity(enemyTarget);
            if (!enemy.isAlive()) {
                i.remove();
                world.destroyBody(enemy.body);
                enemyCounter--;
            }
        }
    }
}