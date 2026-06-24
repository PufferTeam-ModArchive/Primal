package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.pufferlab.primal.utils.TextUtils;

public abstract class CommandSub extends CommandBase implements ISubCommand {

    @Override
    public boolean canHandleCommand(ICommandSender sender) {
        if (!canCommandSenderUseCommand(sender)) return false;
        return true;
    }

    public void sendChatMessage(ICommandSender sender, String message) {
        TextUtils.sendChatMessage(sender, message);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        String[] subArgs = getSubArgs();
        if (subArgs == null) {
            return super.addTabCompletionOptions(sender, args);
        }
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, subArgs) : null;
    }

    public String[] getSubArgs() {
        return null;
    }
}
