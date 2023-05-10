package com.test.game.Spawners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.test.game.Config.AsteroidConfig;
import com.test.game.Game;
import com.test.game.Space.Asteroid;
import com.test.game.Renderable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AsteroidSpawner implements Spawner<Asteroid>, Renderable {

    private static final AsteroidConfig asteroidConfig = Game.getConfigManager().getConfig(AsteroidConfig.class);

    private FileHandle[] fileNames = Gdx.files.internal(asteroidConfig.folderTexture).list();
    private Array<String> fileNamesFromJar = new Array<>();
    private Array<Texture> textures = new Array<>();
    private Array<Asteroid> asteroids = new Array<>();

    private Batch batch;



    public AsteroidSpawner(Batch batch) throws IOException {
        this.batch = batch;

        if (fileNames.length == 0) {
            String jarFilePath = AsteroidSpawner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().startsWith(asteroidConfig.folderTexture)) {
                    fileNamesFromJar.add(entry.getName());
                }
            }
            jarFile.close();
            for(String fileName : fileNamesFromJar) {
                Texture texture = new Texture(fileName);
                textures.add(texture);
            }
        } else {
            for (FileHandle fileName : fileNames) {
                Texture texture = new Texture(fileName);
                textures.add(texture);
            }
        }
        spawn(asteroidConfig.count);

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
        if (asteroids.size < asteroidConfig.count) {
            spawn(asteroidConfig.count - asteroids.size);
        }
        for (Asteroid asteroid : asteroids) {
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

    @Override
    public void dispose() {
        for (Asteroid asteroid : asteroids) {
            asteroid.dispose();
        }
    }
}
