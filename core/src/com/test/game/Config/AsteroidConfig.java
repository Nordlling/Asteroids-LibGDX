package com.test.game.Config;

public class AsteroidConfig extends Config {

    public String folderTexture;
    public float minSpeed;
    public float maxSpeed;
    public float minSpeedRotation;
    public float maxSpeedRotation;
    public int count;

    @Override
    public void loadDefault() {
        folderTexture = "PNG/Meteors";
        minSpeed = 100;
        maxSpeed = 400;
        minSpeedRotation = 100;
        maxSpeedRotation = 400;
        count = 15;
    }
    
}
