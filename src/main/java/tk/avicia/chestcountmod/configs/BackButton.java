package tk.avicia.chestcountmod.configs;

import net.minecraft.client.gui.GuiButton;
import tk.avicia.chestcountmod.ChestCountMod;

public class BackButton extends GuiButton {

    public BackButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        ChestCountMod.getMC().displayGuiScreen(new ConfigsGui());
    }
}
