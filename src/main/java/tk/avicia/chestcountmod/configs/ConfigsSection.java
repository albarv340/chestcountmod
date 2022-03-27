package tk.avicia.chestcountmod.configs;

import tk.avicia.chestcountmod.ChestCountMod;

public class ConfigsSection {
    public ConfigsButton button;
    public String title;
    private String configsKey;

    public ConfigsSection(String title, ConfigsButton button, String configsKey) {
        this.title = title;
        this.button = button;
        this.configsKey = configsKey;

        this.button.setConfigsSection(this);
    }

    public void updateConfigs(String newValue) {
        ChestCountMod.CONFIG.setConfig(configsKey, newValue);
    }
}
