package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockCampfire.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelCampfire;
import net.pufferlab.primal.client.models.ModelCampfireSpit;
import net.pufferlab.primal.client.models.ModelCrossed;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.tileentities.TileEntityMetaFacing;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCampfireRenderer extends BlockPrimalRenderer {

    private final ModelCampfire modelCampfire = new ModelCampfire();
    private final ModelCrossed modelFire = new ModelCrossed();
    private final ModelCampfireSpit modelCampfireSpit = new ModelCampfireSpit();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
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
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isBuilt) {
                modelCampfire.bottom.isHidden = false;
                modelCampfire.rocks.isHidden = false;
            }
        }
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
        boolean rotated = false;
        if (te instanceof TileEntityMetaFacing tef) {
            if (tef.facingMeta == 1 || tef.facingMeta == 3) {
                rotated = true;
            }
        }
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (renderPass == 0) {
            modelCampfire.render(renderer, tess, block, x, y, z, 0.0F, 0.5F, 0.0F, iconCampfire);
            if (te instanceof TileEntityCampfire tef) {
                if (tef.isFired()) {
                    modelFire.render(renderer, block, x, y, z, iconFire);
                }
                if (tef.hasSpit) {
                    modelCampfireSpit.bb_main.rotateAngleY = 0;
                    if (!rotated) {
                        modelCampfireSpit.bb_main.rotateAngleY = (float) Math.PI / 2;
                    }
                    modelCampfireSpit.render(renderer, tess, block, x, y, z, iconCampfireSpit);
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
        return Primal.proxy.getCampfireRenderID();
    }
}
