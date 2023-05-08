package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background implements Screen {
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private final String TEXTURE_NAME = "Backgrounds/darkPurple.png";

    public Background(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        backgroundTexture = new Texture(TEXTURE_NAME);
    }

    @Override
    public void show() {
        System.err.println("SHOW");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int y = 0; y < Gdx.graphics.getHeight(); y += backgroundTexture.getHeight()) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x += backgroundTexture.getWidth()) {
                spriteBatch.draw(backgroundTexture, x, y);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        System.err.println("RESIZE");
    }

    @Override
    public void pause() {
        System.err.println("PAUSE");
    }

    @Override
    public void resume() {
        System.err.println("RESUME");
    }

    @Override
    public void hide() {
        System.err.println("HIDE");
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
