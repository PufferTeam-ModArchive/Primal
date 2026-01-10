package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.pufferlab.primal.events.ticks.WorldTickingData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.TemperatureUtils;

public class CommandTemperature extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        String username = sender.getCommandSenderName();
        EntityPlayerMP entityplayermp = getPlayer(sender, username);
        ItemStack stack = entityplayermp.getHeldItem();
        if (stack == null) return;
        if (stack.getItem() == null) return;
        if (!(stack.getItem() instanceof IHeatableItem)) return;
        long currentTick = WorldTickingData.getTickTime(entityplayermp.getEntityWorld());
        NBTTagCompound tag = stack.getTagCompound();

        sender.addChatMessage(
            new ChatComponentText(
                "Temperature: " + EnumChatFormatting.GRAY
                    + TemperatureUtils.getInterpolatedTemperature(currentTick, tag)
                    + EnumChatFormatting.WHITE
                    + "C"));
        sender.addChatMessage(new ChatComponentText("Advanced Info :"));
        sender.addChatMessage(
            new ChatComponentText(
                "Last-Temperature: " + EnumChatFormatting.GRAY
                    + TemperatureUtils.getTemperatureFromNBT(tag)
                    + EnumChatFormatting.WHITE
                    + "C"));
        sender.addChatMessage(
            new ChatComponentText(
                "Last-WorldTime: " + EnumChatFormatting.GRAY + TemperatureUtils.getWorldTimeFromNBT(tag)));
        sender.addChatMessage(
            new ChatComponentText(
                "Multiplier: " + EnumChatFormatting.GRAY + TemperatureUtils.getMultiplierFromNBT(tag)));
        sender.addChatMessage(
            new ChatComponentText(
                "Max-Temperature: " + EnumChatFormatting.GRAY + TemperatureUtils.getMaxTemperatureFromNBT(tag)));
    }

    @Override
    public String getCommandName() {
        return "temp";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
