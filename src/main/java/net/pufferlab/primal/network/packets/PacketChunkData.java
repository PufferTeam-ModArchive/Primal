package net.pufferlab.primal.network.packets;

import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.renderer.RenderDebug;
import net.pufferlab.primal.world.ChunkDataManager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketChunkData implements IMessage, IMessageHandler<PacketChunkData, IMessage> {

    int dimensionID, x, z;

    public PacketChunkData() {}

    public PacketChunkData(World world, int x, int z) {
        this.x = x;
        this.z = z;
        this.dimensionID = world.provider.dimensionId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dimensionID = buf.readInt();
        this.x = buf.readInt();
        this.z = buf.readInt();

        ChunkDataManager manager = ChunkDataManager.getClientDataManager();
        manager.readFromBuffer(buf, this.x, this.z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.dimensionID);
        buf.writeInt(this.x);
        buf.writeInt(this.z);

        ChunkDataManager manager = ChunkDataManager.getDataManager(this.dimensionID);
        manager.writeToBuffer(buf, this.x, this.z);
    }

    @Override
    public IMessage onMessage(PacketChunkData msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);

        RenderDebug.receiveDataDebug(msg.x, msg.z);

        return null;
    }
}
