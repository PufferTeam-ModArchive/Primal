package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockOven.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelCampfire;
import net.pufferlab.primal.client.models.ModelCrossed;
import net.pufferlab.primal.client.models.ModelOven;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.tileentities.TileEntityMetaFacing;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockOvenRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelCampfire> modelCampfireThread = ThreadLocal.withInitial(ModelCampfire::new);
    private final ThreadLocal<ModelCrossed> modelFireThread = ThreadLocal.withInitial(ModelCrossed::new);
    private final ThreadLocal<ModelOven> modelOvenThread = ThreadLocal.withInitial(ModelOven::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelCampfire modelCampfire = modelCampfireThread.get();
        ModelCrossed modelFire = modelFireThread.get();
        ModelOven modelOven = modelOvenThread.get();

        Tessellator tess = Tessellator.instance;
        int meta = world.getBlockMetadata(x, y, z);
        modelCampfire.kindling.isHidden = true;
        modelCampfire.log1.isHidden = true;
        modelCampfire.log2.isHidden = true;
        modelCampfire.log3.isHidden = true;
        modelCampfire.log4.isHidden = true;
        modelCampfire.bottom.isHidden = true;
        modelCampfire.rocks.isHidden = true;
        TileEntity te = world.getTileEntity(x, y, z);
        if (meta >= 1) {
            modelCampfire.kindling.isHidden = false;
        }
        if (meta >= 2) {
            modelCampfire.log1.isHidden = false;
        }
        if (meta >= 3) {
            modelCampfire.log2.isHidden = false;
        }
        if (meta >= 4) {
            modelCampfire.log3.isHidden = false;
        }
        if (meta >= 5) {
            modelCampfire.log4.isHidden = false;
        }
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (renderPass == 0) {
            modelCampfire.render(renderer, tess, block, x, y, z, -0.02F, 0.05F + 0.5F, -0.02F, iconCampfire);
            if (te instanceof TileEntityMetaFacing tef) {
                modelOven.setFacing(tef.facingMeta);
                modelOven.render(renderer, tess, block, x, y, z, iconOven);
            }
            if (te instanceof TileEntityCampfire tef) {
                if (tef.isFired) {
                    modelFire.render(renderer, block, x, y, z, iconFire);
                }
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
        return Primal.proxy.getOvenRenderID();
    }
}
