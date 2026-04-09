package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.events.MotionHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketAxlePlace implements IMessage, IMessageHandler<PacketAxlePlace, IMessage> {

    private int side;

    public PacketAxlePlace() {}

    public PacketAxlePlace(int side) {
        this.side = side;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        side = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(side);
    }

    @Override
    public IMessage onMessage(PacketAxlePlace msg, MessageContext ctx) {
        final EntityPlayer player = Primal.proxy.getPlayer(ctx);
        MotionHandler.placeAxle(player, msg.side);
        return null;
    }
}
