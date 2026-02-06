package net.pufferlab.primal.network.packets;

import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketAnvilPlan implements IMessage, IMessageHandler<PacketAnvilPlan, IMessage> {

    private int x, y, z;
    String recipeID;

    public PacketAnvilPlan() {}

    public PacketAnvilPlan(TileEntityAnvil tile, String recipeID) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
        this.recipeID = recipeID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        recipeID = IOUtils.readString(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        IOUtils.writeString(buf, recipeID);
    }

    @Override
    public IMessage onMessage(PacketAnvilPlan msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        TileEntity te = player.worldObj.getTileEntity(msg.x, msg.y, msg.z);
        if (te instanceof TileEntityAnvil tef) {
            tef.addInventorySlotContentsUpdate(slotInput, player);
            tef.onRecipeChange(msg.recipeID);
        }

        return null;
    }

}
