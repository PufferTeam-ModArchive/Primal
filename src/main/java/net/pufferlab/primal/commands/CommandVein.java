package net.pufferlab.primal.commands;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.OreType;
import net.pufferlab.primal.world.gen.VeinMath;

public class CommandVein extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        ChunkCoordinates c = sender.getPlayerCoordinates();

        int x = c.posX;
        int y = c.posY;
        int z = c.posZ;
        if (args[0].equals("sphere")) {
            Block block = OreType.getOre(Constants.oreTypes, args[1]);
            int radius = Integer.parseInt(args[2]);
            VeinMath.sphereImperfect(world, x, y, z, block, radius);
        }
        if (args[0].equals("oval")) {
            Block block = OreType.getOre(Constants.oreTypes, args[1]);
            if (args.length > 2) {
                int ox = Integer.parseInt(args[2]);
                int oy = Integer.parseInt(args[3]);
                int oz = Integer.parseInt(args[4]);
                VeinMath.ovalImperfect(world, x, y, z, block, ox, oy, oz);
            }
        }
    }

    @Override
    public String getCommandName() {
        return "genvein";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
