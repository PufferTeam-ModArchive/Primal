package net.pufferlab.primal.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketSwingArm implements IMessage, IMessageHandler<PacketSwingArm, IMessage> {

    private int playerEntityId;

    public PacketSwingArm() {}

    public PacketSwingArm(EntityPlayer player) {
        this.playerEntityId = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
    }

    @Override
    public IMessage onMessage(PacketSwingArm msg, MessageContext ctx) {
        EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(msg.playerEntityId);
        if (player != null) {
            player.swingItem();
        }
        return null;
    }
}
