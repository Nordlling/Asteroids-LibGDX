package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Score.Score;
import com.test.game.Space.Asteroid;
import com.test.game.Spawners.AsteroidSpawner;
import com.test.game.Spawners.BulletSpawner;

public class Bullet {

    private float rad;
    private final float SPEED = 500f;
    private Texture texture;
    private TextureRegion textureRegion;

    private final String TEXTURE_NAME = "PNG/Lasers/laserBlue02.png";
    private AsteroidSpawner asteroidSpawner;
    private Polygon polygonCollider;
    private float[] vertices;

    private final float WIDTH = 10f;
    private final float HEIGHT = 30f;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();
    private Vector2 direction;

    private Score score;
    private BulletSpawner bulletSpawner;


    public Bullet(float x, float y, Vector2 direction, AsteroidSpawner asteroidSpawner, Score score, BulletSpawner bulletSpawner) {
        this.score = score;
        this.asteroidSpawner = asteroidSpawner;
        this.bulletSpawner = bulletSpawner;
        texture = new Texture(TEXTURE_NAME);
        textureRegion = new TextureRegion(texture);
        position.x = x;
        position.y = y;
        angle.set(direction);
        this.direction = new Vector2(direction.x, direction.y);
        init();
    }

    private void init() {
        vertices = new float[]{
                0f, 0f,
                0f, HEIGHT,
                WIDTH, HEIGHT,
                WIDTH, 0f
        };
        polygonCollider = new Polygon(vertices);
        polygonCollider.setOrigin(WIDTH/2, HEIGHT/2);
        vertices = polygonCollider.getTransformedVertices();
        polygonCollider.rotate(90);
        polygonCollider.rotate(direction.angleDeg());
    }

    public void moveTo(Vector2 direction) {
        if (!Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT)) {
        if (!overScreen()) {
            direction = direction.nor();
            direction = new Vector2(direction.x * SPEED * Gdx.graphics.getDeltaTime(), direction.y * SPEED * Gdx.graphics.getDeltaTime());
            position.add(direction);
        }
        }
    }

    public boolean overScreen() {
        if (position.x > Gdx.graphics.getWidth() || position.x < 0 || position.y > Gdx.graphics.getHeight() || position.y < 0) {
            bulletSpawner.remove(this);
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void delete() {
//        super.delete();
//        Game.get().getShip().getBullets().remove(this);
//    }

    public void checkDestroy() {
        if (!overScreen()) {
            vertices = polygonCollider.getTransformedVertices();
            for (Asteroid asteroid : asteroidSpawner.getAll()) {
                if (asteroid.getCollider().contains(vertices[0], vertices[1])) {
                    score.increaseScore();
                    asteroidSpawner.remove(asteroid);
                    bulletSpawner.remove(this);
                }
            }
        }
    }

//    @Override
//    public void loop() {
//        move();
//        if (overscreen()) {
//            delete();
//        }
//        checkDestroy();
//    }

    public void render(Batch batch) {
        moveTo(direction);
        polygonCollider.setPosition(position.x, position.y);
        checkDestroy();
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                WIDTH/2,
                HEIGHT/2,
                WIDTH,
                HEIGHT,
                1,
                1,
                angle.angleDeg() - 90
        );
    }

}
