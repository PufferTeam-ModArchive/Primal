package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.PositionUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.structures.StructureFile;

import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TObjectLongHashMap;

public class CommandStructure extends CommandSub {

    public static String[] arguments = new String[] { "", "save", "load", "pos1", "pos2" };

    public static final TObjectLongMap<String> pos1Map = new TObjectLongHashMap<>();
    public static final TObjectLongMap<String> pos2Map = new TObjectLongHashMap<>();

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        String name = sender.getCommandSenderName();

        if (sender instanceof EntityPlayer player) {
            World world = player.worldObj;
            if (args.length > 0) {
                if (args[0].equals("load")) {
                    if (args.length > 1) {
                        String structureName = args[1];

                        ChunkCoordinates coords = sender.getPlayerCoordinates();
                        int x = coords.posX;
                        int y = coords.posY;
                        int z = coords.posZ;

                        StructureFile.loadStructure(structureName, x, y, z, world);
                        sendChatMessage(sender, "Loaded structure " + structureName + " from structure folder");
                    }
                }
                if (args[0].equals("save")) {
                    if (args.length > 1) {
                        String structureName = args[1];
                        long packedCoord1 = pos1Map.get(name);
                        int x1 = PositionUtils.unpackX(packedCoord1);
                        int y1 = PositionUtils.unpackY(packedCoord1);
                        int z1 = PositionUtils.unpackZ(packedCoord1);
                        long packedCoord2 = pos2Map.get(name);
                        int x2 = PositionUtils.unpackX(packedCoord2);
                        int y2 = PositionUtils.unpackY(packedCoord2);
                        int z2 = PositionUtils.unpackZ(packedCoord2);

                        StructureFile.saveStructure(structureName, x1, y1, z1, x2, y2, z2, world);
                        sendChatMessage(sender, "Saved structure " + structureName + " in structure folder");
                    }
                }
                if (args[0].equals("pos1") || args[0].equals("pos2")) {
                    MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(world, player, false);
                    if (mop != null) {
                        int blockX = mop.blockX;
                        int blockY = mop.blockY;
                        int blockZ = mop.blockZ;
                        long packedCoord = PositionUtils.packCoord(blockX, blockY, blockZ);
                        if (args[0].equals("pos1")) {
                            pos1Map.put(name, packedCoord);
                            sendChatMessage(
                                sender,
                                "Set Position 1 to X:" + blockX + ", Y:" + blockY + ", Z:" + blockZ);
                        }
                        if (args[0].equals("pos2")) {
                            pos2Map.put(name, packedCoord);
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
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Utils.asList(arguments) : null;
    }

    @Override
    public String getCommandName() {
        return "structure";
    }
}
