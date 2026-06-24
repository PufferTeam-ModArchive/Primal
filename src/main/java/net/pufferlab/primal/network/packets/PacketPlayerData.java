package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketPlayerData implements IMessage, IMessageHandler<PacketPlayerData, IMessage> {

    private int playerEntityId;
    private PlayerData data;

    public PacketPlayerData() {}

    public PacketPlayerData(EntityPlayer player, PlayerData data) {
        this.playerEntityId = player.getEntityId();
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.data = PlayerData.dataFromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        PlayerData.dataToBytes(buf, this.data);
    }

    @Override
    public IMessage onMessage(PacketPlayerData msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.playerEntityId);
        PlayerData data = PlayerData.get(player);
        PlayerData.syncPlayerData(msg.data, data);
        return null;
    }
}
