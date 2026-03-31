package net.pufferlab.primal.network.packets;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.world.SchedulerData;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketTask implements IMessage, IMessageHandler<PacketTask, IMessage> {

    private byte taskType;
    private int inTime, worldId, x, y, z, type, blockID;

    public PacketTask() {}

    public PacketTask(byte taskType, int inTime, Block block, World world, int x, int y, int z, int type) {
        this.taskType = taskType;
        this.inTime = inTime;
        this.worldId = world.provider.dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.blockID = Block.getIdFromBlock(block);

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        taskType = buf.readByte();
        inTime = buf.readInt();
        worldId = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        type = buf.readInt();
        blockID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(taskType);
        buf.writeInt(inTime);
        buf.writeInt(worldId);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(type);
        buf.writeInt(blockID);
    }

    @Override
    public IMessage onMessage(PacketTask msg, MessageContext ctx) {
        World world = Primal.proxy.getWorldFromID(msg.worldId);
        Block block = Block.getBlockById(msg.blockID);
        SchedulerData.addScheduledTask(msg.taskType, msg.inTime, block, world, msg.x, msg.y, msg.z, msg.type);
        return null;
    }

}
