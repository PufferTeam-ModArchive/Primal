package net.pufferlab.primal.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.ITile;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketTileDataUpdate implements IMessage, IMessageHandler<PacketTileDataUpdate, IMessage> {

    private int x, y, z;
    private byte[] bytes;

    public PacketTileDataUpdate() {}

    public PacketTileDataUpdate(ITile tile) {
        this.x = tile.x();
        this.y = tile.y();
        this.z = tile.z();

        ByteBuf buf = IOUtils.getBuffer();
        tile.writeToBuffer(buf);
        bytes = IOUtils.getBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        bytes = new byte[buf.readInt()];
        if (bytes.length > 0) {
            buf.readBytes(bytes);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(bytes.length);
        if (bytes.length > 0) {
            buf.writeBytes(bytes);
        }
    }

    @Override
    public IMessage onMessage(PacketTileDataUpdate message, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        if (world == null) return null;

        TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof ITile tef) {
            if (message.bytes.length > 0) {
                ByteBuf buf = IOUtils.getBufferFromBytes(message.bytes);
                tef.readFromBuffer(buf);
            }
        }
        return null;
    }

}
