package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketChunkClient implements IMessage, IMessageHandler<PacketChunkClient, IMessage> {

    private int chunkX, chunkZ;

    public PacketChunkClient() {}

    public PacketChunkClient(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
    }

    @Override
    public IMessage onMessage(PacketChunkClient msg, MessageContext ctx) {
        final EntityPlayer player = Primal.proxy.getPlayer(ctx);
        if (player instanceof EntityPlayerMP playerMP) {
            Primal.proxy.packet.loadClientChunk(player.worldObj, msg.chunkX, msg.chunkZ, playerMP);
            Primal.proxy.packet.sendChunk(playerMP, (WorldServer) playerMP.worldObj, msg.chunkX, msg.chunkZ);
        }
        return null;
    }
}
