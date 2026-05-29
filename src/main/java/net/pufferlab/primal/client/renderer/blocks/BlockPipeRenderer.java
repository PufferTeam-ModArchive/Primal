package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockPipe.iconPipe;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockPipe;
import net.pufferlab.primal.client.models.ModelPipe;
import net.pufferlab.primal.utils.BlockUtils;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockPipeRenderer extends BlockPrimalRenderer {

    ModelPipe modelPipe = new ModelPipe();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
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
        Primal.LOG.info(axis);
        modelPipe.render(renderer, tess, block, x, y, z, iconPipe);
        return true;
    }

    public int updateAxis(int axis, int newAxis) {
        if (axis == -1) {
            return newAxis;
        }
        return -2;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }
}
