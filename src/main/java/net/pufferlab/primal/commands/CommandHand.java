package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.pufferlab.primal.utils.ItemUtils;

public class CommandHand extends CommandSub {

    public static String[] arguments = new String[] { "", "" };

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) return arguments;
        return null;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer player) {
            ItemStack stack = player.getHeldItem();
            if (stack != null) {
                String name = ItemUtils.getName(stack) + ":" + stack.getItemDamage();
                String nbt = null;
                if (stack.stackTagCompound != null) {
                    nbt = stack.stackTagCompound.toString();
                }
                sendChatMessage(sender, EnumChatFormatting.WHITE + "Item in hand:");
                sendChatMessage(sender, EnumChatFormatting.GRAY + "-" + EnumChatFormatting.GREEN + "'" + name + "'");
                if (nbt != null) {
                    sendChatMessage(sender, EnumChatFormatting.GRAY + "-" + EnumChatFormatting.AQUA + nbt);
                }
                List<String> oreDicts = ItemUtils.getOres(stack);
                for (String oreDict : oreDicts) {
                    sendChatMessage(
                        sender,
                        EnumChatFormatting.GRAY + "-" + EnumChatFormatting.YELLOW + "'" + oreDict + "'");
                }
            }
        }
    }

    @Override
    public String getCommandName() {
        return "hand";
    }

    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
