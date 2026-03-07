package net.pufferlab.primal.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityGenerator;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketSpeedButton implements IMessage, IMessageHandler<PacketSpeedButton, IMessage> {

    private int x, y, z;
    private boolean addSpeed, isShift;

    public PacketSpeedButton() {}

    public PacketSpeedButton(TileEntityGenerator tile, boolean addSpeed, boolean isShift) {
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.addSpeed = addSpeed;
        this.isShift = isShift;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        addSpeed = buf.readBoolean();
        isShift = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(addSpeed);
        buf.writeBoolean(isShift);
    }

    @Override
    public IMessage onMessage(PacketSpeedButton msg, MessageContext ctx) {
        World world = Primal.proxy.getWorld(ctx);
        TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);
        float modifier = 1.0F;
        if (msg.isShift) {
            modifier = 10F;
        }
        float modifier2 = 1.0F;
        if (!msg.addSpeed) {
            modifier2 = -1.0F;
        }
        if (te instanceof TileEntityGenerator tef) {
            float newSpeed = Utils.clamp(-50, 50, tef.getGeneratedSpeed() + (modifier * modifier2));
            tef.scheduleGeneratorUpdate(newSpeed);
        }
        return null;
    }
}
