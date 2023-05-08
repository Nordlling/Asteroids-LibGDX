package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {

    private float size;
    private final float MIN_SIZE = 32f;
    private final float MAX_SIZE = 48f;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();

    private float speed;
    private final float MIN_SPEED = 100f;
    private final float MAX_SPEED = 400f;

    private float rotationSpeed;
    private final float MIN_SPEED_ROTATION = 100f;
    private final float MAX_SPEED_ROTATION = 400f;

    private final Texture texture;
    private final TextureRegion textureRegion;

    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();
    private Circle collider;

    private Vector2 direction;


    public Asteroid(float x, float y) {
        this(x, y, "PNG/playerShip2_blue.png");
    }

    public Asteroid(float x, float y, Texture texture) {
        this.texture = texture;
        textureRegion = new TextureRegion(texture);
        int firstSize = Math.min(texture.getWidth(), texture.getHeight());
        int secondSize = Math.max(texture.getWidth(), texture.getHeight());
        size = MathUtils.random(firstSize, secondSize);
        position.set(x - size/2, y - size/2);
        init();
    }

    public Asteroid(float x, float y, String textureName) {
        this(x, y, new Texture(textureName));
    }

    private void init() {
        speed = MathUtils.random(MIN_SPEED, MAX_SPEED);
        int sign = MathUtils.randomBoolean() ? 1 : -1;
        rotationSpeed = sign * MathUtils.random(MIN_SPEED_ROTATION, MAX_SPEED_ROTATION);
        direction = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f));
        angle.set(direction);
        collider = new Circle(position.x + size/2, position.y + size/2, size/2);
    }

    public void render(Batch batch) {
        collider.setPosition(position.x + size/2, position.y + size/2);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                size/2,
                size/2,
                size,
                size,
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
        overScreen();
//        Vector2 direction = angle.nor();
        position.add(new Vector2(direction.x * speed * Gdx.graphics.getDeltaTime(), direction.y * speed * Gdx.graphics.getDeltaTime()));
    }

    public void rotateTo() {
        angle.set(angle.rotateDeg(rotationSpeed * Gdx.graphics.getDeltaTime()));
    }

    private void overScreen(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        if (position.x > screenWidth) {
            position.x = 0;
        }
        if (position.x < 0) {
            position.x = screenWidth;
        }
        if (position.y > screenHeight) {
            position.y = 0;
        }
        if (position.y < 0) {
            position.y = screenHeight;
        }
    }

    public Circle getCollider() {
        return collider;
    }

    public void updatePosition() {
        float newScreenWidth = Gdx.graphics.getWidth();
        float newScreenHeight = Gdx.graphics.getHeight();
        position.set(position.x * (newScreenWidth/screenWidth), position.y * (newScreenHeight/screenHeight));
        screenWidth = newScreenWidth;
        screenHeight = newScreenHeight;
    }

}
