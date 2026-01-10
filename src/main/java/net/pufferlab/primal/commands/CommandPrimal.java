package net.pufferlab.primal.commands;

import java.util.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.Utils;

public class CommandPrimal extends CommandBase {

    private static final Map<String, ISubCommand> commands = new HashMap<>();
    private static final List<String> namesList = new ArrayList<>();
    private static String names;

    public static void registerSubCommand(ISubCommand command) {
        CommandPrimal.commands.put(command.getCommandName(), command);
    }

    public static String getCommands() {
        if (names == null) {
            for (Map.Entry<String, ISubCommand> entry : commands.entrySet()) {
                namesList.add(entry.getKey());
            }
            names = String.join("|", namesList);
        }
        return names;
    }

    public static List<String> getCommandsList(String[] args) {
        getCommands();

        return getListOfStringsFromIterableMatchingLastWord(args, namesList);
    }

    @Override
    public String getCommandName() {
        return "primal";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/primal <" + getCommands() + ">";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        boolean valid = false;
        if (args.length > 0) {
            if (commands.containsKey(args[0])) {
                ISubCommand command = commands.get(args[0]);
                if (command.canHandleCommand(sender)) {
                    command.handleCommand(sender, Utils.removeFirst(args));
                    valid = true;
                }
            }
        }
        if (!valid) {
            sendCommandUsage(sender);
        }
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (commands.containsKey(args[0])) {
                ISubCommand command = commands.get(args[0]);
                if (command.canHandleCommand(sender)) {
                    return command.addTabCompletionOptions(sender, Utils.removeFirst(args));
                }
            }
        }
        return getCommandsList(args);
    }

    public void sendCommandUsage(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
    }
}
