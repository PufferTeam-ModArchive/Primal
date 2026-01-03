package net.pufferlab.primal.client.renderer;

import static net.pufferlab.primal.tileentities.TileEntityQuern.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockQuern;
import net.pufferlab.primal.client.models.ModelHandstone;
import net.pufferlab.primal.tileentities.TileEntityQuern;

import org.lwjgl.opengl.GL11;

public class TileEntityQuernRenderer extends TileEntityPrimalRenderer {

    ModelHandstone modelHandstone = new ModelHandstone();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityQuern quern = (TileEntityQuern) tileEntity;
        Block block = world.getBlock(quern.xCoord, quern.yCoord, quern.zCoord);
        int meta = world.getBlockMetadata(quern.xCoord, quern.yCoord, quern.zCoord);
        if (!(block instanceof BlockQuern)) return;
        this.itemRenderer.setRenderManager(renderManager);

        if (meta == 1) {
            renderTileEntityRotationAt(tileEntity, x, y, z, partialTicks);
            GL11.glEnable(GL11.GL_LIGHTING);
            ItemStack slot = quern.getInventoryStack(slotInput);

            renderSlotItem(slot, x + 0.5, y + 1F, z + 0.5);
        }
    }

    public void renderTileEntityRotationAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        TileEntityQuern quern = (TileEntityQuern) tileEntity;

        float partialRotation = quern.rotation;

        if (quern.speed > 0) {
            partialRotation = quern.rotation + (partialTicks * quern.speed);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotatef(partialRotation, 0.0F, 1.0F, 0.0F);
        modelHandstone.render();

        GL11.glPopMatrix();
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glTranslated(xAdjust, yAdjust, zAdjust);
            GL11.glRotatef(45, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(0.5F, 0.5F, 0.5F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
