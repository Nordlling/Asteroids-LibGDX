package com.test.game.Config;

public class ScreenConfig extends Config {

    public String textureName;
    public float width;
    public float height;

    @Override
    public void loadDefault() {
        textureName = "Backgrounds/darkPurple.png";
        width = 1200;
        height = 720;
    }
    
}
