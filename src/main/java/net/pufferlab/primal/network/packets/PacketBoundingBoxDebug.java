package net.pufferlab.primal.network.packets;

import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.client.renderer.RenderBounds;
import net.pufferlab.primal.utils.BoundingBox;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketBoundingBoxDebug implements IMessage, IMessageHandler<PacketBoundingBoxDebug, IMessage> {

    int id;
    public int type;
    private AxisAlignedBB bb;
    private BoundingBox bb2;

    public PacketBoundingBoxDebug() {}

    public PacketBoundingBoxDebug(int id, AxisAlignedBB bb) {
        this.id = id;
        this.bb = bb;
        this.type = 0;
    }

    public PacketBoundingBoxDebug(int id, BoundingBox bb) {
        this.id = id;
        this.bb2 = bb;
        this.type = 1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.type = buf.readByte();
        if (this.type == 0) {
            this.bb = IOUtils.readAABB(buf);
        }
        if (this.type == 1) {
            this.bb2 = IOUtils.readBB(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeByte(this.type);
        if (this.type == 0) {
            IOUtils.writeAABB(buf, this.bb);
        }
        if (this.type == 1) {
            IOUtils.writeBB(buf, this.bb2);
        }
    }

    @Override
    public IMessage onMessage(PacketBoundingBoxDebug msg, MessageContext ctx) {
        if (msg.type == 0) {
            RenderBounds.setTemporaryAABB(msg.id, msg.bb);
        }
        if (msg.type == 1) {
            RenderBounds.setTemporaryBB(msg.id, msg.bb2);
        }
        return null;
    }
}
