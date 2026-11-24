package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.QuestType;

public class CommandQuest extends CommandSub {

    public static String[] arguments = { "", "menu", "unlock", "lock" };

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) {
            return arguments;
        }
        if (args.length > 0) {
            if (args[0].equals("unlock") || args[0].equals("lock")) return QuestType.valuesName()
                .toArray(new String[0]);
        }
        return null;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        if (args.length > 0) {
            if (args[0].equals("menu")) {
                Primal.proxy.openQuestGui(
                    player,
                    sender.getEntityWorld(),
                    sender.getPlayerCoordinates().posX,
                    sender.getPlayerCoordinates().posY,
                    sender.getPlayerCoordinates().posZ);
            }
            if (args.length > 1) {
                QuestType quest = QuestType.getQuest(args[1]);
                if (args[0].equals("unlock")) {
                    QuestType.unlockQuest(player, quest);
                }
                if (args[0].equals("lock")) {
                    QuestType.lockQuest(player, quest);
                }
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
