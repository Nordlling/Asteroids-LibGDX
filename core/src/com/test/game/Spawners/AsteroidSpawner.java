package com.test.game.Spawners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.test.game.Space.Asteroid;
import com.test.game.Renderable;

public class AsteroidSpawner implements Spawner<Asteroid>, Renderable {

    private static AsteroidSpawner instance;

    private String folderPath = "PNG/Meteors";
    private FileHandle[] fileNames = Gdx.files.internal(folderPath).list();
    private Array<Texture> textures = new Array<>();
    private Array<Asteroid> asteroids = new Array<>();

    private Batch batch;

    private final int COUNT = 15;

    public AsteroidSpawner(Batch batch) {
        if (instance != null) {
            System.err.println("No duplicate AsteroidSpawner");
        }
        this.batch = batch;
        instance = this;
        for(FileHandle fileName : fileNames) {
            Texture texture = new Texture(fileName);
            textures.add(texture);
        }
        spawn(COUNT);
    }

    private void spawn(int count) {
        for (int i = 0; i < count; i++) {
            int positionX = MathUtils.random(Gdx.graphics.getWidth());
            int positionY = MathUtils.random(Gdx.graphics.getHeight());
            Asteroid asteroid = new Asteroid(positionX, positionY, textures.get(MathUtils.random(0, textures.size - 1)));
            asteroids.add(asteroid);
        }
    }

    @Override
    public void clear() {
        asteroids.clear();
    }

    @Override
    public void render() {
        if (asteroids.size < COUNT) {
            spawn(COUNT - asteroids.size);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.rotateTo();
            asteroid.moveTo();
            asteroid.render(batch);
        }
    }

    @Override
    public Array<Asteroid> getAll() {
        return asteroids;
    }

    @Override
    public void remove(Asteroid asteroid) {
        asteroids.removeValue(asteroid, false);
    }

    @Override
    public void add(Asteroid asteroid) {
        asteroids.add(asteroid);
    }
}
