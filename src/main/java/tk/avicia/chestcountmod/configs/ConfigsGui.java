package tk.avicia.chestcountmod.configs;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import tk.avicia.chestcountmod.ChestCountMod;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigsGui extends GuiScreen {
    private final int settingLineHeight = 27;
    ArrayList<ConfigsSection> sectionList = new ArrayList<>();
    ArrayList<ConfigsSection> totalSectionsList = new ArrayList<>();

    public ConfigsGui() {
        super();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Makes blur
        this.drawWorldBackground(0);
        // Draws a shadowed string with a dark color, to make it easier to read depending on the background
        this.drawCenteredString(this.fontRenderer, ChatFormatting.BOLD + "ChestCountMod Configs", this.width / 2 + 1, 6, 0x444444);
        this.drawCenteredString(this.fontRenderer, ChatFormatting.BOLD + "ChestCountMod Configs", this.width / 2, 5, 0xFFFFFF);

        for (ConfigsSection configsSection : this.sectionList) {
            int y = sectionList.indexOf(configsSection) * settingLineHeight;

            int color = 0x777777;
            // Makes the enabled options brighter
            if (configsSection.button.displayString.equals("Enabled")) {
                color = 0xFFFFFF;
            }
            // Draws a shadowed string with the opposite color, to make it easier to read depending on the background
            this.drawString(this.fontRenderer, configsSection.title, this.width / 2 - this.width / 4 + 1, y + 22, 0xFFFFFF - color);
            // Draws the actual string
            this.drawString(this.fontRenderer, configsSection.title, this.width / 2 - this.width / 4, y + 21, color);
            this.drawHorizontalLine(this.width / 4 + 3, this.width / 4 * 3 - 5, y + 38, new Color(255, 255, 255).getRGB());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void initGui() {
        this.sectionList = new ArrayList<>();
        this.totalSectionsList = new ArrayList<>();
        this.buttonList = new ArrayList<>();

        for (ConfigSetting config : ChestCountMod.CONFIG.getConfigsArray()) {
            this.addSection(config);
        }
        this.sectionList.forEach((ConfigsSection configsSection) -> {
            this.buttonList.add(configsSection.button);
        });
    }

    @Override
    public void onResize(Minecraft mineIn, int w, int h) {
        super.onResize(mineIn, w, h);

        this.initGui();
    }

    @Override

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int scrollAmount = Mouse.getDWheel() / 120;

        if (scrollAmount != 0) {
            try {
                this.scroll(scrollAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scroll(int amount) {
        if (this.totalSectionsList.size() == this.sectionList.size()) return;

        int totalAllowed = (int) Math.floor(this.height / settingLineHeight);
        int startingIndex = this.totalSectionsList.indexOf(this.sectionList.get(0));
        if (amount < 0 && startingIndex + totalAllowed < this.totalSectionsList.size()) {
            startingIndex++;
        } else if (amount > 0 && startingIndex > 0) {
            startingIndex--;
        }

        this.sectionList = new ArrayList<>();
        this.buttonList = new ArrayList<>();
        for (int i = startingIndex; i < startingIndex + totalAllowed; i++) {
            this.sectionList.add(this.totalSectionsList.get(i));
        }

        for (ConfigsSection configsSection : this.sectionList) {
            configsSection.button.y = this.sectionList.indexOf(configsSection) * settingLineHeight + 15;
            this.buttonList.add(configsSection.button);
        }
    }

    public void addSection(ConfigSetting config) {
        String configValue = ChestCountMod.CONFIG.getConfig(config.configsKey);
        if (!configValue.equals("")) {
            config.defaultValue = configValue;
        }

        ConfigsSection sectionToAdd = new ConfigsSection(config.sectionText, new ConfigsButton(this.sectionList.size(), this.sectionList.size() * settingLineHeight + 15, config.choices, config.defaultValue, this.width), config.configsKey);
        if ((this.sectionList.size() + 1) * settingLineHeight < this.height) {
            this.sectionList.add(sectionToAdd);
        }

        this.totalSectionsList.add(sectionToAdd);
    }
}
