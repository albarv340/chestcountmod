package tk.avicia.chestcountmod.configs;

import com.google.gson.JsonObject;
import tk.avicia.chestcountmod.CustomFile;
import tk.avicia.chestcountmod.ChestCountMod;

public class ConfigsSection {
    public ConfigsButton button;
    public String title;
    private CustomFile customFile;
    private String configsKey;

    public ConfigsSection(String title, ConfigsButton button, String configsKey) {
        this.title = title;
        this.button = button;
        this.configsKey = configsKey;

        this.button.setConfigsSection(this);
        this.customFile = new CustomFile(ChestCountMod.getMC().mcDataDir, "chestcountmod/configs/configs.json");
    }

    public void updateConfigs(String newValue) {
        JsonObject configsJson = this.customFile.readJson();
        configsJson.addProperty(this.configsKey, newValue);

        ChestCountMod.CONFIG.setConfigs(configsJson);
        this.customFile.writeJson(configsJson);
    }
}
