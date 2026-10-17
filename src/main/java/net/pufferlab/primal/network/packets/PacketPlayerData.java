package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketPlayerData implements IMessage, IMessageHandler<PacketPlayerData, IMessage> {

    private int playerEntityId;
    public byte[] bytes;

    public PacketPlayerData() {}

    public PacketPlayerData(EntityPlayer player, PlayerData data) {
        this.playerEntityId = player.getEntityId();

        ByteBuf buf = IOUtils.getBuffer();
        data.writeToBuffer(buf);
        bytes = IOUtils.getBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.bytes = IOUtils.readByteArray(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        IOUtils.writeByteArray(buf, bytes);
    }

    @Override
    public IMessage onMessage(PacketPlayerData msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.playerEntityId);
        PlayerData data = PlayerData.get(player);
        if (data != null) {
            if (msg.bytes.length > 0) {
                ByteBuf buf = IOUtils.getBuffer(msg.bytes);
                data.readFromBuffer(buf);
            }
        }
        return null;
    }
}
