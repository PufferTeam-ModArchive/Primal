package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

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
        int max = 60;
        int charge = (getMaxItemUseDuration(stack) - count) % (max + 2);

        if (charge > max) {
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
            if (mop != null) {
                int x = Utils.getBlockX(mop.sideHit, mop.blockX);
                int y = Utils.getBlockY(mop.sideHit, mop.blockY);
                int z = Utils.getBlockZ(mop.sideHit, mop.blockZ);
                float hitX = (float) (mop.hitVec.xCoord - mop.blockX);
                float hitY = (float) (mop.hitVec.yCoord - mop.blockY);
                float hitZ = (float) (mop.hitVec.zCoord - mop.blockZ);
                World world = player.worldObj;
                if (world.rand.nextInt(5) == 0) {
                    if (!world.isRemote) {
                        world.setBlock(x, y, z, Blocks.fire);
                    }
                }
                world.spawnParticle("smoke", x + hitX, y + hitY, z + hitZ, 0.0F, 0.1F, 0.0F);
                if (!world.isRemote) {
                    stack.damageItem(1, player);
                }
            }
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
