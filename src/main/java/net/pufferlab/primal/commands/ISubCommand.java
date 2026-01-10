package net.pufferlab.primal.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public interface ISubCommand extends ICommand {

    public void handleCommand(ICommandSender sender, String[] args);

    public boolean canHandleCommand(ICommandSender sender);

    default void processCommand(ICommandSender sender, String[] args) {}

    default String getCommandUsage(ICommandSender sender) {
        return null;
    }

}
