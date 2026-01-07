package net.pufferlab.primal.events.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.events.PitKilnHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketPitKilnPlace implements IMessage, IMessageHandler<PacketPitKilnPlace, IMessage> {

    public PacketPitKilnPlace() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(PacketPitKilnPlace msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        PitKilnHandler.placePitKiln(player);
        player.inventoryContainer.detectAndSendChanges();
        return null;
    }
}
