package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class CommandStrata extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        ChunkCoordinates c = sender.getPlayerCoordinates();

        if (args.length > 0) {
            if (args[0].equals("debug")) {
                StoneType[][] layers = Constants.stoneTypesLayer;
                for (int x = -32; x <= 32; x++) {
                    for (int y = 0; y <= Constants.maxHeight; y++) {
                        for (int z = -32; z <= 32; z++) {

                            int bx = c.posX + x;
                            int bz = c.posZ + z;

                            world.setBlockToAir(bx, y, bz);
                        }
                    }
                }
                for (int i = 0; i < layers.length; i++) {
                    StoneType[] stones = layers[i];
                    if (stones == null) continue;
                    for (int j = 0; j < stones.length; j++) {
                        int meta = StoneType.getMeta(Constants.stoneTypes, stones[j]);

                        world.setBlock(c.posX, i, c.posZ + j, Registry.stone, meta, 2);
                    }

                }
            }
        }
    }

    @Override
    public String getCommandName() {
        return "genstrata";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

}
