package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.pufferlab.primal.world.GlobalTickingData;

public class CommandTickTime extends CommandSub {

    public static String[] arguments = new String[] { "", "add" };

    @Override
    public String[] getSubArgs() {
        return arguments;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("add")) {
                int time = Integer.parseInt(args[1]);
                GlobalTickingData.add(time);
            }
        }
        sendChatMessage(sender, "TickTime: " + GlobalTickingData.getTickTime(sender.getEntityWorld()));
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
