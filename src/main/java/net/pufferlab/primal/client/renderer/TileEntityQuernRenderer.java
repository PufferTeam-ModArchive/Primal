package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockQuern;
import net.pufferlab.primal.client.models.ModelHandstone;
import net.pufferlab.primal.tileentities.TileEntityQuern;

import org.lwjgl.opengl.GL11;

public class TileEntityQuernRenderer extends TileEntitySpecialRenderer {

    ModelHandstone modelHandstone = new ModelHandstone();

    public EntityItem slotEntity = new EntityItem(null, 0.0D, 0.0D, 0.0D);

    private RenderManager renderManager = RenderManager.instance;
    private RenderItem itemRenderer = new RenderItem() {

        public byte getMiniBlockCount(ItemStack stack, byte original) {
            return 1;
        }

        public boolean shouldBob() {
            return false;
        }

        public boolean shouldSpreadItems() {
            return false;
        }
    };

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityQuern quern = (TileEntityQuern) tileEntity;
        Block block = world.getBlock(quern.xCoord, quern.yCoord, quern.zCoord);
        if (!(block instanceof BlockQuern)) return;

        renderTileEntityRotationAt(tileEntity, x, y, z, partialTicks);

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot = quern.getInventoryStack(1);

        renderSlotItem(slot, x + 0.5, y + 0.90F, z + 0.5);
    }

    public void renderTileEntityRotationAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        TileEntityQuern globe = (TileEntityQuern) tileEntity;

        float partialRotation = globe.rotation;

        if (globe.speed > 0) {
            partialRotation = globe.rotation + (partialTicks * globe.speed);
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
            this.slotEntity.setEntityItemStack(stack);
            this.slotEntity.hoverStart = 0.0F;
            GL11.glTranslated(xAdjust, yAdjust, zAdjust);
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            RenderItem.renderInFrame = true;
            try {
                this.itemRenderer.doRender(slotEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            } catch (RuntimeException ignored) {}
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
    }
}
