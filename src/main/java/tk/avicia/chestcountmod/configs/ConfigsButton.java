package tk.avicia.chestcountmod.configs;

import net.minecraft.client.gui.GuiButton;
import tk.avicia.chestcountmod.ChestCountMod;

import java.util.Arrays;
import java.util.stream.Stream;

public class ConfigsButton extends GuiButton {
    public String[] choices;
    private ConfigsSection configsSection;
    private int currentIndex;

    public ConfigsButton(int buttonId, int x, int y, int widthIn, int heightIn, String[] choices, String defaultValue) {
        super(buttonId, x, y, widthIn, heightIn, defaultValue);

        this.choices = choices;
        this.currentIndex = Arrays.asList(choices).indexOf(defaultValue);
    }

    public ConfigsButton(int buttonId, int x, int y, String[] choices, String defaultValue) {
        super(buttonId, x, y, Stream.of(choices).mapToInt((String choice) -> ChestCountMod.getMC().fontRenderer.getStringWidth(choice)).max().getAsInt() + 4, 20, defaultValue);
        this.choices = choices;
        this.currentIndex = Arrays.asList(choices).indexOf(defaultValue);
    }

    public ConfigsButton(int buttonId, int y, String[] choices, String defaultValue, int guiWidth) {
        super(buttonId, guiWidth - guiWidth / 4 - (Stream.of(choices).mapToInt((String choice) -> ChestCountMod.getMC().fontRenderer.getStringWidth(choice)).max().getAsInt() + 10), y, Stream.of(choices).mapToInt((String choice) -> ChestCountMod.getMC().fontRenderer.getStringWidth(choice)).max().getAsInt() + 10, 20, defaultValue);
        this.choices = choices;
        this.currentIndex = Arrays.asList(choices).indexOf(defaultValue);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.currentIndex++;
        if (this.currentIndex == choices.length) {
            this.currentIndex = 0;
        }
        this.displayString = this.choices[this.currentIndex];

        if (this.configsSection != null) {
            this.configsSection.updateConfigs(this.displayString);
        }
    }

    public void setConfigsSection(ConfigsSection configsSection) {
        this.configsSection = configsSection;
    }
}
