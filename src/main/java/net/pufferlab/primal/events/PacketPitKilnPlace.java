package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockPitKiln;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketPitKilnPlace implements IMessage, IMessageHandler<PacketPitKilnPlace, IMessage> {

    public PacketPitKilnPlace() {}

    public PacketPitKilnPlace(int x, int y) {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(PacketPitKilnPlace msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        MovingObjectPosition mop = Utils.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
        if (mop != null) {
            int x = Utils.getBlockX(mop.sideHit, mop.blockX);
            int y = Utils.getBlockY(mop.sideHit, mop.blockY);
            int z = Utils.getBlockZ(mop.sideHit, mop.blockZ);
            player.worldObj.setBlock(x, y, z, Registry.pit_kiln, 0, 2);
            float hitX = (float) (mop.hitVec.xCoord - x);
            float hitY = (float) (mop.hitVec.yCoord - y);
            float hitZ = (float) (mop.hitVec.zCoord - z);
            Block actualBlock2 = player.worldObj.getBlock(x, y, z);
            if (actualBlock2 instanceof BlockPitKiln) {
                actualBlock2.onBlockActivated(player.worldObj, x, y, z, player, mop.sideHit, hitX, hitY, hitZ);
            }
        }
        return null;
    }
}
