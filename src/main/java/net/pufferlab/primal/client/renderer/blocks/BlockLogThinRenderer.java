package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.BlockLogThin;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = false)
public class BlockLogThinRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block0, int metadata, int modelId, RenderBlocks renderer) {
        if (block0 instanceof BlockLogThin block) {
            float margin = block.getMargin(metadata);
            renderer.setRenderBounds(margin, 0, margin, 1 - margin, 1, 1 - margin);
            renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block0, int modelId,
        RenderBlocks renderer) {
        boolean flag = false;
        if (block0 instanceof BlockLogThin block) {
            float margin = block.getMargin(world.getBlockMetadata(x, y, z));
            int connectFlags = block.calcConnectionFlags(world, x, y, z);

            boolean connectYNeg = (connectFlags & 1) != 0;
            boolean connectYPos = (connectFlags & 2) != 0;
            boolean connectZNeg = (connectFlags & 4) != 0;
            boolean connectZPos = (connectFlags & 8) != 0;
            boolean connectXNeg = (connectFlags & 16) != 0;
            boolean connectXPos = (connectFlags & 32) != 0;

            boolean connectY = connectYNeg | connectYPos;
            boolean connectZ = connectZNeg | connectZPos;
            boolean connectX = connectXNeg | connectXPos;

            if (!(connectYNeg && connectYPos) && !(connectZNeg && connectZPos) && !(connectXNeg && connectXPos)) {
                if (connectY && !connectX && !connectZ) {
                    block.setPass(0);
                } else if (connectZ && !connectY && !connectX) {
                    block.setPass(1);
                    renderer.uvRotateNorth = 1;
                    renderer.uvRotateSouth = 1;
                } else if (connectX && !connectY && !connectZ) {
                    block.setPass(2);
                    renderer.uvRotateEast = 1;
                    renderer.uvRotateWest = 1;
                    renderer.uvRotateTop = 1;
                    renderer.uvRotateBottom = 1;
                } else {
                    block.setPass(3);
                }

                renderer.setRenderBounds(margin, margin, margin, 1 - margin, 1 - margin, 1 - margin);
                flag = renderer.renderStandardBlock(block, x, y, z);
            }

            if (connectY) {
                block.setPass(0);
                if (connectYNeg && connectYPos && !(connectXNeg && connectXPos))
                    renderer.setRenderBounds(margin, 0, margin, 1 - margin, 1, 1 - margin);
                else if (connectYNeg && connectYPos && (connectXNeg && connectXPos)) {
                    renderer.setRenderBounds(margin, 0, margin, 1 - margin, margin, 1 - margin);
                    flag = renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(margin, 1 - margin, margin, 1 - margin, 1, 1 - margin);
                } else if (connectYNeg) renderer.setRenderBounds(margin, 0, margin, 1 - margin, margin, 1 - margin);
                else if (connectYPos) renderer.setRenderBounds(margin, 1 - margin, margin, 1 - margin, 1, 1 - margin);
                flag = renderer.renderStandardBlock(block, x, y, z);
            }

            if (connectZ) {
                block.setPass(1);
                renderer.uvRotateSouth = 1;
                renderer.uvRotateNorth = 1;
                if (connectZNeg && connectZPos && !(connectYNeg && connectYPos))
                    renderer.setRenderBounds(margin, margin, 0, 1 - margin, 1 - margin, 1);
                else if (connectZNeg && connectZPos && (connectYNeg && connectYPos)) {
                    renderer.setRenderBounds(margin, margin, 0, 1 - margin, 1 - margin, margin);
                    flag = renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(margin, margin, 1 - margin, 1 - margin, 1 - margin, 1);
                } else if (connectZNeg) renderer.setRenderBounds(margin, margin, 0, 1 - margin, 1 - margin, margin);
                else if (connectZPos) renderer.setRenderBounds(margin, margin, 1 - margin, 1 - margin, 1 - margin, 1);
                flag = renderer.renderStandardBlock(block, x, y, z);
            }

            if (connectX) {
                block.setPass(2);
                renderer.uvRotateEast = 1;
                renderer.uvRotateWest = 1;
                renderer.uvRotateTop = 1;
                renderer.uvRotateBottom = 1;
                if (connectXNeg && connectXPos && !(connectZNeg && connectZPos))
                    renderer.setRenderBounds(0, margin, margin, 1, 1 - margin, 1 - margin);
                else if (connectXNeg && connectXPos && (connectZNeg && connectZPos)) {
                    renderer.setRenderBounds(0, margin, margin, margin, 1 - margin, 1 - margin);
                    flag = renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(1 - margin, margin, margin, 1, 1 - margin, 1 - margin);
                } else if (connectXNeg) renderer.setRenderBounds(0, margin, margin, margin, 1 - margin, 1 - margin);
                else if (connectXPos) renderer.setRenderBounds(1 - margin, margin, margin, 1, 1 - margin, 1 - margin);
                flag = renderer.renderStandardBlock(block, x, y, z);
            }

            block.setPass(0);

            renderer.uvRotateSouth = 0;
            renderer.uvRotateEast = 0;
            renderer.uvRotateWest = 0;
            renderer.uvRotateNorth = 0;
            renderer.uvRotateTop = 0;
            renderer.uvRotateBottom = 0;
        }

        return flag;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
