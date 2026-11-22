package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.pufferlab.primal.Primal;

public class CommandQuest extends CommandSub {

    public static String[] arguments = { "", "menu" };

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) return arguments;
        return null;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("menu")) {
                Primal.proxy.openQuestGui(
                    getCommandSenderAsPlayer(sender),
                    sender.getEntityWorld(),
                    sender.getPlayerCoordinates().posX,
                    sender.getPlayerCoordinates().posY,
                    sender.getPlayerCoordinates().posZ);
            }
        }
    }

    @Override
    public String getCommandName() {
        return "quest";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
