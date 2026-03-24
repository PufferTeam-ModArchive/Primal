package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockTanning.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelTanning;
import net.pufferlab.primal.client.models.ModelTanningFrame;
import net.pufferlab.primal.tileentities.TileEntityTanning;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockTanningRenderer extends BlockPrimalRenderer {

    private final ModelTanningFrame modelTanningFrame = new ModelTanningFrame();
    private final ModelTanning modelTanning = new ModelTanning();
    private final ModelTanningFrame modelTanningFrame2 = new ModelTanningFrame();
    private final ModelTanning modelTanning2 = new ModelTanning();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelTanningFrame2.strings.isHidden = true;
        modelTanningFrame2.render();
        GL11.glTranslatef(0.0625F, 0.0F, 0.0F);
        modelTanning2.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        TileEntityTanning tef = (TileEntityTanning) world.getTileEntity(x, y, z);
        modelTanningFrame.strings.isHidden = true;
        if (tef.getInventoryStack(0) != null) {
            modelTanningFrame.strings.isHidden = false;
        }
        modelTanning.setFacingOffset(0.0625F, 0.0F, -0.32F);
        modelTanningFrame.setFacingOffset(0.0F, 0.45F, 0.075F);
        modelTanning.setFacing(tef.facingMeta);
        modelTanningFrame.setFacing(tef.facingMeta);
        modelTanning.render(renderer, tess, block, x, y, z, iconTanning);
        modelTanningFrame.render(renderer, tess, block, x, y, z, iconTanning);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getTanningRenderID();
    }
}
