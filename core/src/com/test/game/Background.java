package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Config.ScreenConfig;

public class Background implements Screen {

    private static final ScreenConfig screenConfig = Game.getConfigManager().getConfig(ScreenConfig.class);

    private SpriteBatch batch;
    private Texture backgroundTexture;

    public Background(SpriteBatch batch) {
        this.batch = batch;
        backgroundTexture = new Texture(screenConfig.textureName);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int y = 0; y < Gdx.graphics.getHeight(); y += backgroundTexture.getHeight()) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x += backgroundTexture.getWidth()) {
                batch.draw(backgroundTexture, x, y);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
