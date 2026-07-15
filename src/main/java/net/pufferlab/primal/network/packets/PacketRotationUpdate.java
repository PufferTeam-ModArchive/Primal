package net.pufferlab.primal.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.IMoving;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketRotationUpdate implements IMessage, IMessageHandler<PacketRotationUpdate, IMessage> {

    private int x, y, z, rotAxis;
    private float rotAngle, rotSpeed;

    public PacketRotationUpdate() {}

    public PacketRotationUpdate(IMoving tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
        this.rotAxis = tile.getMovingAxis();
        this.rotAngle = tile.getMovingAngle();
        this.rotSpeed = tile.getMovingSpeed();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        rotAxis = buf.readByte();
        rotAngle = buf.readFloat();
        rotSpeed = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(rotAxis);
        buf.writeFloat(rotAngle);
        buf.writeFloat(rotSpeed);
    }

    @Override
    public IMessage onMessage(PacketRotationUpdate message, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        if (world == null) return null;

        TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof IMoving tef) {
            tef.setMovingAxis(message.rotAxis);
            tef.setMovingAngle(message.rotAngle);
            tef.setMovingSpeed(message.rotSpeed);
        }
        return null;
    }

}
