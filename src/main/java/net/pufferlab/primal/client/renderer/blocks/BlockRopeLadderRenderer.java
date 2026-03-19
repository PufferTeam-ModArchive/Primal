package net.pufferlab.primal.client.renderer.blocks;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;

@ThreadSafeISBRH(perThread = false)
public class BlockRopeLadderRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);

        int meta = world.getBlockMetadata(x, y, z);
        IIcon icon = block.getIcon(0, meta);
        if(meta == 2 || meta == 3) {
            renderer.renderFaceZNeg(block, x, y, z + 0.1, icon);
            renderer.renderFaceZPos(block, x, y, z - 0.1, icon);
        }
        if(meta == 4 || meta == 5) {
            renderer.renderFaceXNeg(block, x + 0.1, y, z, icon);
            renderer.renderFaceXPos(block, x - 0.1, y, z, icon);
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getRopeLadderRenderID();
    }
}
