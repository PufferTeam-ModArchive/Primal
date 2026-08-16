package net.pufferlab.primal.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.TextUtils;

public class CommandBlockInfo extends CommandSub {

    public static String[] arguments = { "", "debug" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = CommandBase.getCommandSenderAsPlayer(sender);
        if (args.length == 1) {
            if (args[0].equals("debug")) {
                PlayerData data = PlayerData.get(player);
                boolean state = !data.getBlockInfoDebug();
                data.setBlockInfoDebug(state);
                sender.addChatMessage(new ChatComponentText(TextUtils.getStateTooltip(state, "Enabled", "Disabled")));
            }
        }
    }

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) return arguments;
        return null;
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
