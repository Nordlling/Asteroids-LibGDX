package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Config.AsteroidConfig;
import com.test.game.Game;

public class Asteroid extends SpaceObject {

    private static final AsteroidConfig asteroidConfig = Game.getConfigManager().getConfig(AsteroidConfig.class);

    private final float rotationSpeed;
    private final Circle collider;
    private final Vector2 direction;

    public Asteroid(float x, float y, Texture texture) {
        super(texture);
        float size = MathUtils.random(Math.min(texture.getWidth(), texture.getWidth()), Math.max(texture.getWidth(), texture.getWidth()));
        width = size;
        height = size;
        position.set(x - width / 2, y - height / 2);
        direction = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f));
        angle.set(direction);
        speed = MathUtils.random(asteroidConfig.minSpeed, asteroidConfig.maxSpeed);
        int sign = MathUtils.randomBoolean() ? 1 : -1;
        rotationSpeed = sign * MathUtils.random(asteroidConfig.minSpeedRotation, asteroidConfig.maxSpeedRotation);
        collider = new Circle(position.x + width/2, position.y + height/2, Math.min(width/2, height/2));
    }

    public void render(Batch batch) {
        moveTo();
        rotateTo();
        collider.setPosition(getPosition());
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
        position.add(new Vector2(direction).scl(speed * Gdx.graphics.getDeltaTime()));
    }

    private void overScreen(){
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

    public void rotateTo() {
        angle.set(angle.rotateDeg(rotationSpeed * Gdx.graphics.getDeltaTime()));
    }

    @Override
    public Circle getCollider() {
        return collider;
    }

}
