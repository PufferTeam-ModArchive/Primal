package net.pufferlab.primal.commands;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.tileentities.TileEntityMoving;
import net.pufferlab.primal.utils.BlockUtils;

public class CommandMovingBlock extends CommandSub {

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        Block block = BlockUtils.getBlockFromName(args[0]);

        MovingObjectPosition mop = BlockUtils
            .getMovingObjectPositionFromPlayer(sender.getEntityWorld(), (EntityPlayer) sender, false);
        if (mop != null) {
            TileEntity te = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if (te instanceof TileEntityMoving tef) {}
        }
    }

    @Override
    public String getCommandName() {
        return "modify_moving_block";
    }
}
