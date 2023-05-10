package com.test.game.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public abstract class SpaceObject {

    protected float width;
    protected float height;
    protected float screenWidth = Gdx.graphics.getWidth();
    protected float screenHeight = Gdx.graphics.getHeight();
    protected float speed;
    protected final Vector2 position = new Vector2();
    protected final Vector2 angle = new Vector2();
    protected final Texture texture;
    protected final TextureRegion textureRegion;

    public SpaceObject(String textureName) {
        this.texture = new Texture(textureName);
        this.textureRegion = new TextureRegion(texture);
    }

    protected SpaceObject(Texture texture) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
    }

    public abstract Shape2D getCollider();

    public Vector2 getPosition() {
        return new Vector2(position).add(width / 2, height / 2);
    }

    public Vector2 getAngle() {
        return angle;
    }

    public void resetPosition() {
        float screenWidthCenter = Gdx.graphics.getWidth() / 2f;
        float screenHeightCenter = Gdx.graphics.getHeight() / 2f;
        position.set(new Vector2(screenWidthCenter - width /2, screenHeightCenter - height /2));
    }

    public void dispose() {
        texture.dispose();
    }

}
