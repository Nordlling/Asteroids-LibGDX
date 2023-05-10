package com.test.game.Spawners;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Renderable;
import com.test.game.Space.Bullet;

public class BulletSpawner implements Spawner<Bullet>, Renderable {

    private Batch batch;

    private Pool<Bullet> bulletPool;
    private Array<Bullet> activeBullets;

    public BulletSpawner(Batch batch) {
        this.batch = batch;

        bulletPool = new Pool<Bullet>(16) {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };

        activeBullets = new Array<>();
    }

    public void spawnBullet(float x, float y, Vector2 direction) {
        Bullet bullet = bulletPool.obtain();
        bullet.init(x, y, direction);
        activeBullets.add(bullet);
    }

    @Override
    public void clear() {
        activeBullets.clear();
    }

    @Override
    public void render() {
        for (Bullet bullet : activeBullets) {
            bullet.render(batch);
        }
    }

    @Override
    public Array<Bullet> getAll() {
        return activeBullets;
    }

    @Override
    public void remove(Bullet bullet) {
        activeBullets.removeValue(bullet, true);
        bulletPool.free(bullet);
    }

    @Override
    public void add(Bullet bullet) {
    }

    @Override
    public void dispose() {
        for (Bullet bullet : activeBullets) {
            bullet.dispose();
        }
        activeBullets.clear();
    }
}
