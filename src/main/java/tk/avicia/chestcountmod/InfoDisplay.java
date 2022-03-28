package tk.avicia.chestcountmod;

import com.google.gson.JsonObject;
import tk.avicia.chestcountmod.configs.locations.MultipleElements;
import tk.avicia.chestcountmod.configs.locations.TextElement;

import java.awt.*;
import java.util.ArrayList;

public class InfoDisplay {
    public static MultipleElements getElementsToDraw() {
        int dry = ChestCountMod.getMythicData().getChestsDry();
        String lastMythic = "";
        try {
            JsonObject lastMythicObject = ChestCountMod.getMythicData().getLastMythic();
            if (lastMythicObject != null) {
                lastMythic = lastMythicObject.get("mythic").getAsString();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        String shortLastMythic = "";
        if (lastMythic.length() != 0) {
            try {
                String[] wordsInString = lastMythic.split(" ");
                shortLastMythic = wordsInString[1] + " " + wordsInString[wordsInString.length - 1];
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        String finalLastMythic = "Last Mythic: " + shortLastMythic;
        ArrayList<TextElement> elementsList = new ArrayList<>();

        Point location = new Point(ChestCountMod.LOCATIONS.getStartX("infoLocation"), ChestCountMod.LOCATIONS.getStartY("infoLocation"));
        boolean showChestCount = ChestCountMod.CONFIG.getConfigBoolean("alwaysShowChestCount");
        boolean showSessionChestCount = ChestCountMod.CONFIG.getConfigBoolean("alwaysShowSessionChestCount");
        boolean showDryStreak = ChestCountMod.CONFIG.getConfigBoolean("alwaysShowDry");
        boolean showLastMythic = ChestCountMod.CONFIG.getConfigBoolean("alwaysShowLastMythic");

        // offset balances the displays, so they don't have blank rows
        int offset = 0;
        if (showChestCount) {
            elementsList.add(new TextElement("Chests Opened: " + ChestCountMod.getChestCountData().getTotalChestCount(), location.x + 1, location.y + 1, Color.BLACK));
            elementsList.add(new TextElement("Chests Opened: " + ChestCountMod.getChestCountData().getTotalChestCount(), location.x, location.y, Color.WHITE));
            offset++;
        }
        if (showSessionChestCount) {
            elementsList.add(new TextElement("Session Chests: " + ChestCountMod.getChestCountData().getSessionChestCount(), location.x + 1, location.y + (12 * offset) + 1, Color.BLACK));
            elementsList.add(new TextElement("Session Chests: " + ChestCountMod.getChestCountData().getSessionChestCount(), location.x, location.y + (12 * offset), Color.WHITE));
            offset++;
        }
        if (showDryStreak) {
            elementsList.add(new TextElement("Chests Dry: " + dry, location.x + 1, location.y + (12 * offset) + 1, Color.BLACK));
            elementsList.add(new TextElement("Chests Dry: " + dry, location.x, location.y + (12 * offset), Color.WHITE));
            offset++;
        }
        if (showLastMythic) {
            if (lastMythic.length() != 0) {
                elementsList.add(new TextElement(finalLastMythic, location.x + 1, location.y + (12 * offset) + 1, Color.BLACK));
                elementsList.add(new TextElement(finalLastMythic, location.x, location.y + (12 * offset), new Color(168, 0, 168)));
            }
        }
        return new MultipleElements("infoLocation", elementsList);
    }
}
