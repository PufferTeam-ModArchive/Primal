package net.pufferlab.primal.events;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.IMotion;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketSpeedUpdate implements IMessage, IMessageHandler<PacketSpeedUpdate, IMessage> {

    private int x, y, z;
    private float speed, speedModifier;
    private boolean hasNetwork, hasOffset;

    public PacketSpeedUpdate() {}

    public PacketSpeedUpdate(IMotion tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
        this.speed = tile.getSpeed();
        this.speedModifier = tile.getSpeedModifier();
        this.hasNetwork = tile.hasNetwork();
        this.hasOffset = tile.hasOffset();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        speed = buf.readFloat();
        speedModifier = buf.readFloat();
        hasNetwork = buf.readBoolean();
        hasOffset = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeFloat(speed);
        buf.writeFloat(speedModifier);
        buf.writeBoolean(hasNetwork);
        buf.writeBoolean(hasOffset);
    }

    @Override
    public IMessage onMessage(PacketSpeedUpdate message, MessageContext ctx) {
        World world = Primal.proxy.getClientWorld();
        if (world == null) return null;

        TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof IMotion tef) {
            tef.setSpeed(message.speed);
            tef.setSpeedModifier(message.speedModifier);
            tef.setHasNetwork(message.hasNetwork);
            tef.setHasOffset(message.hasOffset);
        }
        return null;
    }

}
