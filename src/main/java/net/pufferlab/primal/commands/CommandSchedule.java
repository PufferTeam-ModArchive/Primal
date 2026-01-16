package net.pufferlab.primal.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.world.ScheduledTask;
import net.pufferlab.primal.world.SchedulerData;

public class CommandSchedule extends CommandSub {

    public static String[] arguments = new String[] { "", "list" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("list")) {
                String size = Integer.toString(
                    SchedulerData.getTasks(sender.getEntityWorld())
                        .size());
                sender.addChatMessage(
                    new ChatComponentText(
                        "List of ScheduledTasks in dimension " + sender.getEntityWorld().provider.dimensionId));
                sender.addChatMessage(new ChatComponentText("Size of List :" + size));
                for (ScheduledTask task : SchedulerData.getTasks(sender.getEntityWorld())) {
                    sender.addChatMessage(new ChatComponentText(task.toString()));
                }
            } else {
                try {
                    int number = Integer.parseInt(args[0]);
                    sender.addChatMessage(
                        new ChatComponentText(
                            "Added ScheduledTask in dimension " + sender.getEntityWorld().provider.dimensionId));
                    sender.addChatMessage(new ChatComponentText("Task is Scheduled in " + number + " ticks"));
                    SchedulerData.addScheduledTask(number, sender.getEntityWorld());
                } catch (NumberFormatException ignored) {}

            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Arrays.asList(arguments) : null;
    }

    @Override
    public String getCommandName() {
        return "schedule";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
