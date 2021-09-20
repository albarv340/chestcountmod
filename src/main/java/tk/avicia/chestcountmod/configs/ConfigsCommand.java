package tk.avicia.chestcountmod.configs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import tk.avicia.chestcountmod.ChestCountMod;

import java.util.Arrays;
import java.util.List;

public class ConfigsCommand extends CommandBase implements IClientCommand {
    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "chestcountconfig";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("ccc", "cccf", "chestcountmod");
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
        ChestCountMod.CONFIG.setShouldGuiConfigBeDrawn(true);
    }
}
