package tk.avicia.chestcountmod;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventHandlerClass {
    private static final TextFormatting[] colors = {TextFormatting.DARK_GRAY, TextFormatting.BLACK, TextFormatting.RED,
            TextFormatting.LIGHT_PURPLE, TextFormatting.DARK_BLUE, TextFormatting.DARK_GREEN, TextFormatting.DARK_RED,
            TextFormatting.DARK_PURPLE, TextFormatting.BLUE};

    private boolean hasMythicBeenRegistered = false;
    private int chestsDry = 0;


    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent event) {
        if (ChestCountMod.getMC().player == null || event.getGui() == null) {
            return;
        }
        if (!ChestCountMod.getChestCountData().hasBeenInitialized()) {
            // Keeps trying to get the chestcount data from the api until it gets it
            Thread thread = new Thread(() -> {
                ChestCountMod.PLAYER_UUID = ChestCountMod.getMC().player.getGameProfile().getId().toString();
                ChestCountMod.getChestCountData().updateChestCount();
                ChestCountMod.getMythicData().updateDry();
            });
            thread.start();
        }
        Container openContainer = ChestCountMod.getMC().player.openContainer;
        if (openContainer instanceof ContainerChest) {
            InventoryBasic lowerInventory = (InventoryBasic) ((ContainerChest) openContainer).getLowerChestInventory();
            String containerName = lowerInventory.getName();
            // It is a lootchest and it doesn't already have a new name
            if (containerName.contains("Loot Chest") && !containerName.contains("#")) {
                // All this code runs once when the loot chest has been opened
                ChestCountMod.getChestCountData().addToSessionChestCount();
                ChestCountMod.getMythicData().addToDry();
                this.chestsDry = ChestCountMod.getMythicData().getChestsDry();
                // Defaults to not having a mythic in the chest
                this.hasMythicBeenRegistered = false;
                lowerInventory.setCustomName(ChestCountMod.getRandom(colors) + containerName + " #" +
                        ChestCountMod.getChestCountData().getSessionChestCount()
                        + " Tot: " + ChestCountMod.getChestCountData().getTotalChestCount());
            }
        }
    }


    @SubscribeEvent
    public void guiDraw(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (ChestCountMod.getMC().player == null || event.getGui() == null) {
            return;
        }
        Container openContainer = ChestCountMod.getMC().player.openContainer;
        if (openContainer instanceof ContainerChest) {
            InventoryBasic lowerInventory = (InventoryBasic) ((ContainerChest) openContainer).getLowerChestInventory();
            String containerName = lowerInventory.getName();
            if (containerName.contains("Loot Chest")) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(1f, 1f, 1f);
                int screenWidth = event.getGui().width;
                int screenHeight = event.getGui().height;
                ChestCountMod.drawString(chestsDry + " Dry", screenWidth / 2 - 20, screenHeight / 2 - 11, Color.BLACK);
                GlStateManager.popMatrix();
                int itemCount = 0;
                for (int i = 0; i < 27; i++) {
                    if (!lowerInventory.getStackInSlot(i).getDisplayName().equals("Air")) {
                        itemCount++;
                    }
                }
                if (itemCount == 0) {
                    return; // If there are no items on the chest (or the items haven't loaded) just try again basically
                }
                boolean isMythicInChest = false;

                for (int i = 0; i < 27; i++) {
                    ItemStack itemStack = lowerInventory.getStackInSlot(i);
                    if (!itemStack.getDisplayName().equals("Air")) {
                        List<String> lore = itemStack.getTooltip(ChestCountMod.getMC().player, ITooltipFlag.TooltipFlags.ADVANCED);
                        Optional<String> mythicTier = lore.stream()
                                .filter(line -> Objects.requireNonNull(TextFormatting.getTextWithoutFormattingCodes(line)).contains("Tier: Mythic")).findFirst();
                        Optional<String> itemLevel = lore.stream()
                                .filter(line -> line.contains("Lv. ")).findFirst();

                        if (mythicTier.isPresent()) {
                            if (!hasMythicBeenRegistered) {
                                if (itemLevel.isPresent()) {
                                    String mythicString = itemStack.getDisplayName() + " " +  itemLevel.get();
                                    ChestCountMod.getMC().player.sendMessage(new TextComponentString(mythicString + " : " + TextFormatting.RED + ChestCountMod.getMythicData().getChestsDry() + " dry"));
                                    EntityPlayerSP player = ChestCountMod.getMC().player;
                                    ChestCountMod.getMythicData().addMythic(ChestCountMod.getChestCountData().getTotalChestCount(), TextFormatting.getTextWithoutFormattingCodes(mythicString), this.chestsDry, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
                                }
                            }
                            isMythicInChest = true;
                        }
                    }
                }
                if (isMythicInChest) {
                    if (!this.hasMythicBeenRegistered) {
                        this.hasMythicBeenRegistered = true;
                    }
                }
            }
        }
    }

}
