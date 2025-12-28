package net.pufferlab.primal.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

public class BlockCrucible extends BlockContainer {

    public IIcon[] icons = new IIcon[2];
    private IIcon[] heatingIcons = new IIcon[8];

    public BlockCrucible() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (!Utils.isFluidContainer(player.getHeldItem())) {
            player.openGui(Primal.instance, Primal.proxy.crucibleContainerID, worldIn, x, y, z);
        }
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":crucible");
        icons[1] = reg.registerIcon(Primal.MODID + ":ceramic");
        heatingIcons[0] = reg.registerIcon(Primal.MODID + ":crucible");
        heatingIcons[1] = reg.registerIcon(Primal.MODID + ":crucible_1");
        heatingIcons[2] = reg.registerIcon(Primal.MODID + ":crucible_2");
        heatingIcons[3] = reg.registerIcon(Primal.MODID + ":crucible_3");
        heatingIcons[4] = reg.registerIcon(Primal.MODID + ":crucible_4");
        heatingIcons[5] = reg.registerIcon(Primal.MODID + ":crucible_5");
        heatingIcons[6] = reg.registerIcon(Primal.MODID + ":crucible_6");
        heatingIcons[7] = reg.registerIcon(Primal.MODID + ":crucible_7");
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (side > 99) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityCrucible tef) {
                int heatingLevel = Utils.getHeatingLevel(tef.temperature);
                return heatingIcons[heatingLevel];
            }
        }
        return super.getIcon(worldIn, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[0];
        }
        return icons[1];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".crucible";
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        ItemStack heldItem = placer.getHeldItem();
        NBTTagCompound tagCompound = heldItem.getTagCompound();
        if (tagCompound != null) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityCrucible tef) {
                tef.readFromNBTInventory(tagCompound);
            }
        }
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        ItemStack item = new ItemStack(this, 1, 0);
        NBTTagCompound tagCompound = new NBTTagCompound();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrucible tef) {
            tef.writeToNBTInventory(tagCompound);
            item.setTagCompound(tagCompound);
        }
        dropItemStack(worldIn, x, y, z, item);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrucible();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getCrucibleRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
