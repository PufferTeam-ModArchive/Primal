package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.pufferlab.primal.events.ticks.SchedulerData;

public class CommandSchedule extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            SchedulerData.scheduleNewTask(Integer.parseInt(args[0]), sender.getEntityWorld());
        }
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
