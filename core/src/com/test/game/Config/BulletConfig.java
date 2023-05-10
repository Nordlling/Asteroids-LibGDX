package com.test.game.Config;

public class BulletConfig extends Config {

    public String textureName;
    public float width;
    public float height;
    public float speed;

    @Override
    public void loadDefault() {
        textureName = "PNG/Lasers/laserBlue02.png";
        width = 10;
        height = 30;
        speed = 500;
    }
    
}
