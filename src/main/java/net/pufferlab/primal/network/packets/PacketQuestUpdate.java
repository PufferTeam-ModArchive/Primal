package net.pufferlab.primal.network.packets;

import net.pufferlab.primal.client.gui.GuiQuest;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketQuestUpdate implements IMessage, IMessageHandler<PacketQuestUpdate, IMessage> {

    public PacketQuestUpdate() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(PacketQuestUpdate msg, MessageContext ctx) {
        GuiQuest.updateRendering();
        return null;
    }
}
