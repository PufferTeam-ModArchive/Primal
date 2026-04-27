package net.pufferlab.primal.network.packets;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketEffect implements IMessage, IMessageHandler<PacketEffect, IMessage> {

    private int x, y, z, side;
    private int blockID, metadata;
    private boolean bounded;
    private List<AxisAlignedBB> bounds;

    public PacketEffect() {}

    public PacketEffect(int x, int y, int z, Block block, int meta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = Block.getIdFromBlock(block);
        this.metadata = meta;
        this.side = -1;
    }

    public PacketEffect(int x, int y, int z, Block block, int meta, int side, List<AxisAlignedBB> list) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = Block.getIdFromBlock(block);
        this.metadata = meta;
        this.side = side;
        this.bounds = list;
        this.bounded = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        blockID = buf.readInt();
        metadata = buf.readInt();
        side = buf.readInt();
        bounded = buf.readBoolean();
        if (bounded) {
            bounds = IOUtils.readBBList(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(blockID);
        buf.writeInt(metadata);
        buf.writeInt(side);
        buf.writeBoolean(bounded);
        if (bounded) {
            IOUtils.writeBBList(buf, bounds);
        }
    }

    @Override
    public IMessage onMessage(PacketEffect msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        if (world == null) return null;

        Block block = Block.getBlockById(msg.blockID);
        int side = msg.side;
        if (msg.bounded) {
            Primal.proxy.renderFX(world, msg.x, msg.y, msg.z, block, msg.metadata, side, msg.bounds);
        } else {
            Primal.proxy.renderFX(world, msg.x, msg.y, msg.z, block, msg.metadata, side);
        }
        return null;
    }

}
