package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockCast.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.client.models.ModelItem;
import net.pufferlab.primal.client.models.ModelMold;
import net.pufferlab.primal.tileentities.TileEntityCast;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCastRenderer extends BlockPrimalRenderer {

    private final ModelFluid modelFluid = new ModelFluid();
    private final ModelMold modelMold = new ModelMold();
    private final ModelItem modelItem = new ModelItem();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        // spotless:off
        Tessellator tess = Tessellator.instance;
        dumpVertices(tess, x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityCast tef) {
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.0625F, 0.125F);
            if (renderPass == 1) {
                modelFluid.render(renderer, tess, x, y, z, stack, 0.1875F, 0.0625F, 0.1875F, 0.8125F, height, 0.8125F, false, false);
            } else if (renderPass == 0) {
                modelMold.render(renderer, tess, block, x, y, z, iconCast);

                modelItem.bb_main.rotateAngleX = (float) Math.PI / 2;
                modelItem.renderItem(renderer, tess, block, x, y, z, -0.425D, 0.0822D, -0.425D, tef.castIndex + 100, 0.85F);
            }
        }
        return true;
        // spotless:on
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getCastRenderID();
    }
}
