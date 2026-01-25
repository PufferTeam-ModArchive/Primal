package net.pufferlab.primal.commands;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.blocks.BlockMetaOre;

public class CommandClearBlocks extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        ChunkCoordinates c = sender.getPlayerCoordinates();

        if (args.length > 0) {
            if (args[0].equals("chunk")) {
                for (int x = -32; x <= 32; x++) {
                    for (int y = 0; y <= Constants.maxHeight; y++) {
                        for (int z = -32; z <= 32; z++) {

                            int bx = c.posX + x;
                            int bz = c.posZ + z;

                            Block block = world.getBlock(bx, y, bz);
                            if (block instanceof BlockMetaOre) continue;
                            world.setBlockToAir(bx, y, bz);
                        }
                    }
                }
            } else {
                int radius = Integer.parseInt(args[0]);
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {

                            int bx = c.posX + x;
                            int by = c.posY + y;
                            int bz = c.posZ + z;

                            if (by < 0 || by > 255) continue;

                            Block block = world.getBlock(bx, by, bz);
                            if (block instanceof BlockMetaOre) continue;
                            world.setBlockToAir(bx, by, bz);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getCommandName() {
        return "cleararea";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
