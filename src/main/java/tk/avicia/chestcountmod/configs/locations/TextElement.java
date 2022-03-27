package tk.avicia.chestcountmod.configs.locations;

import net.minecraft.client.gui.FontRenderer;
import tk.avicia.chestcountmod.ChestCountMod;

import java.awt.*;

public class TextElement {
    protected float x, y;
    protected Color color;
    protected String text;

    public TextElement(String text, float x, float y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
    }

    public void draw() {
        ChestCountMod.drawCenteredString(text, (int) x, (int) y, color);
    }

    public void move(float changeX, float changeY) {
        x += changeX;
        y += changeY;
    }

    public boolean inRange(int mouseX, int mouseY) {
        // Returns true if the coordinates are overlapping with the textElement
        FontRenderer fontRenderer = ChestCountMod.getMC().fontRenderer;
        final int halfWidth = fontRenderer.getStringWidth(this.text) / 2;
        final int halfHeight = 6;
        return mouseX >= x - halfWidth && mouseX <= x + halfWidth &&
                mouseY >= y - halfHeight && mouseY <= y + halfHeight;
    }

    public String getText() {
        return text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setText(String text) {
        this.text = text;
    }
}
