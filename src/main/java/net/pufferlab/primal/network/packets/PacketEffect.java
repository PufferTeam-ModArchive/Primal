package net.pufferlab.primal.network.packets;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketEffect implements IMessage, IMessageHandler<PacketEffect, IMessage> {

    private int x, y, z;
    private int blockID, metadata;

    public PacketEffect() {}

    public PacketEffect(int x, int y, int z, Block block, int meta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = Block.getIdFromBlock(block);
        this.metadata = meta;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        blockID = buf.readInt();
        metadata = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(blockID);
        buf.writeInt(metadata);
    }

    @Override
    public IMessage onMessage(PacketEffect msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        if (world == null) return null;

        Block block = Block.getBlockById(msg.blockID);
        Primal.proxy.effect.addBlockDestroyEffects(world, msg.x, msg.y, msg.z, block, msg.metadata);
        return null;
    }

}
