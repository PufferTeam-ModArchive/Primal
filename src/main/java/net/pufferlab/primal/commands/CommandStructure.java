package net.pufferlab.primal.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.world.structures.StructureFile;

public class CommandStructure extends CommandSub {

    public static String[] arguments = { "", "save", "load", "pos1", "pos2" };

    @Override
    public String[] getSubArgs(String[] args) {
        if (args.length == 1) return arguments;
        return null;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer player) {
            World world = player.worldObj;
            PlayerData data = PlayerData.get(player);
            if (args.length > 0) {
                if (args[0].equals("load")) {
                    if (args.length > 1) {
                        String structureName = args[1];
                        int facing = 0;
                        if (args.length > 2) {
                            facing = Integer.parseInt(args[2]);
                        }

                        ChunkCoordinates coords = sender.getPlayerCoordinates();
                        int x = coords.posX;
                        int y = coords.posY;
                        int z = coords.posZ;

                        StructureFile.loadStructure(structureName, x, y, z, world, facing);
                        sendChatMessage(sender, "Loaded structure " + structureName + " from structure folder");
                    }
                }
                if (args[0].equals("save")) {
                    if (args.length > 1) {
                        String structureName = args[1];
                        if (data.hasValidSelection()) {
                            long packedCoord1 = data.getSelectPos1();
                            int x1 = HashUtils.unpackX(packedCoord1);
                            int y1 = HashUtils.unpackY(packedCoord1);
                            int z1 = HashUtils.unpackZ(packedCoord1);
                            long packedCoord2 = data.getSelectPos2();
                            int x2 = HashUtils.unpackX(packedCoord2);
                            int y2 = HashUtils.unpackY(packedCoord2);
                            int z2 = HashUtils.unpackZ(packedCoord2);

                            StructureFile.saveStructure(structureName, x1, y1, z1, x2, y2, z2, world);
                            sendChatMessage(sender, "Saved structure " + structureName + " in structure folder");
                        } else {
                            sendChatMessage(
                                sender,
                                "Selection is not valid, set the pos1 and po2 to valid coordinates");
                        }
                    }
                }
                if (args[0].equals("pos1") || args[0].equals("pos2")) {
                    MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(world, player, false);
                    if (mop != null) {
                        int blockX = mop.blockX;
                        int blockY = mop.blockY;
                        int blockZ = mop.blockZ;
                        long packedCoord = HashUtils.packCoord(blockX, blockY, blockZ);
                        if (args[0].equals("pos1")) {
                            data.setSelectPos1(packedCoord);
                            sendChatMessage(
                                sender,
                                "Set Position 1 to X:" + blockX + ", Y:" + blockY + ", Z:" + blockZ);
                        }
                        if (args[0].equals("pos2")) {
                            data.setSelectPos2(packedCoord);
                            sendChatMessage(
                                sender,
                                "Set Position 2 to X:" + blockX + ", Y:" + blockY + ", Z:" + blockZ);
                        }
                    }
                }
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandName() {
        return "structure";
    }
}
