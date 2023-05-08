package com.test.game.Score;

import com.badlogic.gdx.utils.Array;

public class Score {
    private int score;
    private Array<ScoreChangeListener> listeners;

    public Score() {
        listeners = new Array<>();
    }

    public void increaseScore() {
        score++;
        notifyListeners();
    }

    public void resetScore() {
        score = 0;
        notifyListeners();
    }


    private void notifyListeners() {
        ScoreChangeEvent event = new ScoreChangeEvent(score);
        for (ScoreChangeListener listener : listeners) {
            listener.onScoreChange(event);
        }
    }

    public void addListener(ScoreChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ScoreChangeListener listener) {
        listeners.removeValue(listener, true);
    }
}