package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockForge.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelForge;
import net.pufferlab.primal.tileentities.TileEntityForge;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockForgeRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelForge> modelForgeThread = ThreadLocal.withInitial(ModelForge::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelForge modelForge = modelForgeThread.get();
        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityForge tef) {
            if (renderPass == 0) {
                int metadata = world.getBlockMetadata(x, y, z);
                if (metadata > 0) {
                    renderer.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.625F + (0.0625F * metadata), 0.75F);
                    renderer.renderStandardBlock(block, x, y, z);
                }
                modelForge.render(renderer, tess, block, x, y, z, iconForge);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getForgeRenderID();
    }
}
