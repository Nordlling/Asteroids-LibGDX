package com.test.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Score.Score;
import com.test.game.Score.ScoreUI;
import com.test.game.Space.Asteroid;
import com.test.game.Space.Ship;
import com.test.game.Spawners.AsteroidSpawner;
import com.test.game.Spawners.BulletSpawner;

public class Game extends ApplicationAdapter {

	private float SCREEN_WIDTH = 1200f;
	private float SCREEN_HEIGHT = 720f;
	private float DEATH_ZONE = 10f;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Ship ship;
	private AsteroidSpawner asteroidSpawner;
	private BulletSpawner bulletSpawner;
	private Restarter restarter;
	private Background background;
	private ScoreUI scoreUI;
	private Score score;


	@Override
	public void create () {
		Gdx.graphics.setWindowedMode((int) SCREEN_WIDTH, (int) SCREEN_HEIGHT);
		Gdx.graphics.setResizable(false);

		score = new Score();
		scoreUI = new ScoreUI();
		score.addListener(scoreUI);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		background = new Background(batch);
		asteroidSpawner = new AsteroidSpawner(batch);
		bulletSpawner = new BulletSpawner(batch);
		ship = new Ship(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, asteroidSpawner, score, bulletSpawner);
		restarter = new Restarter(batch, asteroidSpawner, ship);
	}

	@Override
	public void render () {
		batch.begin();
		Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		ship.rotateTo(mousePosition);

		Vector2 shipCenterPosition = new Vector2(ship.getPosition().x + ship.getSize()/2, ship.getPosition().y + ship.getSize()/2);
		if (mousePosition.dst(shipCenterPosition) > DEATH_ZONE) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				ship.getAngle().set(ship.getAngle().rotate90(1));
				ship.moveTo();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				ship.getAngle().set(ship.getAngle().rotate90(-1));
				ship.moveTo();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				ship.moveTo();
			}
		}

		background.render(1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		asteroidSpawner.render();
		bulletSpawner.render();
		ship.render(batch);
		scoreUI.render();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ship.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(camera.combined);
		ship.updatePosition();
		for (Asteroid asteroid : asteroidSpawner.getAll()) {
			asteroid.updatePosition();
		}
	}
}