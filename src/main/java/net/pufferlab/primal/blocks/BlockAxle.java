package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.itemblocks.ItemBlockAxle;
import net.pufferlab.primal.tileentities.TileEntityAxle;
import net.pufferlab.primal.utils.FacingUtils;

public class BlockAxle extends BlockMotion {

    public IIcon[] icons = new IIcon[2];

    public static final int iconAxle = 99;

    public BlockAxle() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem == null) return false;
            if (heldItem.getItem() == Item.getItemFromBlock(this) && heldItem.getItemDamage() == 1) {
                int axis = tef.axisMeta;
                int axisClicked = FacingUtils.getAxis(side);
                if (axis == axisClicked) {
                    tef.setGear(side, player);
                    return true;
                }
            }
            if (heldItem.getItem() == Item.getItemFromBlock(this) && heldItem.getItemDamage() == 2) {
                return tef.setBracket(side, player);
            }
        }
        return false;
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            int numberGear = 0;
            if (tef.hasGearPos) {
                numberGear++;
            }
            if (tef.hasGearNeg) {
                numberGear++;
            }
            if (numberGear > 0) {
                dropItemStack(worldIn, x, y, z, new ItemStack(this, numberGear, 1));
            }
            tef.scheduleStrongUpdate();
            if (tef.hasBracket) {
                dropItemStack(worldIn, x, y, z, new ItemStack(this, 1, 2));
            }
        }
    }

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minecraft:planks_spruce");
        icons[1] = reg.registerIcon(Primal.MODID + ":bracket");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconAxle) {
            return icons[1];
        }
        return icons[0];
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAxle();
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".axle";
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getAxleRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockAxle.class;
    }
}
