package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelPipe;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockPipe extends BlockPrimal {

    public IIcon pipe;
    public static final int iconPipe = 99;

    public BlockPipe() {
        super(Material.iron);
        this.setBlockBounds(0.4f, 0.4f, 0.4f, 0.45f, 0.45f, 0.45f);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);

        pipe = reg.registerIcon(Primal.MODID + ":pipe");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconPipe) {
            return pipe;
        }
        return super.getIcon(side, meta);
    }

    ModelPipe modelPipe = new ModelPipe();

    @Override
    public boolean renderDefaultBounds() {
        return false;
    }

    @Override
    public boolean collideDefaultBounds() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, EntityPlayer player, BoundsType bounds) {
        int axis = -1;
        for (int i = 0; i < modelPipe.tube.length; i++) {
            modelPipe.tube[i].isHidden = true;
        }
        Block blockNorth = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.NORTH);
        if (blockNorth instanceof BlockPipe) {
            modelPipe.tube[0].isHidden = false;
            axis = updateAxis(axis, 1);
        }
        Block blockEast = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.EAST);
        if (blockEast instanceof BlockPipe) {
            modelPipe.tube[3].isHidden = false;
            axis = updateAxis(axis, 2);
        }
        Block blockSouth = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.SOUTH);
        if (blockSouth instanceof BlockPipe) {
            modelPipe.tube[2].isHidden = false;
            axis = updateAxis(axis, 1);
        }
        Block blockWest = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.WEST);
        if (blockWest instanceof BlockPipe) {
            modelPipe.tube[1].isHidden = false;
            axis = updateAxis(axis, 2);
        }
        Block blockUp = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.UP);
        if (blockUp instanceof BlockPipe) {
            modelPipe.tube[4].isHidden = false;
            axis = updateAxis(axis, 0);
        }
        Block blockDown = BlockUtils.getBlockDirection(world, x, y, z, ForgeDirection.DOWN);
        if (blockDown instanceof BlockPipe) {
            modelPipe.tube[5].isHidden = false;
            axis = updateAxis(axis, 0);
        }
        if (axis == 0 || axis == -1) {
            modelPipe.tube[4].isHidden = false;
            modelPipe.tube[5].isHidden = false;
        } else if (axis == 1) {
            modelPipe.tube[0].isHidden = false;
            modelPipe.tube[2].isHidden = false;
        } else if (axis == 2) {
            modelPipe.tube[1].isHidden = false;
            modelPipe.tube[3].isHidden = false;
        }

        return modelPipe.getBounds();
    }

    public int updateAxis(int axis, int newAxis) {
        if (axis == -1) {
            return newAxis;
        }
        return -2;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".pipe";
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
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        worldIn.markBlockForUpdate(x, y, z);
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getPipeRenderer();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
