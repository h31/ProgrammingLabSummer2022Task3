package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.dasher.game.DasherMain;
import com.dasher.game.Player;

import static com.dasher.game.DasherMain.PPM;

public class GameScreen extends AbstractScreen {
    public static CHARACTER_CLASS type;

    public enum COLLISIONS {
        PLAYER((byte) 1), ENEMY((byte) 2), DEATHZONE((byte) 4), BOX((byte) 8);
        public byte mask;

        COLLISIONS(byte s) {
            this.mask = s;
        }
    }

    public enum CHARACTER_CLASS {
        GOBLIN, HOBGOBLIN
    }

    OrthographicCamera camera;

    World world;
    Box2DDebugRenderer b2rd;
    private Body deathZoneLeft, deathZoneRight, deathZoneTop, deathZoneBottom, box;
    private Player player;
    private Texture pTex;
    private Texture earth;
    private Sound dashSound;
    private long lastDashTime;

    private final Vector3 touchPos = new Vector3();
    private final Vector2 target = new Vector2();

    public GameScreen(DasherMain app) {
        super(app);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.camera.position.x = 0;
        this.camera.position.y = 0;
        this.camera.update();

        world = new World(new Vector2(0f, 0f), false);
        b2rd = new Box2DDebugRenderer();
        earth = new Texture("earthBack.png");
        dashSound = Gdx.audio.newSound(Gdx.files.internal("dash.mp3"));
        lastDashTime = 0;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        app.batch.setProjectionMatrix(camera.combined);
        pTex = type.equals(CHARACTER_CLASS.GOBLIN) ? new Texture("Goblin.png") :
                new Texture("Hobgoblin.png");
        player = new Player(type, createBox(0.4f, 0.5f, 26, 32, COLLISIONS.PLAYER, COLLISIONS.BOX, BodyDef.BodyType.DynamicBody, 10f));
        deathZoneTop = createBox(0.295f, 4.8f, 547, 16, COLLISIONS.DEATHZONE, COLLISIONS.ENEMY, BodyDef.BodyType.StaticBody, 0f);
        deathZoneBottom = createBox(0.295f, -5.4f, 547, 16, COLLISIONS.DEATHZONE, COLLISIONS.ENEMY, BodyDef.BodyType.StaticBody, 0f);
        deathZoneLeft = createBox(-8f, -0.43f, 16, 350, COLLISIONS.DEATHZONE, COLLISIONS.ENEMY, BodyDef.BodyType.StaticBody, 0f);
        deathZoneRight = createBox(8.6f, -0.43f, 16, 350, COLLISIONS.DEATHZONE, COLLISIONS.ENEMY, BodyDef.BodyType.StaticBody, 0f);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        world.step(1 / 120f, 12, 4);
        inputUpdate();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.valueOf("709f6e"));
        stage.draw();
        app.batch.begin();
        app.batch.draw(earth, -earth.getWidth() / 2, -earth.getHeight() / 2);
        app.batch.draw(pTex, player.body.getPosition().x * PPM - (pTex.getWidth() / 2), player.body.getPosition().y * PPM - (pTex.getHeight() / 2));
        app.batch.end();
        b2rd.render(world, camera.combined.cpy().scl(PPM));
    }

    @Override
    public void dispose() {
        super.dispose();
        earth.dispose();
        pTex.dispose();
        dashSound.dispose();
        world.dispose();
        b2rd.dispose();
    }
    private void inputUpdate() {
        player.isDash = false;
        long DASH_DELAY = 800000000; // откат рывка
        if (TimeUtils.nanoTime() - lastDashTime > DASH_DELAY) {
            player.isDash = true;
            if (Gdx.input.isTouched()) {
                dashSound.play(0.4f);
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                target.set(touchPos.x / PPM - player.body.getPosition().x, touchPos.y / PPM - player.body.getPosition().y);
                target.nor();
                target.set(target.x * player.getMoveSpeed(), target.y * player.getMoveSpeed());
                player.body.setLinearVelocity(target);
                lastDashTime = TimeUtils.nanoTime();
            }
        }
    }

    private Body createBox(float x, float y, float width, float height,
                           COLLISIONS ctg, COLLISIONS msk,
                           BodyDef.BodyType type, float damping) {
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
        fixtureDef.filter.categoryBits = ctg.mask;
        fixtureDef.filter.maskBits = msk.mask;
        return world.createBody(definitions).createFixture(fixtureDef).getBody();
    }
}
