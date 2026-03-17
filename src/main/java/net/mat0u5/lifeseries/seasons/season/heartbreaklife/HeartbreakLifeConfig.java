package net.mat0u5.lifeseries.seasons.season.heartbreaklife;

import net.mat0u5.lifeseries.Main;
import net.mat0u5.lifeseries.config.ConfigManager;

public class HeartbreakLifeConfig extends ConfigManager {
    public HeartbreakLifeConfig() {
        super("./config/" + Main.MOD_ID, "heartbreaklife.properties");
    }

    @Override
    public void instantiateProperties() {
        // Heartbreak Life uses 3 default lives.
        DEFAULT_LIVES.defaultValue = 3;
        super.instantiateProperties();
    }
}
