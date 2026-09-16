package net.pufferlab.primal.blocks;

import static net.minecraftforge.common.util.ForgeDirection.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRopeLadder extends BlockLadder implements IPrimalBlock {

    public IIcon ropeLadder;

    public BlockRopeLadder() {
        this.setHardness(0.4F);
        this.setStepSound(soundTypeLadder);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null) {
            if (heldItem.getItem() == Item.getItemFromBlock(this)) {
                for (int i = 0; i > -(Config.ropeLadderExtension.getInt()); i--) {
                    Block blockBelow = worldIn.getBlock(x, y + i - 1, z);
                    if (blockBelow.getMaterial() == Material.air) {
                        int currentMeta = worldIn.getBlockMetadata(x, y + i, z);
                        worldIn.setBlock(x, y + i - 1, z, this, currentMeta, 2);
                        heldItem.stackSize--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        for (int i = 0; i < Config.ropeLadderExtension.getInt(); i++) {
            Block block = worldIn.getBlock(x, y + i, z);
            Block blockAbove = worldIn.getBlock(x, y + 1 + i, z);
            if (block == this && blockAbove != this) {
                if (!isBlockSupported(worldIn, x, y + i, z)) {
                    breakLadder(worldIn, x, y + i, z);
                    for (int j = 0; j > -(Config.ropeLadderExtension.getInt()); j--) {
                        if (!isBlockSupported(worldIn, x, y + i + j, z)) {
                            breakLadder(worldIn, x, y + i + j, z);
                        } else {
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void breakLadder(World worldIn, int x, int y, int z) {
        int l = worldIn.getBlockMetadata(x, y, z);
        this.dropBlockAsItem(worldIn, x, y, z, l, 0);
        worldIn.setBlockToAir(x, y, z);
    }

    public boolean isBlockSupported(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y, z);
        if (block != this) return true;
        int l = worldIn.getBlockMetadata(x, y, z);
        if (l == 2 && worldIn.isSideSolid(x, y, z + 1, NORTH)) {
            return true;
        }

        if (l == 3 && worldIn.isSideSolid(x, y, z - 1, SOUTH)) {
            return true;
        }

        if (l == 4 && worldIn.isSideSolid(x + 1, y, z, WEST)) {
            return true;
        }

        if (l == 5 && worldIn.isSideSolid(x - 1, y, z, EAST)) {
            return true;
        }
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        ropeLadder = reg.registerIcon(Primal.MODID + ":rope_ladder");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return ropeLadder;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".rope_ladder";
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getRopeLadderRenderer();
    }

    @Override
    public int getRenderType() {
        return getRenderId();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }
}
