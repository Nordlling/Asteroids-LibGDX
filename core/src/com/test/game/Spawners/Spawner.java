package com.test.game.Spawners;

import com.badlogic.gdx.utils.Array;

public interface Spawner<T> {

    void clear();

    Array<T> getAll();

    void remove(T object);

    void add(T object);

    void dispose();

}
