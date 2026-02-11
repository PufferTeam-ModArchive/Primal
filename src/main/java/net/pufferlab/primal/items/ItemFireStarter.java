package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.network.packets.PacketFireStarter;
import net.pufferlab.primal.tileentities.IHeatable;
import net.pufferlab.primal.utils.FacingUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFireStarter extends Item {

    public IIcon[] icons = new IIcon[1];

    public ItemFireStarter() {
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        World world = player.worldObj;
        if (world.isRemote) return;
        int max = 60;
        int charge = (getMaxItemUseDuration(stack) - count) % (max + 2);
        boolean success = false;
        int chance = Config.fireStarterSuccessChance.getChance();

        if (charge > max) {
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, false);
            if (mop != null) {
                int x = FacingUtils.getBlockX(mop.sideHit, mop.blockX);
                int y = FacingUtils.getBlockY(mop.sideHit, mop.blockY);
                int z = FacingUtils.getBlockZ(mop.sideHit, mop.blockZ);
                float hitX = (float) (mop.hitVec.xCoord - mop.blockX);
                float hitY = (float) (mop.hitVec.yCoord - mop.blockY);
                float hitZ = (float) (mop.hitVec.zCoord - mop.blockZ);
                if (world.rand.nextInt(chance) == 0) {
                    TileEntity te = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                    if (te instanceof IHeatable tef) {
                        if (tef.canBeFired()) {
                            tef.setFired(true);
                        }
                    } else {
                        world.setBlock(x, y, z, Blocks.fire, 0, 2);
                    }
                    success = true;
                }
                float pHitX = mop.blockX + hitX;
                float pHitY = mop.blockY + hitY;
                float pHitZ = mop.blockZ + hitZ;
                world.spawnParticle("smoke", pHitX, pHitY, pHitZ, 0.0F, 0.0F, 0.0F);
                stack.damageItem(1, player);
                updatePacket(player, success);
            }
        }
    }

    public void updatePacket(EntityPlayer player, boolean success) {
        if (!player.worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketFireStarter(player, success));
        }
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icons[0] = register.registerIcon(Primal.MODID + ":firestarter");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return icons[0];
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return icons[0];
    }

    @Override
    public String getUnlocalizedName() {
        return "item." + Primal.MODID + ".firestarter";
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int p_94599_1_) {
        return icons[0];
    }
}
