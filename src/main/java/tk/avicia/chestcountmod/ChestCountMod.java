package tk.avicia.chestcountmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tk.avicia.chestcountmod.configs.Config;
import tk.avicia.chestcountmod.configs.ConfigSetting;
import tk.avicia.chestcountmod.configs.ConfigsCommand;
import tk.avicia.chestcountmod.configs.locations.Locations;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;


@Mod(modid = ChestCountMod.MODID, name = ChestCountMod.NAME, version = ChestCountMod.VERSION)
public class ChestCountMod {
    public static final String MODID = "chestcountmod";
    public static final String NAME = "ChestCountMod";
    public static final String VERSION = "1.1";

    public static final Config CONFIG = new Config(new ConfigSetting[]{
            new ConfigSetting("Randomize color of Loot Chest names", new String[]{"Enabled", "Disabled"}, "Enabled", "enableColoredName"),
            new ConfigSetting("Say mythic found in chat", new String[]{"Enabled", "Disabled"}, "Enabled", "displayMythicOnFind"),
            new ConfigSetting("Say mythic type in chat on mythic found", new String[]{"Enabled", "Disabled"}, "Enabled", "displayMythicTypeOnFind"),
            new ConfigSetting("Always display chest count on screen", new String[]{"Enabled", "Disabled"}, "Disabled", "alwaysShowChestCount"),
            new ConfigSetting("Always display session chest count on screen", new String[]{"Enabled", "Disabled"}, "Disabled", "alwaysShowSessionChestCount"),
            new ConfigSetting("Always display dry count on screen", new String[]{"Enabled", "Disabled"}, "Disabled", "alwaysShowDry"),
            new ConfigSetting("Always display last mythic on screen", new String[]{"Enabled", "Disabled"}, "Disabled", "alwaysShowLastMythic"),
            new ConfigSetting("Info location", new String[]{"Edit", "Editing"}, "Edit", "infoLocation")
    });

    public static final Locations LOCATIONS = new Locations(new HashMap<String, String>() {{
        put("infoLocation", "0.1,0.4");
    }});

    private static final ChestCountData CHEST_COUNT_DATA = new ChestCountData();
    private static final MythicData MYTHIC_DATA = new MythicData();
    public static String PLAYER_UUID = "";

    public static TextFormatting getRandom(TextFormatting[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static ChestCountData getChestCountData() {
        return CHEST_COUNT_DATA;
    }

    public static MythicData getMythicData() {
        return MYTHIC_DATA;
    }

    public static Minecraft getMC() {
        return Minecraft.getMinecraft();
    }

    public static void drawString(String text, int x, int y, Color color) {
        FontRenderer fontRenderer = getMC().fontRenderer;
        fontRenderer.drawString(text, x, y, color.getRGB());
    }

    public static void drawCenteredString(String text, int x, int y, Color color) {
        FontRenderer fontRenderer = getMC().fontRenderer;
        fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color.getRGB());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandlerClass());
        ClientCommandHandler.instance.registerCommand(new LastMythicCommand());
        ClientCommandHandler.instance.registerCommand(new ConfigsCommand());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CONFIG.initializeConfigs();
        LOCATIONS.initializeLocations();
    }

}
