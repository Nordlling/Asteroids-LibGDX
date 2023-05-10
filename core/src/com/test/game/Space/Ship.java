package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Config.ShipConfig;
import com.test.game.Game;
import com.test.game.Renderable;

public class Ship extends SpaceObject implements Renderable {

	private static final ShipConfig shipConfig = Game.getConfigManager().getConfig(ShipConfig.class);

	private float currentInvulnerabilityTime = shipConfig.invulnerabilityTime;
	private float currenHealth = shipConfig.health;
	private final Circle collider;
	private final SpriteBatch spriteBatch;
	private final Color invulnerabilityColor;
	private final Color defaultColor;

	public Ship(float x, float y) {
		super(shipConfig.textureName);
		width = shipConfig.width;
		height = shipConfig.height;
		spriteBatch = new SpriteBatch();
		invulnerabilityColor = new Color(1f, 0f, 0f, 1f);
		defaultColor = new Color(1f, 1f, 1f, 1f);
		position.set(x - width / 2, y - height / 2);
		angle.set(new Vector2(0, 1));
		collider = new Circle(getPosition().x, getPosition().y, Math.min(width / 2, height / 2));
	}

	public void render() {
		collider.setPosition(getPosition());
		checkInput();
		checkCollisionEnter();
		draw();
	}

	private void draw(){
		int halfSeconds = (int) (currentInvulnerabilityTime * 2);
		spriteBatch.begin();
		if (halfSeconds > 0 && halfSeconds % 2 != 0) {
			spriteBatch.setColor(invulnerabilityColor);
		} else {
			spriteBatch.setColor(defaultColor);
		}
		spriteBatch.draw(textureRegion, position.x, position.y, width / 2, height / 2, width, height, 1, 1, angle.angleDeg() - 90);
		spriteBatch.end();
	}

	private void checkInput() {
		Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		if (mousePosition.dst(getPosition()) > shipConfig.noMoveDistance) {
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				angle.rotate90(1);
				moveTo();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				angle.rotate90(-1);
				moveTo();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				moveTo();
			}
			rotateTo(mousePosition);
		}
		if (Gdx.input.justTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			shoot();
		}
	}

	private void moveTo() {
		overScreen();
		position.add(angle.nor().scl(shipConfig.speed * Gdx.graphics.getDeltaTime()));
	}

	private void overScreen() {
		if (position.x > screenWidth - width) {
			position.x = 0;
		}
		if (position.x < 0) {
			position.x = screenWidth - width;
		}
		if (position.y > screenHeight - height) {
			position.y = 0;
		}
		if (position.y < 0) {
			position.y = screenHeight - height;
		}
	}

	private void rotateTo(Vector2 target) {
		angle.set(target).sub(getPosition());
	}

	private void shoot() {
		Game.getBulletSpawner().spawnBullet(getPosition().x - 5f, getPosition().y - 15f, angle);
	}

	private void checkCollisionEnter() {
		if (currentInvulnerabilityTime > 0f) {
			currentInvulnerabilityTime -= Gdx.graphics.getDeltaTime();
			return;
		}
		for (Asteroid asteroid : Game.getAsteroidSpawner().getAll()) {
			if (asteroid.getCollider().overlaps(collider)) {
				currenHealth--;
				Game.getAsteroidSpawner().remove(asteroid);
				currentInvulnerabilityTime = shipConfig.invulnerabilityTime;
				if (currenHealth < 1) {
					Game.getScore().resetScore();
					currenHealth = shipConfig.health;
					Game.getRestarter().restart();
				}
			}
		}
	}

	@Override
	public Shape2D getCollider() {
		return collider;
	}
}