package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Restarter;
import com.test.game.Score.Score;
import com.test.game.Spawners.AsteroidSpawner;
import com.test.game.Spawners.BulletSpawner;

public class Ship {
	private final float SHIP_SIZE = 64f;
	private final float SHIP_HALF_SIZE = SHIP_SIZE / 2;

	private final float INVULNERABILITY_TIME = 3f;

	private float shipWidth = SHIP_SIZE;
	private float shipHeight = SHIP_SIZE;

	private float invulnerabilityTime = INVULNERABILITY_TIME;

	private float screenWidth = Gdx.graphics.getWidth();
	private float screenHeight = Gdx.graphics.getHeight();

	private final Vector2 position = new Vector2();
	private final Vector2 angle = new Vector2();

	private final float speed = 300f;

	private final Texture texture;
	private final TextureRegion textureRegion;
	private Circle collider;
	private AsteroidSpawner asteroidSpawner;
	private BulletSpawner bulletSpawner;

	private final String TEXTURE_NAME = "PNG/playerShip2_blue.png";

	private int health = 3;

	private Score score;

	public Ship(float x, float y, AsteroidSpawner asteroidSpawner, Score score, BulletSpawner bulletSpawner) {
		this(x, y, asteroidSpawner, "PNG/playerShip2_blue.png", score, bulletSpawner);
	}

	public Ship(float x, float y, AsteroidSpawner asteroidSpawner, String textureName, Score score, BulletSpawner bulletSpawner) {
		this.score = score;
		this.asteroidSpawner = asteroidSpawner;
		this.bulletSpawner = bulletSpawner;
		texture = new Texture(textureName);
		textureRegion = new TextureRegion(texture);
		Sprite sprite = new Sprite(texture);
		sprite.getColor().a = 0f;
		position.set(x - SHIP_HALF_SIZE, y - SHIP_HALF_SIZE);
		initVertices();
	}
	
	private void initVertices() {
		collider = new Circle(position.x + SHIP_HALF_SIZE, position.y + SHIP_HALF_SIZE, SHIP_HALF_SIZE);
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
		collider.setPosition(position.x + SHIP_HALF_SIZE, position.y + SHIP_HALF_SIZE);
		checkCollisionEnter();
		checkShoot();
	}

	private void checkShoot() {
		if (Gdx.input.justTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			shoot();
		}
	}

	private void shoot() {
		Bullet bullet = new Bullet(position.x + SHIP_HALF_SIZE - 5f, position.y + SHIP_HALF_SIZE - 15f, angle, asteroidSpawner, score, bulletSpawner);
		bulletSpawner.add(bullet);
	}

	public void checkCollisionEnter() {
		if (invulnerabilityTime > 0f) {
			invulnerabilityTime -= Gdx.graphics.getDeltaTime();
			return;
		}
		for (Asteroid asteroid : asteroidSpawner.getAll()) {  // per ogni asteroide chiamo expolison e verifico se contiene le cordinate del proiettile
			if (asteroid.getCollider().overlaps(collider)) {
				health--;
				asteroidSpawner.remove(asteroid);
				invulnerabilityTime = INVULNERABILITY_TIME;
				if (health < 1) {
					score.resetScore();
					Restarter.get().restart();
					health = 3;
				}
			}
		}
	}

	public void dispose() {
		texture.dispose();
	}

	public void moveTo(Vector2 direction) {
		overScreen();
		direction = new Vector2(direction.nor().x * speed * Gdx.graphics.getDeltaTime(), direction.nor().y * speed * Gdx.graphics.getDeltaTime());
		position.add(direction);
	}

	public void moveTo() {
		overScreen();
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

	public float getSize(){
		return SHIP_SIZE;
	}

	public Circle getCollider() {
		return collider;
	}

	private void overScreen(){
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		if (position.x > screenWidth - SHIP_SIZE) {
			position.x = 0;
		}
		if (position.x < 0) {
			position.x = screenWidth - SHIP_SIZE;
		}
		if (position.y > screenHeight - SHIP_SIZE) {
			position.y = 0;
		}
		if (position.y < 0) {
			position.y = screenHeight - SHIP_SIZE;
		}
	}

	public void resetPosition() {
		float screenWidthCenter = Gdx.graphics.getWidth() / 2f;
		float screenHeightCenter = Gdx.graphics.getHeight() / 2f;
		this.position.set(new Vector2(screenWidthCenter - SHIP_HALF_SIZE, screenHeightCenter - SHIP_HALF_SIZE));
		angle.set(new Vector2(0, 1));
	}

	public void updatePosition() {
		float newScreenWidth = Gdx.graphics.getWidth();
		float newScreenHeight = Gdx.graphics.getHeight();
		position.set(position.x * (newScreenWidth/screenWidth), position.y * (newScreenHeight/screenHeight));
		screenWidth = newScreenWidth;
		screenHeight = newScreenHeight;
	}
}