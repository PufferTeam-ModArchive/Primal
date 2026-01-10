package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.events.ticks.WorldTickingData;

public class CommandTickTime extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        sender.addChatMessage(
            new ChatComponentText("TickTime: " + WorldTickingData.getTickTime(sender.getEntityWorld())));
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
