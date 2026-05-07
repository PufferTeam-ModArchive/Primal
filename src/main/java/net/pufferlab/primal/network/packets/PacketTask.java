package net.pufferlab.primal.network.packets;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.world.SchedulerData;
import net.pufferlab.primal.world.Tasks;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketTask implements IMessage, IMessageHandler<PacketTask, IMessage> {

    private int inTime, worldId, x, y, z;
    Tasks task;
    Tasks.Type taskType;
    Block block;

    public PacketTask() {}

    public PacketTask(Tasks.Type taskType, int inTime, Block block, World world, int x, int y, int z, Tasks task) {
        this.taskType = taskType;
        this.inTime = inTime;
        this.worldId = world.provider.dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.task = task;
        this.block = block;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        taskType = Tasks.Type.getTask(buf.readByte());
        inTime = buf.readInt();
        worldId = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        task = Tasks.getTask(buf.readInt());
        block = Block.getBlockById(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(Tasks.Type.getID(taskType));
        buf.writeInt(inTime);
        buf.writeInt(worldId);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(Tasks.getID(task));
        buf.writeInt(Block.getIdFromBlock(block));
    }

    @Override
    public IMessage onMessage(PacketTask msg, MessageContext ctx) {
        World world = Primal.proxy.getWorldFromID(msg.worldId);
        SchedulerData.addScheduledTask(msg.taskType, msg.inTime, msg.block, world, msg.x, msg.y, msg.z, msg.task);
        return null;
    }

}
