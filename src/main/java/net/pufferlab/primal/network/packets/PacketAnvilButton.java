package net.pufferlab.primal.network.packets;

import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketAnvilButton implements IMessage, IMessageHandler<PacketAnvilButton, IMessage> {

    private int x, y, z, button;
    private boolean isWork;

    public PacketAnvilButton() {}

    public PacketAnvilButton(TileEntityAnvil tile, int button, boolean isWork) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.z = tile.getZ();
        this.button = button;
        this.isWork = isWork;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        button = buf.readInt();
        isWork = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(button);
        buf.writeBoolean(isWork);
    }

    @Override
    public IMessage onMessage(PacketAnvilButton msg, MessageContext ctx) {
        final EntityPlayer player = ctx.getServerHandler().playerEntity;
        TileEntity te = player.worldObj.getTileEntity(msg.x, msg.y, msg.z);
        if (msg.isWork) {
            ItemStack heldItem = player.getHeldItem();
            if (Utils.isHammerTool(heldItem)) {
                if (player.worldObj.rand.nextInt(3) == 0) {
                    heldItem.damageItem(1, player);
                }
            }
            Primal.proxy.sendPacketToClient(new PacketSwingArm(player));
            if (te instanceof TileEntityAnvil tef) {
                tef.onWorkButtonClick(msg.button);
            }
        } else {
            if (te instanceof TileEntityAnvil tef) {
                tef.recipeIndex = msg.button;
                tef.addInventorySlotContentsUpdate(slotInput, player);
                tef.onRecipeChange(msg.button);
            }
        }

        return null;
    }

}
