package tk.avicia.chestcountmod.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import tk.avicia.chestcountmod.ChestCountMod;
import tk.avicia.chestcountmod.CustomFile;

public class Config {

    private boolean shouldGuiConfigBeDrawn = false;
    private JsonObject configs = null;
    private ConfigSetting[] configsArray;

    public Config(ConfigSetting[] configsArray) {
        this.configsArray = configsArray;
    }

    public String getConfig(String configKey) {
        JsonElement configElement = configs.get(configKey);

        if (configElement == null || configElement.isJsonNull()) {
            return null;
        } else {
            return configElement.getAsString();
        }
    }

    /*
     * If the setting is a boolean and is true, this will return true, otherwise false
     */
    public boolean getConfigBoolean(String configKey) {
        String configValue = getConfig(configKey);
        if (configValue == null) return false;
        return configValue.equals("Enabled");
    }

    public void initializeConfigs() {
        CustomFile configsFile = new CustomFile(ChestCountMod.getMC().mcDataDir, "chestcountmod/configs/configs.json");
        JsonObject configsJson = configsFile.readJson();
        boolean changed = false;

        for (ConfigSetting config : configsArray) {
            JsonElement configElement = configsJson.get(config.configsKey);

            if (configElement == null || configElement.isJsonNull()) {
                configsJson.addProperty(config.configsKey, config.defaultValue);
                changed = true;
            }
        }

        if (changed) {
            configsFile.writeJson(configsJson);
        }

        configs = configsJson;
    }

    public boolean shouldGuiConfigBeDrawn() {
        return shouldGuiConfigBeDrawn;
    }

    public JsonObject getConfigs() {
        return configs;
    }

    public ConfigSetting[] getConfigsArray() {
        return configsArray;
    }

    public void setShouldGuiConfigBeDrawn(boolean shouldGuiConfigBeDrawn) {
        this.shouldGuiConfigBeDrawn = shouldGuiConfigBeDrawn;
    }

    public void setConfigs(JsonObject configs) {
        this.configs = configs;
    }
}
