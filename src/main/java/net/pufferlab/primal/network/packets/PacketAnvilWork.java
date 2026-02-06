package net.pufferlab.primal.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.utils.ItemUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketAnvilWork implements IMessage, IMessageHandler<PacketAnvilWork, IMessage> {

    private int x, y, z, button;

    public PacketAnvilWork() {}

    public PacketAnvilWork(TileEntityAnvil tile, int button) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
        this.button = button;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        button = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(button);
    }

    @Override
    public IMessage onMessage(PacketAnvilWork msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        TileEntity te = player.worldObj.getTileEntity(msg.x, msg.y, msg.z);
        ItemStack heldItem = player.getHeldItem();
        if (ItemUtils.isHammerTool(heldItem)) {
            if (player.worldObj.rand.nextInt(3) == 0) {
                heldItem.damageItem(1, player);
            }
        }
        Primal.proxy.sendPacketToClient(new PacketSwingArm(player));
        if (te instanceof TileEntityAnvil tef) {
            tef.onWorkButtonClick(msg.button);
        }

        return null;
    }

}
