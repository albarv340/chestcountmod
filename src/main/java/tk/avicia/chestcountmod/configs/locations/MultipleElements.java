package tk.avicia.chestcountmod.configs.locations;

import net.minecraft.client.gui.ScaledResolution;
import tk.avicia.chestcountmod.ChestCountMod;


import java.util.List;

public class MultipleElements {
    // Elements list is a list of textElements that should be drawn in the custom location
    private final List<TextElement> elementsList;
    private final String key;
    private int startX, startY;
    private boolean clicked = false;

    public MultipleElements(String key, List<TextElement> elementsList) {
        this.key = key;
        this.elementsList = elementsList;
    }

    public void draw() {
        if(!LocationsGui.isOpen()) {
            elementsList.forEach(TextElement::draw);
        }
    }

    public void drawGuiElement() {
        elementsList.forEach(TextElement::draw);
    }

    public void pickup(int mouseX, int mouseY) {
        elementsList.forEach(element -> {
            if (element.inRange(mouseX, mouseY)) {
                startX = mouseX;
                startY = mouseY;
                clicked = true;
            }
        });
    }

    public void move(int mouseX, int mouseY) {
        // Gets called when mouse is moved
        if (!clicked) return;

        elementsList.forEach(element -> element.move(mouseX - startX, mouseY - startY));
        startX = mouseX;
        startY = mouseY;
    }

    public void release(int mouseX, int mouseY) {
        if (!clicked) return;

        startX = 0;
        startY = 0;
        clicked = false;
    }

    public void save() {
        ChestCountMod.LOCATIONS.save(this);
    }

    public String getKey() {
        return key;
    }

    public String toString() {
        // The leftmost text
        float x = elementsList.stream().map(TextElement::getX).min(Float::compare).orElse((float) 0);
        // The highest text
        float y = elementsList.stream().map(TextElement::getY).min(Float::compare).orElse((float) 0);

        float screenWidth = new ScaledResolution(ChestCountMod.getMC()).getScaledWidth();
        float screenHeight = new ScaledResolution(ChestCountMod.getMC()).getScaledHeight();
        // The coordinates are saved as portion of screenwidth and screenheight separated by a comma
        float xProp = ((int) x) / screenWidth;
        float yProp = ((int) y) / screenHeight;

        return xProp + "," + yProp;
    }
}
