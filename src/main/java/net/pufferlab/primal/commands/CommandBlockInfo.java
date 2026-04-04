package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.RecipeUtils;
import net.pufferlab.primal.utils.Utils;

public class CommandBlockInfo extends CommandSub {

    public static String[] arguments = new String[] { "", "debug" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = CommandBase.getCommandSenderAsPlayer(sender);
        if (args.length == 1) {
            if (args[0].equals("debug")) {
                PlayerData data = PlayerData.get(player);
                boolean state = !data.blockInfoDebug;
                data.setBlockInfoDebug(state);
                sender.addChatMessage(new ChatComponentText(RecipeUtils.getStateTooltip(state, "Enabled", "Disabled")));
                Primal.proxy.packet.sendPlayerData(player, data);
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Utils.asList(arguments) : null;
    }

    @Override
    public String getCommandName() {
        return "blockinfo";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
