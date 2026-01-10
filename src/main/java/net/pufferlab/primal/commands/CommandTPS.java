package net.pufferlab.primal.commands;

import java.text.DecimalFormat;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;

public class CommandTPS extends CommandSub {

    private static DecimalFormat floatfmt = new DecimalFormat("##0.00");

    @Override
    public String getCommandName() {
        return "tps";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        double tps = getTps(null);

        sender.addChatMessage(new ChatComponentText("TPS: " + floatfmt.format(tps)));
    }

    private double getTickTimeSum(long[] times) {

        long timesum = 0L;
        if (times == null) {
            return 0.0D;
        }
        for (int i = 0; i < times.length; i++) {
            timesum += times[i];
        }

        return (double) timesum / times.length;
    }

    private double getTickMs(World world) {

        return getTickTimeSum(
            world == null ? Primal.proxy.getServer().tickTimeArray
                : (long[]) Primal.proxy.getServer().worldTickTimes.get(0))
            * 1.0E-006D;
    }

    private double getTps(World world) {

        double tps = 1000.0D / getTickMs(world);
        return Math.min(tps, 20.0D);
    }
}
