package net.pufferlab.primal.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketCrucibleUpdate implements IMessage, IMessageHandler<PacketCrucibleUpdate, IMessage> {

    private int x, y, z;

    public PacketCrucibleUpdate() {}

    public PacketCrucibleUpdate(TileEntityCrucible tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(PacketCrucibleUpdate message, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        if (world == null) return null;

        TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof TileEntityCrucible tef) {
            tef.updateMeltCache();
        }
        return null;
    }

}
