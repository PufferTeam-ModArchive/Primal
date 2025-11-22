package net.pufferlab.primal.events;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.inventory.container.ContainerKnapping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketKnappingClick implements IMessage, IMessageHandler<PacketKnappingClick, IMessage> {

    private int x, y;

    public PacketKnappingClick() {}

    public PacketKnappingClick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readByte();
        y = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(x);
        buf.writeByte(y);
    }

    @Override
    public IMessage onMessage(PacketKnappingClick msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (player.openContainer instanceof ContainerKnapping) {
            ((ContainerKnapping) player.openContainer).clickedOnSlot(msg.x, msg.y);
        }
        return null;
    }
}
