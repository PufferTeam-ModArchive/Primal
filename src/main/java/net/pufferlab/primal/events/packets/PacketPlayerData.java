package net.pufferlab.primal.events.packets;

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
    private boolean temperatureDebug;

    public PacketPlayerData() {}

    public PacketPlayerData(EntityPlayer player, PlayerData data) {
        this.playerEntityId = player.getEntityId();
        this.temperatureDebug = data.temperatureDebug;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.temperatureDebug = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        buf.writeBoolean(this.temperatureDebug);
    }

    @Override
    public IMessage onMessage(PacketPlayerData msg, MessageContext ctx) {
        World world = Primal.proxy.getClientWorld();
        EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.playerEntityId);
        PlayerData data = PlayerData.get(player);
        data.temperatureDebug = msg.temperatureDebug;
        return null;
    }
}
