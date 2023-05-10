package com.test.game.Config;

import java.util.ArrayList;

public class ConfigManager {
    
    private boolean loadDefault = false;
    private ArrayList<Config> configs;

    public ConfigManager() {
        configs = new ArrayList<>();
        configs.add(new ScreenConfig());
        configs.add(new ShipConfig());
        configs.add(new AsteroidConfig());
        configs.add(new BulletConfig());
    }
    
    public void init() {
        if (loadDefault) {
            loadDefaultAll();
            return;
        }
        for (int i = 0; i < configs.size(); i++) {
            Config loaded = configs.get(i).loadFromJson();
            if (loaded == null) {
                configs.get(i).loadDefault();
                configs.get(i).saveToJson();
            } else {
                configs.set(i,loaded);
            }
        }
    }
    
    public void loadDefaultAll() {
        for (Config config : configs) {
            config.loadDefault();
        }
    }
    
    public void saveAll() {
        for (Config config : configs) {
            config.saveToJson();
        }
    }
    
    public <T extends Config> T getConfig(Class<T> type) {
        for (Config config : configs) {
            if (type.isInstance(config)) {
                return (T) config;
            }
        }
        return null;
    }

    public ArrayList<Config> getConfigs() {
        return configs;
    }

}
