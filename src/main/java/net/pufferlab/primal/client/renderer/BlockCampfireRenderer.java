package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelCampfire;
import net.pufferlab.primal.client.models.ModelCampfireSpit;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.tileentities.TileEntityMetaFacing;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockCampfireRenderer implements ISimpleBlockRenderingHandler {

    ModelCampfire modelCampfire = new ModelCampfire();
    ModelCampfireSpit modelCampfireSpit = new ModelCampfireSpit(0);
    ModelCampfireSpit modelCampfireSpit2 = new ModelCampfireSpit(1);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }

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
        modelCampfire.render(renderer, tess, block, x, y, z, 99);
        if (te instanceof TileEntityCampfire tef) {
            if (tef.hasSpit) {
                if (!rotated) {
                    modelCampfireSpit2.render(renderer, tess, block, x, y, z, 97);
                } else {
                    modelCampfireSpit.render(renderer, tess, block, x, y, z, 97);
                }
            }
        }
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isFired) {
                renderer.drawCrossedSquares(block.getIcon(world, x, y, z, 98), x, y, z, 1.0F);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getCampfireRenderID();
    }
}
