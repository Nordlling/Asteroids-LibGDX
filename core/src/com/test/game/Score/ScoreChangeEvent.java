package com.test.game.Score;

import com.badlogic.gdx.scenes.scene2d.Event;

public class ScoreChangeEvent extends Event {

    public int newScore;

    public ScoreChangeEvent(int newScore) {
        this.newScore = newScore;
    }
}
