package tk.avicia.chestcountmod.configs.locations;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.avicia.chestcountmod.ChestCountMod;

public class ResetButton extends GuiButton {
    GuiScreen gui;

    public ResetButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, GuiScreen gui) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.gui = gui;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        ChestCountMod.LOCATIONS.resetToDefault();
        gui.initGui();
    }
}
