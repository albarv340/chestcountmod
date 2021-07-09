package tk.avicia.chestcountmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.awt.*;
import java.util.Random;


@Mod(modid = ChestCountMod.MODID, name = ChestCountMod.NAME, version = ChestCountMod.VERSION)
public class ChestCountMod {
    public static final String MODID = "chestcountmod";
    public static final String NAME = "ChestCountMod";
    public static final String VERSION = "1.0";

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

    @EventHandler

    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandlerClass());
        ClientCommandHandler.instance.registerCommand(new CommandHandler());
    }

}
