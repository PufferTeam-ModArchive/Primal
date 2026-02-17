package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockBarrel.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockBarrel;
import net.pufferlab.primal.client.models.ModelBarrel;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockBarrelRenderer extends BlockPrimalRenderer {

    private final ModelBarrel modelBarrel = new ModelBarrel();
    private final ModelFluid modelFluid = new ModelFluid();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        // spotless:off
        Tessellator tess = Tessellator.instance;
        dumpVertices(tess, x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            int renderPass = ForgeHooksClient.getWorldRenderPass();
            FluidStack stack = tef.getFluidStack();
            FluidStack stackOutput = tef.getFluidStackOutput();
            float height = tef.getFillLevel(0.1875F, 0.875F);
            float heightOutput = tef.getFillLevelOutput(0.1875F, 0.875F);
            modelBarrel.bb_main.rotateAngleX = 0.0F;
            if (tef.isOpen) {
                modelBarrel.top.isHidden = true;
            } else {
                modelBarrel.top.isHidden = false;
            }
            if (tef.isFloorBarrel) {
                modelBarrel.setFacingOffset(0.0F, 0.375F, -0.5F);
                modelBarrel.bb_main.rotateAngleX = (float) (Math.PI / 2);
                modelBarrel.top.isHidden = false;
            } else {
                modelBarrel.setFacingOffset(0.0F, 0.0F, 0.0F);
            }
            int meta = tef.facingMeta;
            modelBarrel.setFacing(meta);
            double o = Constants.modelConst;
            if (renderPass == 1 && !tef.isFloorBarrel) {
                modelFluid.render(renderer, tess, x, y, z, stack, 0.125F + o, 0.1875F, 0.125F + o, 0.875F - o, height, 0.875F - o, false, false);
                modelFluid.render(renderer, tess, x, y, z, stackOutput, 0.125F + o, 0.1875F, 0.125F + o, 0.875F - o, heightOutput, 0.875F - o, false, false);
            } else if (renderPass == 0) {
                Block blockAbove = world.getBlock(x, y + 1, z);
                if (blockAbove instanceof BlockBarrel) {
                    renderer.setRenderBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
                    renderer.setOverrideBlockTexture(Blocks.planks.getIcon(0, 1));
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setOverrideBlockTexture(null);
                }
                modelBarrel.render(renderer, tess, block, x, y, z, iconBarrel);
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
        return Primal.proxy.getBarrelRenderID();
    }
}
