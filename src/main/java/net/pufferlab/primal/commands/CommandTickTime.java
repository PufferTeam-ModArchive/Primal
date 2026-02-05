package net.pufferlab.primal.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.world.GlobalTickingData;

public class CommandTickTime extends CommandSub {

    public static String[] arguments = new String[] { "", "add" };

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
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Arrays.asList(arguments) : null;
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
