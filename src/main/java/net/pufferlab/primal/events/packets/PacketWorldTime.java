package net.pufferlab.primal.events.packets;

import net.pufferlab.primal.events.ticks.GlobalTickingData;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketWorldTime implements IMessage, IMessageHandler<PacketWorldTime, IMessage> {

    private long tickTime;

    public PacketWorldTime() {}

    public PacketWorldTime(long tickTime) {
        this.tickTime = tickTime;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tickTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.tickTime);
    }

    @Override
    public IMessage onMessage(PacketWorldTime msg, MessageContext ctx) {
        GlobalTickingData.clientTickTime = msg.tickTime;
        return null;
    }
}
