package com.test.game.Score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreUI implements ScoreChangeListener {

    private int score = 0;
    private int offset = 10;
    private BitmapFont font;
    private SpriteBatch spriteBatch;

    public ScoreUI() {
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
    }

    public void render() {
        spriteBatch.begin();
        font.draw(spriteBatch, "Score: " + score, 10, Gdx.graphics.getHeight() - offset);
        spriteBatch.end();
    }

    @Override
    public void onScoreChange(ScoreChangeEvent event) {
        score = event.newScore;
    }
}
