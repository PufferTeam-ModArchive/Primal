package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.tileentities.TileEntityFarmland;
import net.pufferlab.primal.tileentities.TileEntityPrimal;
import net.pufferlab.primal.utils.SoilType;

public class BlockSoilFarmland extends BlockMetaFarmland implements ITileEntityProvider {

    public BlockSoilFarmland(SoilType[] materials, String type) {
        super(Material.ground, SoilType.getNames(materials), type);
        this.setHardness(0.6F);
        this.setStepSound(soundTypeGravel);
        this.setHasSuffix();
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(Registry.dirt);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        Material material = worldIn.getBlock(x, y + 1, z)
            .getMaterial();

        if (material.isSolid()) {
            worldIn.setBlock(x, y, z, Registry.dirt, meta, 2);
        }
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityPrimal tef) {
            tef.init();
        }
    }

    // Tile Entity Provider Function
    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        int meta = worldIn.getBlockMetadata(x, y, z);
        worldIn.setTileEntity(x, y, z, this.createNewTileEntity(worldIn, meta));
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
        worldIn.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, int x, int y, int z, int eventId, int eventData) {
        super.onBlockEventReceived(worldIn, x, y, z, eventId, eventData);
        TileEntity tileentity = worldIn.getTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(eventId, eventData) : false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFarmland();
    }
}
