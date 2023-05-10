package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Config.BulletConfig;
import com.test.game.Game;

public class Bullet extends SpaceObject {

    private static final BulletConfig bulletConfig = Game.getConfigManager().getConfig(BulletConfig.class);

    private Polygon collider;
    private float[] vertices;
    private Vector2 direction;

    public Bullet() {
        super(bulletConfig.textureName);
    }

    public void init(float x, float y, Vector2 direction) {
        position.x = x;
        position.y = y;
        width = bulletConfig.width;
        height = bulletConfig.height;
        speed = bulletConfig.speed;
        angle.set(direction);
        this.direction = new Vector2(direction.x, direction.y);
        initCollider();
    }

    private void initCollider() {
        vertices = new float[]{
                0f, 0f,
                0f, height,
                width, height,
                width, 0f
        };
        collider = new Polygon(vertices);
        collider.setOrigin(width/2, height/2);
        vertices = collider.getTransformedVertices();
        collider.rotate(90);
        collider.rotate(direction.angleDeg());
    }

    public void render(Batch batch) {
        collider.setPosition(position.x, position.y);
        moveTo();
        checkDestroy();
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                width/2,
                height/2,
                width,
                height,
                1,
                1,
                angle.angleDeg() - 90
        );
    }

    public void moveTo() {
        overScreen();
        position.add(angle.nor().scl(speed * Gdx.graphics.getDeltaTime()));
    }

    public void checkDestroy() {
        vertices = collider.getTransformedVertices();
        for (Asteroid asteroid : Game.getAsteroidSpawner().getAll()) {
            if (asteroid.getCollider().contains(vertices[0], vertices[1])) {
                Game.getScore().increaseScore();
                Game.getAsteroidSpawner().remove(asteroid);
                Game.getBulletSpawner().remove(this);
            }
        }
    }

    public void overScreen() {
        if (position.x > Gdx.graphics.getWidth() || position.x < 0 || position.y > Gdx.graphics.getHeight() || position.y < 0) {
            Game.getBulletSpawner().remove(this);
        }
    }

    @Override
    public Shape2D getCollider() {
        return collider;
    }
}
