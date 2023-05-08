package com.test.game.Spawners;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.test.game.Space.Bullet;
import com.test.game.Renderable;

public class BulletSpawner implements Spawner<Bullet>, Renderable {

    private static BulletSpawner instance;

    private Array<Bullet> bullets = new Array<>();

    private Batch batch;

    public BulletSpawner(Batch batch) {
        if (instance != null) {
            System.err.println("No duplicate BulletSpawner");
        }
        this.batch = batch;
        instance = this;
    }

    @Override
    public void clear() {
        bullets.clear();
    }

    @Override
    public void render() {
        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    @Override
    public Array<Bullet> getAll() {
        return bullets;
    }

    @Override
    public void remove(Bullet bullet) {
        bullets.removeValue(bullet, false);
    }

    @Override
    public void add(Bullet bullet) {
        bullets.add(bullet);
    }
}
