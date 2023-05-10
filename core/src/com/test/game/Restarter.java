package com.test.game;

import com.test.game.Space.Ship;

public class Restarter {

    private Ship ship;

    public Restarter(Ship ship) {
        this.ship = ship;
    }

    public void restart() {
        Game.getAsteroidSpawner().clear();
        ship.resetPosition();
    }
}
