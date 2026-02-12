package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.tileentities.TileEntityCut;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketCutMaterial implements IMessage, IMessageHandler<PacketCutMaterial, IMessage> {

    private int x, y, z, material;

    public PacketCutMaterial() {}

    public PacketCutMaterial(int x, int y, int z, int material) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        material = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(material);
    }

    @Override
    public IMessage onMessage(PacketCutMaterial msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        TileEntity te = player.worldObj.getTileEntity(msg.x, msg.y, msg.z);
        if (te instanceof TileEntityCut tef) {
            tef.setMaterialMeta(msg.material);
        }
        return null;
    }

}
