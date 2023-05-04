package com.test.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private float SCREEN_WIDTH = 800f;
	private float SCREEN_HEIGHT = 480f;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Ship ship;

	@Override
	public void create () {
		ship = new Ship(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Vector2 mousePosition = new Vector2(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY());
		ship.rotateTo(mousePosition);

		ScreenUtils.clear(0.3f, 0.3f, 0.5f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		ship.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ship.dispose();
	}
}
