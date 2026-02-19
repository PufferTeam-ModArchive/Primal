package net.pufferlab.primal.network.packets;

import net.minecraft.world.World;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;

public class PacketCutMaterial implements IMessage, IMessageHandler<PacketCutMaterial, IMessage> {

    private int x, y, z, material, material2;

    public PacketCutMaterial() {}

    public PacketCutMaterial(int x, int y, int z, int material) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
    }

    public PacketCutMaterial(int x, int y, int z, int material, int material2) {
        this(x, y, z, material);
        this.material2 = material2;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        material = buf.readShort();
        material2 = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeShort(material);
        buf.writeShort(material2);
    }

    @Override
    public IMessage onMessage(PacketCutMaterial msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        Primal.proxy.packet.setMaterial(world, msg.x, msg.y, msg.z, msg.material, msg.material2);
        if (ctx.side == Side.SERVER) {
            Primal.proxy.packet.sendChunkUpdate(world);
        }
        return null;
    }

}
