package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;

public class BlockGroundcover extends BlockMeta {

    public Item item;

    public BlockGroundcover(Material material, String[] materials, String type) {
        super(material, materials, type);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F * 3, 1.0F);
        this.canBlockGrass = true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public BlockGroundcover setItem(Item item) {
        this.item = item;
        return this;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess worldIn, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return this.canBlockStay(worldIn, x, y, z);
    }

    @Override
    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        return worldIn.isSideSolid(x, y - 1, z, ForgeDirection.UP);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        dropItemStack(worldIn, x, y, z, new ItemStack(getItem(worldIn, x, y, z), 1, meta));
    }

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            Random rando = world.rand;
            float ri = rando.nextFloat() * 0.8F + 0.1F;
            float rj = rando.nextFloat() * 0.8F + 0.1F;
            float rk = rando.nextFloat() * 0.8F + 0.1F;
            EntityItem entityItem = new EntityItem(world, x + ri, y + rj, z + rk, item.copy());
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
    public Item getItem(World worldIn, int x, int y, int z) {
        return item;
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        return meta;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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

    @Override
    public int getRenderType() {
        return Primal.proxy.getGroundcoverRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }
}
