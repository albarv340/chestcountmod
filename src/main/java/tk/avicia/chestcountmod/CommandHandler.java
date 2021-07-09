package tk.avicia.chestcountmod;

import com.google.gson.JsonObject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

import java.util.Arrays;
import java.util.List;

public class CommandHandler extends CommandBase implements IClientCommand {
    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "lastmythic";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("lm", "lmf", "glm", "mythic");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/lm";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        JsonObject lastMythic = ChestCountMod.getMythicData().getLastMythic();
        if (lastMythic != null) {
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Last mythic: " + TextFormatting.DARK_PURPLE
                    + lastMythic.get("mythic").getAsString()));
            sender.sendMessage(new TextComponentString(TextFormatting.DARK_AQUA + "In chest #" + TextFormatting.GOLD + lastMythic.get("chestCount")
                    + TextFormatting.DARK_AQUA + " after " + TextFormatting.RED + lastMythic.get("dry") + TextFormatting.DARK_AQUA + " chests dry."));
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "In the chest at: " + lastMythic.get("x") + " " + lastMythic.get("y") + " " + lastMythic.get("z")));
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Current dry streak: " + ChestCountMod.getMythicData().getChestsDry()));
        }else{
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "No mythics found"));
        }
    }
}
