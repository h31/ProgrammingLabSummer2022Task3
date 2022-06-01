package com.dasher.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class DasherMain extends ApplicationAdapter {
	public static final float PPM = 64;
	public static final short PLAYER = 1;
	public static final short ENEMY = 2;
	public static final short DEATHZONE = 4;
	public static final short BOX = 8;

	private SpriteBatch batch;
	private Texture earth;
	private Texture pTex;
	private Sound dashSound;
	private Music music;
	private long lastDashTime;

	private OrthographicCamera camera;

	public static World world;
	private Box2DDebugRenderer b2dr;
	private final Goblin player = new Goblin();
	private Body deathZoneLeft, deathZoneRight, deathZoneTop, deathZoneBottom;

	private final Vector3 touchPos = new Vector3();
	private final Vector2 target = new Vector2();


	/**
	 * инициализация объектов
	 */
	@Override
	public void create () {
		earth = new Texture("earthBack.png");
		pTex = new Texture("Goblin.png");
		dashSound = Gdx.audio.newSound(Gdx.files.internal("daashh.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();

		world = new World(new Vector2(0, 0), false);
		b2dr = new Box2DDebugRenderer();
		player.body = player.createBody();

		deathZoneTop = createBox(0.295f, 4.8f, 547, 16);
		deathZoneBottom = createBox(0.295f, -5.4f, 547, 16);
		deathZoneLeft = createBox(-8f, -0.43f, 16, 350);
		deathZoneRight = createBox(8.6f, -0.43f, 16, 350);
		lastDashTime = 0;
	}

	/**
	 * отрисовка и логика через update()
	 */
	@Override
	public void render () {
		update();
		ScreenUtils.clear(0, 0, 0, 1.0f);

		batch.begin();
		batch.draw(earth, -earth.getWidth() / 2, -earth.getHeight() / 2);
		batch.draw(pTex, player.body.getPosition().x * PPM - (pTex.getWidth() / 2), player.body.getPosition().y * PPM - (pTex.getHeight() / 2));
		batch.end();

		//b2dr.render(world, camera.combined.scl(PPM));
	}

	/**
	 *  оптимизация
	 */
	@Override
	public void dispose () {
		earth.dispose();
		batch.dispose();
		music.dispose();
		world.dispose();
		b2dr.dispose();
		dashSound.dispose();
	}

	private void update() {
		world.step(1 / 120f, 12, 4);
		inputUpdate();

		camera.position.x = 0;
		camera.position.y = 0;
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}

	/**
	 * реализация передвижения
	 */
	public void inputUpdate() {
		player.isDash = false;
		if(TimeUtils.nanoTime() - lastDashTime > 800000000) {
			player.isDash = true;
			if(Gdx.input.isTouched()) {
				dashSound.play(0.4f);
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				target.x = touchPos.x / PPM - player.body.getPosition().x;
				target.y = touchPos.y / PPM - player.body.getPosition().y;
				target.nor();
				target.set(target.x * player.moveSpeed, target.y * player.moveSpeed);
				player.body.setLinearVelocity(target);
				lastDashTime = TimeUtils.nanoTime();
			}
		}
	}

	/**
	 * Создание краёв карты
	 */
	protected Body createBox(float x, float y, int width, int height) {

		BodyDef definitions = new BodyDef();
		definitions.type = BodyDef.BodyType.StaticBody;
		definitions.position.set(x, y);
		definitions.fixedRotation = true;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / PPM, height / PPM);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = DEATHZONE;
		fixtureDef.filter.maskBits = ENEMY;

		return world.createBody(definitions).createFixture(fixtureDef).getBody();
	}

}