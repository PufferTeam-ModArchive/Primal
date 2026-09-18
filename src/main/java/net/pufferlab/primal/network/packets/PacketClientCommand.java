package net.pufferlab.primal.network.packets;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.commands.CommandPrimal;
import net.pufferlab.primal.commands.ISubCommand;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketClientCommand implements IMessage, IMessageHandler<PacketClientCommand, IMessage> {

    private ISubCommand command;
    private String[] args;
    private int playerEntityId;

    public PacketClientCommand() {}

    public PacketClientCommand(ISubCommand command, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer player) {
            this.playerEntityId = player.getEntityId();
        }
        this.command = command;
        this.args = args;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.command = CommandPrimal.getSubCommand(IOUtils.readString(buf));
        this.args = IOUtils.readStringArray(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        IOUtils.writeString(buf, this.command.getCommandName());
        IOUtils.writeStringArray(buf, this.args);
    }

    @Override
    public IMessage onMessage(PacketClientCommand msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.playerEntityId);

        msg.command.handleClientCommand(player, msg.args);

        return null;
    }
}
