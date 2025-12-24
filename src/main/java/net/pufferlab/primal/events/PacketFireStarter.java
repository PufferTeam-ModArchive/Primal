package net.pufferlab.primal.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.tileentities.TileEntityInventory;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketFireStarter implements IMessage, IMessageHandler<PacketFireStarter, IMessage> {

    private int playerEntityId, x, y, z;
    private float hitX, hitY, hitZ;
    private boolean success;

    public PacketFireStarter() {}

    public PacketFireStarter(EntityPlayer player, int x, int y, int z, float hitX, float hitY, float hitZ,
        boolean success) {
        this.playerEntityId = player.getEntityId();
        this.x = x;
        this.y = y;
        this.z = z;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.success = success;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerEntityId = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.hitX = buf.readFloat();
        this.hitY = buf.readFloat();
        this.hitZ = buf.readFloat();
        this.success = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerEntityId);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeFloat(this.hitX);
        buf.writeFloat(this.hitY);
        buf.writeFloat(this.hitZ);
        buf.writeBoolean(this.success);
    }

    @Override
    public IMessage onMessage(PacketFireStarter msg, MessageContext ctx) {
        EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(msg.playerEntityId);
        World world = player.worldObj;
        world.spawnParticle("smoke", msg.hitX, msg.hitY, msg.hitZ, 0.0F, 0.0F, 0.0F);
        if (msg.success) {
            TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);
            if (te instanceof TileEntityInventory tef) {
                if (tef.canBeFired()) {
                    tef.setFired(true);
                }
            }
        }
        return null;
    }
}
