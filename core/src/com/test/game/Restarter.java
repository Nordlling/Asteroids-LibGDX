package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.test.game.Space.Ship;
import com.test.game.Spawners.AsteroidSpawner;

public class Restarter {

    private static Restarter restarter;

    private AsteroidSpawner asteroidSpawner;
    private Ship ship;
    private Batch batch;

    public Restarter(Batch batch, AsteroidSpawner asteroidSpawner, Ship ship) {
        restarter = this;
        this.batch = batch;
        this.asteroidSpawner = asteroidSpawner;
        this.ship = ship;
    }

    public void restart() {
        asteroidSpawner.clear();
        ship.resetPosition();
    }

    public static Restarter get() {
        return restarter;
    }
}
