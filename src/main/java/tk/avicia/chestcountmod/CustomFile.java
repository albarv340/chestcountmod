package tk.avicia.chestcountmod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CustomFile extends File {
    public CustomFile(File parent, String child) {
        super(parent, child);

        if (!super.exists()) {
            try {
                String[] split = child.split("/");

                if (split.length >= 1) {
                    String[] directorySplit = Arrays.copyOfRange(split, 0, split.length - 1);
                    if (directorySplit.length >= 1) {
                        String directory = String.join("/", directorySplit);
                        System.out.println(directory);
                        File file = new File(directory);
                        file.mkdirs();
                    }
                }
                super.createNewFile();
                this.writeJson("{}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JsonObject readJson() {
        StringBuilder output = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(this), StandardCharsets.UTF_8);

            int currentChar;
            while ((currentChar = reader.read()) != -1) {
                output.append((char) currentChar);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            return new JsonParser().parse(output.toString()).getAsJsonObject();
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            return new JsonParser().parse("{}").getAsJsonObject();
        }
    }

    public void writeJson(JsonObject jsonObject) {
        this.writeJson(jsonObject.toString());
    }

    public void writeJson(String text) {
        try {
            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(this), StandardCharsets.UTF_8);
            fileWriter.write(text, 0, text.length());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
