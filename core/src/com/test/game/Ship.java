package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ship {
	private final float SHIP_SIZE = 64f;
	private final float SHIP_HALF_SIZE = SHIP_SIZE / 2;

	private final Vector2 position = new Vector2();
	private final Vector2 angle = new Vector2();

	private final float speed = 300f;

	private final Texture texture;
	private final TextureRegion textureRegion;

	public Ship(float x, float y) {
		this(x, y, "PNG/playerShip2_blue.png");
	}

	public Ship(float x, float y, String textureName) {
		texture = new Texture(textureName);
		textureRegion = new TextureRegion(texture);
		position.set(x - SHIP_HALF_SIZE, y - SHIP_HALF_SIZE);
	}

	public void render(Batch batch) {
		batch.draw(
				textureRegion,
				position.x,
				position.y,
				SHIP_HALF_SIZE,
				SHIP_HALF_SIZE,
				SHIP_SIZE,
				SHIP_SIZE,
				1,
				1,
				angle.angleDeg() - 90
		);
	}

	public void dispose() {
		texture.dispose();
	}

	public void moveTo(Vector2 direction) {
		direction = new Vector2(direction.nor().x * speed * Gdx.graphics.getDeltaTime(), direction.nor().y * speed * Gdx.graphics.getDeltaTime());
		position.add(direction);
	}

	public void moveTo() {
		Vector2 direction = angle.nor();
		position.add(new Vector2(direction.x * speed * Gdx.graphics.getDeltaTime(), direction.y * speed * Gdx.graphics.getDeltaTime()));
	}

	public void rotateTo(Vector2 mousePos) {
		angle.set(mousePos).sub(position.x + SHIP_HALF_SIZE, position.y + SHIP_HALF_SIZE);
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getAngle(){
		return angle;
	}
}