package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.RecipeUtils;
import net.pufferlab.primal.utils.Utils;

public class CommandHeat extends CommandSub {

    public static String[] arguments = new String[] { "", "debug" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = CommandBase.getCommandSenderAsPlayer(sender);
        if (args.length == 1) {
            if (args[0].equals("debug")) {
                PlayerData data = PlayerData.get(player);
                boolean state = !data.temperatureDebug;
                data.setTemperatureDebug(state);
                sender.addChatMessage(new ChatComponentText(RecipeUtils.getStateTooltip(state, "Enabled", "Disabled")));
                Primal.proxy.packet.sendPlayerData(player, data);
            }
        }
        ItemStack stack = player.getHeldItem();
        if (stack == null) return;
        if (stack.getItem() == null) return;
        if (!HeatUtils.hasImpl(stack)) return;
        NBTTagCompound tag = stack.getTagCompound();

        sender.addChatMessage(new ChatComponentText(Utils.translate("heat." + Primal.MODID + ".debug.info.desc")));
        sender.addChatMessage(
            new ChatComponentText(
                Utils.translate(
                    "heat." + Primal.MODID + ".debug.last-temperature.desc",
                    HeatUtils.getTemperatureFromNBT(tag))));
        sender.addChatMessage(
            new ChatComponentText(
                Utils.translate("heat." + Primal.MODID + ".debug.worldtime.desc", HeatUtils.getWorldTimeFromNBT(tag))));
        sender.addChatMessage(
            new ChatComponentText(
                Utils.translate("heat." + Primal.MODID + ".debug.modifier.desc", HeatUtils.getMultiplierFromNBT(tag))));
        sender.addChatMessage(
            new ChatComponentText(
                Utils.translate(
                    "heat." + Primal.MODID + ".debug.max-temperature.desc",
                    HeatUtils.getMaxTemperatureFromNBT(tag))));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Utils.asList(arguments) : null;
    }

    @Override
    public String getCommandName() {
        return "heat";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
