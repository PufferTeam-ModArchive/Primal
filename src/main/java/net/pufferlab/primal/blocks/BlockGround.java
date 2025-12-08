package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockGround extends BlockMeta {

    public Item item;
    protected String[] elementsTextures;

    public BlockGround(Material material, String[] materials, String type) {
        super(material, materials, type);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F * 3, 1.0F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public BlockGround setItem(Item item) {
        this.item = item;
        return this;
    }

    public BlockGround setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        Block block = worldIn.getBlock(x, y, z);
        if (!worldIn.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            worldIn.setBlockToAir(x, y, z);
            block.onBlockPreDestroy(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (elementsTextures[i] == null) {
                if (!Utils.containsExactMatch(elementsBlacklist, elements[i])) {
                    icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_" + name);
                }
            } else {
                icons[i] = register.registerIcon(elementsTextures[i]);
            }
        }
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
        return Primal.proxy.getGroundRenderID();
    }
}
