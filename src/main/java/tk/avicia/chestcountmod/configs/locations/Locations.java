package tk.avicia.chestcountmod.configs.locations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.ScaledResolution;
import tk.avicia.chestcountmod.ChestCountMod;
import tk.avicia.chestcountmod.CustomFile;

import java.util.Map;

public class Locations {

    private JsonObject locations = null;
    private Map<String, String> defaultLocations;
    CustomFile locationsFile;


    public Locations(Map<String, String> defaultLocations) {
        this.defaultLocations = defaultLocations;
    }

    public String getLocation(String locationKey) {
        JsonElement locationElement = locations.get(locationKey);

        if (locationElement == null || locationElement.isJsonNull()) {
            return defaultLocations.get(locationKey);
        } else {
            return locationElement.getAsString();
        }
    }


    public void initializeLocations() {
        locationsFile = new CustomFile(ChestCountMod.getMC().mcDataDir, "chestcountmod/configs/locations.json");
        JsonObject locationsJson = locationsFile.readJson();
        boolean changed = false;

        for (Map.Entry<String, String> location : defaultLocations.entrySet()) {
            JsonElement locationsElement = locationsJson.get(location.getKey());

            if (locationsElement == null || locationsElement.isJsonNull()) {
                locationsJson.addProperty(location.getKey(), location.getValue());
                changed = true;
            }
        }

        if (changed) {
            locationsFile.writeJson(locationsJson);
        }

        locations = locationsJson;
    }

    public void save(MultipleElements multipleElements) {
        JsonObject locations = locationsFile.readJson();
        locations.addProperty(multipleElements.getKey(), multipleElements.toString());
        this.locations = locations;
        locationsFile.writeJson(locations);
    }

    public void resetToDefault() {
        JsonObject locationsJson = locationsFile.readJson();

        for (Map.Entry<String, String> locationData : defaultLocations.entrySet()) {
            locationsJson.addProperty(locationData.getKey(), locationData.getValue());
        }

        locationsFile.writeJson(locationsJson);
        locations = locationsJson;
    }

    public int getStartX(String key) {
        String locationText = getLocation(key);
        if (locationText == null) return 0;
        // The location X is stored as portion of screenwidth, so we multiply it by screenwidth to get the x value
        float screenWidth = new ScaledResolution(ChestCountMod.getMC()).getScaledWidth();
        return (int) (Float.parseFloat(locationText.split(",")[0]) * screenWidth);
    }

    public int getStartY(String key) {
        String locationText = getLocation(key);
        if (locationText == null) return 0;

        float screenHeight = (new ScaledResolution(ChestCountMod.getMC()).getScaledHeight());
        return (int) (Float.parseFloat(locationText.split(",")[1]) * screenHeight);
    }

}


