package tk.avicia.chestcountmod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MythicData {
    private int chestsDry = 0;

    public MythicData() {
    }

    public JsonObject getMythicData() {
        CustomFile mythicData = new CustomFile(ChestCountMod.getMC().mcDataDir, "chestcountmod/mythicData.json");

        return mythicData.readJson();
    }

    public void addMythic(int chestCount, String mythic, int dry, int x, int y, int z) {
        try {

            JsonObject currentData = getMythicData();
            JsonObject newData = new JsonObject();
            newData.addProperty("chestCount", chestCount);
            newData.addProperty("mythic", mythic);
            newData.addProperty("dry", dry);
            newData.addProperty("x", x);
            newData.addProperty("y", y);
            newData.addProperty("z", z);
            if (!currentData.has(ChestCountMod.PLAYER_UUID)) {
                currentData.add(ChestCountMod.PLAYER_UUID, new JsonArray());
            }
            currentData.get(ChestCountMod.PLAYER_UUID).getAsJsonArray().add(newData);
            CustomFile mythicData = new CustomFile(ChestCountMod.getMC().mcDataDir, "chestcountmod/mythicData.json");
            mythicData.writeJson(currentData);
            updateDry();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void updateDry() {
        try {
            JsonObject lastMythic = getLastMythic();
            if (lastMythic != null) {
                chestsDry = ChestCountMod.getChestCountData().getTotalChestCount() - lastMythic.get("chestCount").getAsInt();
            } else {
                chestsDry = ChestCountMod.getChestCountData().getTotalChestCount();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void addToDry() {
        chestsDry++;
    }

    public int getChestsDry() {
        return chestsDry;
    }

    public JsonObject getLastMythic() {
        JsonObject mythicData = getMythicData().getAsJsonObject();
        if (!mythicData.has(ChestCountMod.PLAYER_UUID)) {
            mythicData.add(ChestCountMod.PLAYER_UUID, new JsonArray());
        }
        JsonArray mythics = mythicData.get(ChestCountMod.PLAYER_UUID).getAsJsonArray();
        if (mythics.size() > 0) {
            return mythics.get(mythics.size() - 1).getAsJsonObject();
        } else {
            return null;
        }
    }
}
