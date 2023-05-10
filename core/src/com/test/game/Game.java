package com.test.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Config.ConfigManager;
import com.test.game.Config.ScreenConfig;
import com.test.game.Score.Score;
import com.test.game.Score.ScoreUI;
import com.test.game.Space.Ship;
import com.test.game.Spawners.AsteroidSpawner;
import com.test.game.Spawners.BulletSpawner;

import java.io.IOException;

public class Game extends ApplicationAdapter {

	private static ConfigManager configManager;
	private static AsteroidSpawner asteroidSpawner;
	private static BulletSpawner bulletSpawner;
	private static Restarter restarter;
	private static Score score;

	private float width;
	private float height;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Ship ship;
	private Background background;
	private ScoreUI scoreUI;

	@Override
	public void create () {
		loadConfigs();
		width = getConfigManager().getConfig(ScreenConfig.class).width;
		height = getConfigManager().getConfig(ScreenConfig.class).height;
		Gdx.graphics.setWindowedMode((int) width, (int) height);
		Gdx.graphics.setResizable(false);

		score = new Score();
		scoreUI = new ScoreUI();
		score.addListener(scoreUI);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		batch = new SpriteBatch();
		background = new Background(batch);
		try {
			asteroidSpawner = new AsteroidSpawner(batch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		bulletSpawner = new BulletSpawner(batch);
		ship = new Ship(width / 2, height / 2);
		restarter = new Restarter(ship);
	}

	@Override
	public void render () {
		batch.begin();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		background.render(1);
		asteroidSpawner.render();
		bulletSpawner.render();
		ship.render();
		scoreUI.render();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		ship.dispose();
		asteroidSpawner.dispose();
		bulletSpawner.dispose();
		background.dispose();
	}

	private static void loadConfigs() {
		configManager = new ConfigManager();
		configManager.init();
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static AsteroidSpawner getAsteroidSpawner() {
		return asteroidSpawner;
	}

	public static BulletSpawner getBulletSpawner() {
		return bulletSpawner;
	}

	public static Restarter getRestarter() {
		return restarter;
	}

	public static Score getScore() {
		return score;
	}
}