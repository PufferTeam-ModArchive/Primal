package net.pufferlab.primal.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.network.packets.PacketPlayerData;
import net.pufferlab.primal.utils.TemperatureUtils;
import net.pufferlab.primal.world.GlobalTickingData;

public class CommandTemperature extends CommandSub {

    public static String[] arguments = new String[] { "", "debug" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = CommandBase.getCommandSenderAsPlayer(sender);
        if (args.length == 1) {
            if (args[0].equals("debug")) {
                PlayerData data = PlayerData.get(player);
                boolean state = !data.temperatureDebug;
                data.setTemperatureDebug(state);
                sender.addChatMessage(new ChatComponentText(Utils.getStateTooltip(state, "Enabled", "Disabled")));
                updatePacket(player, data);
            }
        }
        ItemStack stack = player.getHeldItem();
        if (stack == null) return;
        if (stack.getItem() == null) return;
        if (!TemperatureUtils.hasImpl(stack)) return;
        long currentTick = GlobalTickingData.getTickTime(player.getEntityWorld());
        NBTTagCompound tag = stack.getTagCompound();

        sender.addChatMessage(
            new ChatComponentText(
                "Temperature: " + Constants.gray
                    + TemperatureUtils.getInterpolatedTemperature(currentTick, tag)
                    + Constants.white
                    + "C"));
        sender.addChatMessage(new ChatComponentText("Advanced Info :"));
        sender.addChatMessage(
            new ChatComponentText(
                "Last-Temperature: " + Constants.gray
                    + TemperatureUtils.getTemperatureFromNBT(tag)
                    + Constants.white
                    + "C"));
        sender.addChatMessage(
            new ChatComponentText("Last-WorldTime: " + Constants.gray + TemperatureUtils.getWorldTimeFromNBT(tag)));
        sender.addChatMessage(
            new ChatComponentText("Multiplier: " + Constants.gray + TemperatureUtils.getMultiplierFromNBT(tag)));
        sender.addChatMessage(
            new ChatComponentText(
                "Max-Temperature: " + Constants.gray + TemperatureUtils.getMaxTemperatureFromNBT(tag)));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Arrays.asList(arguments) : null;
    }

    public void updatePacket(EntityPlayer player, PlayerData data) {
        if (!player.worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketPlayerData(player, data));
        }
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
