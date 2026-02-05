package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.world.GlobalTickingData;

public class CommandTickTime extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("add")) {
                int time = Integer.parseInt(args[1]);
                GlobalTickingData.add(time);
            }
        }
        sender.addChatMessage(
            new ChatComponentText("TickTime: " + GlobalTickingData.getTickTime(sender.getEntityWorld())));
    }

    @Override
    public String getCommandName() {
        return "ticktime";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
