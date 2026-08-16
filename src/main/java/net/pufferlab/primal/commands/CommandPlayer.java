package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.pufferlab.primal.Primal;

public class CommandPlayer extends CommandSub {

    public static String[] arguments = { "", "fly_speed", "walk_speed", "invulnerable" };

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) return this.getPlayers();
        if (args.length == 2) return arguments;
        return null;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("commands." + Primal.MODID + ".player.usage", new Object[0]);
        } else {
            EntityPlayerMP player = getPlayer(sender, args[0]);
            int multiplier = 1;

            if (args.length == 3) {
                multiplier = Integer.parseInt(args[2]);

                if (args[1].equals("fly_speed")) {
                    player.capabilities.setFlySpeed(0.05F * (float) multiplier);
                } else if (args[1].equals("walk_speed")) {
                    player.capabilities.setPlayerWalkSpeed(0.1F * (float) multiplier);
                } else if (args[1].equals("invulnerable")) {
                    player.capabilities.disableDamage = !player.capabilities.disableDamage;
                }
                player.sendPlayerAbilities();
            }

        }
    }

    @Override
    public String getCommandName() {
        return "player";
    }

    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
