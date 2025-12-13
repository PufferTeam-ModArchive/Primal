package net.pufferlab.primal.client.renderer;

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

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

@ThreadSafeISBRH(perThread = true)
public class BlockTanningRenderer implements ISimpleBlockRenderingHandler {

    private final ThreadLocal<ModelTanningFrame> modelTanningFrameThread = ThreadLocal
        .withInitial(ModelTanningFrame::new);
    private final ThreadLocal<ModelTanning> modelTanningThread = ThreadLocal.withInitial(ModelTanning::new);

    ModelTanningFrame modelTanningFrameGlobal = new ModelTanningFrame();
    ModelTanning modelTanningGlobal = new ModelTanning();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelTanningFrameGlobal.render();
        GL11.glTranslatef(0.0625F, 0.0F, 0.0F);
        modelTanningGlobal.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelTanningFrame modelTanningFrame = modelTanningFrameThread.get();
        ModelTanning modelTanning = modelTanningThread.get();

        Tessellator tess = Tessellator.instance;
        TileEntityTanning tef = (TileEntityTanning) world.getTileEntity(x, y, z);
        modelTanning.setFacingOffset(0.0625F, 0.0F, -0.32F);
        modelTanningFrame.setFacingOffset(0.0F, 0.0625F, 0.45F);
        modelTanning.setFacing(tef.facingMeta);
        modelTanningFrame.setFacing(tef.facingMeta);
        modelTanning.render(renderer, tess, block, x, y, z, 99);
        modelTanningFrame.render(renderer, tess, block, x, y, z, 99);

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
