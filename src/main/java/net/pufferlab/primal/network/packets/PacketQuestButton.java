package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.utils.QuestType;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketQuestButton implements IMessage, IMessageHandler<PacketQuestButton, IMessage> {

    String questName;

    public PacketQuestButton() {}

    public PacketQuestButton(QuestType questType) {
        this.questName = questType.name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.questName = IOUtils.readString(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        IOUtils.writeString(buf, questName);
    }

    @Override
    public IMessage onMessage(PacketQuestButton msg, MessageContext ctx) {
        EntityPlayer player = Primal.proxy.getPlayer(ctx);
        QuestType.claimQuest(player, QuestType.getQuest(msg.questName));
        return null;
    }
}
