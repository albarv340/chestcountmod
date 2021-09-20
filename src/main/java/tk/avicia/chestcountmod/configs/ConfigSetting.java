package tk.avicia.chestcountmod.configs;

public class ConfigSetting {
    public String sectionText, defaultValue, configsKey;
    public String[] choices;

    public ConfigSetting(String sectionText, String[] choices, String defaultValue, String configsKey) {
        this.sectionText = sectionText;
        this.choices = choices;
        this.defaultValue = defaultValue;
        this.configsKey = configsKey;
    }
}
