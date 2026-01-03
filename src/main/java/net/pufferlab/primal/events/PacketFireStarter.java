package net.pufferlab.primal.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityInventory;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketFireStarter implements IMessage, IMessageHandler<PacketFireStarter, IMessage> {

    private int playerEntityId;
    private boolean success;

    public PacketFireStarter() {}

    public PacketFireStarter(EntityPlayer player, boolean success) {
        this.playerEntityId = player.getEntityId();
        this.success = success;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.success = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        buf.writeBoolean(this.success);
    }

    @Override
    public IMessage onMessage(PacketFireStarter msg, MessageContext ctx) {
        World world = Primal.proxy.getClientWorld();
        EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.playerEntityId);
        MovingObjectPosition mop = Utils.getMovingObjectPositionFromPlayer(world, player, false);
        if (mop != null) {
            world.spawnParticle("smoke", mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 0.0F, 0.0F, 0.0F);
            if (msg.success) {
                TileEntity te = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                if (te instanceof TileEntityInventory tef) {
                    if (tef.canBeFired()) {
                        tef.setFired(true);
                    }
                }
            }
        }
        return null;
    }
}
