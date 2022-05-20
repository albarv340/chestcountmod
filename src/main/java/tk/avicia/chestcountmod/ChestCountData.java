package tk.avicia.chestcountmod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ChestCountData {
    private int totalChestCount = 0;
    private int sessionChestCount = 0;
    private boolean hasBeenInitialized = false;

    public ChestCountData() {

    }

    public void updateChestCount() {
//        try {
//            URL urlObject = new URL("https://api.wynncraft.com/v2/player/" +
//                    ChestCountMod.PLAYER_UUID + "/stats");
//            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
//            con.setRequestMethod("GET");
//            int responseCode = con.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                this.totalChestCount = new JsonParser().parse(response.toString()).getAsJsonObject()
//                        .getAsJsonArray("data").get(0).getAsJsonObject().get("global").getAsJsonObject()
//                        .get("chestsFound").getAsInt();
//                this.hasBeenInitialized = true;
//            } else {
//                System.out.println("GET request not worked");
//                throw new NotFound();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        JsonObject mythicData = ChestCountMod.getMythicData().getMythicData();
        JsonElement dryCount = ChestCountMod.getMythicData().getDryData().get(ChestCountMod.PLAYER_UUID);
        JsonObject lastMythic = ChestCountMod.getMythicData().getLastMythic();

        if (lastMythic != null && dryCount != null) {
            this.totalChestCount = dryCount.getAsInt() + lastMythic.get("chestCount").getAsInt();
        } else if (lastMythic != null) {
            this.totalChestCount = lastMythic.get("chestCount").getAsInt();
        } else if (dryCount != null) {
            this.totalChestCount = dryCount.getAsInt();
        } else {
            this.totalChestCount = 0;
        }
        this.hasBeenInitialized = true;

    }

    public int getTotalChestCount() {
        return totalChestCount + sessionChestCount;
    }

    public void addToSessionChestCount() {
        this.sessionChestCount++;
    }

    public int getSessionChestCount() {
        return sessionChestCount;
    }

    public boolean hasBeenInitialized() {
        return hasBeenInitialized;
    }
}
