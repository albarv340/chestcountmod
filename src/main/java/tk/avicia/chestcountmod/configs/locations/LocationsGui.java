package tk.avicia.chestcountmod.configs.locations;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import tk.avicia.chestcountmod.ChestCountMod;
import tk.avicia.chestcountmod.InfoDisplay;
import tk.avicia.chestcountmod.configs.BackButton;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LocationsGui extends GuiScreen {
    private List<MultipleElements> items;
    private static boolean isOpen = false;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Makes blur
        this.drawWorldBackground(0);
        // Draws a shadowed string with a dark color, to make it easier to read depending on the background
        this.drawCenteredString(this.fontRenderer, ChatFormatting.BOLD + "ChestCountMod Locations", this.width / 2 + 1, 6, 0x444444);
        this.drawCenteredString(this.fontRenderer, ChatFormatting.BOLD + "ChestCountMod Locations", this.width / 2, 5, 0xFFFFFF);
        items.forEach(MultipleElements::drawGuiElement);
        buttonList.forEach(button -> button.drawButton(ChestCountMod.getMC(), mouseX, mouseY, partialTicks));
    }

    @Override
    public void initGui() {
        items = Arrays.asList(
                InfoDisplay.getElementsToDraw()
        );
        buttonList.add(new ResetButton(0, this.width / 2 - 50, this.height - 30, 100, 20, "Reset to Defaults", this));
        buttonList.add(new BackButton(1, 10, 10, 40, 20, "Back"));
        isOpen = true;
    }

    @Override
    public void onResize(@Nonnull Minecraft mineIn, int w, int h) {
        super.onResize(mineIn, w, h);

        this.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int guiScale = new ScaledResolution(ChestCountMod.getMC()).getScaleFactor();
        items.forEach(e -> e.pickup((Mouse.getX() / guiScale), height - ((Mouse.getY() / guiScale))));

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        int guiScale = new ScaledResolution(ChestCountMod.getMC()).getScaleFactor();
        items.forEach(e -> e.move(Mouse.getX() / guiScale, height - (Mouse.getY() / guiScale)));

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        int guiScale = new ScaledResolution(ChestCountMod.getMC()).getScaleFactor();
        items.forEach(e -> e.release(Mouse.getX() / guiScale, height - (Mouse.getY() / guiScale)));

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
        items.forEach(MultipleElements::save);
        isOpen = false;
        super.onGuiClosed();
    }

    public static boolean isOpen() {
        return isOpen;
    }
}
