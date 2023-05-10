package com.test.game.Config;

public class ShipConfig extends Config {

    public String textureName;
    public float width;
    public float height;
    public float speed;
    public float noMoveDistance;
    public int health;
    public int invulnerabilityTime;

    @Override
    public void loadDefault() {
        textureName = "PNG/playerShip2_blue.png";
        width = 64;
        height = 64;
        speed = 300;
        health = 3;
        invulnerabilityTime = 3;
        noMoveDistance = 10;
    }
    
}
