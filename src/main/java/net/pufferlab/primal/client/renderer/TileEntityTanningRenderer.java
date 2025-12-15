package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockTanning;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.tileentities.TileEntityTanning;

import org.lwjgl.opengl.GL11;

public class TileEntityTanningRenderer extends TileEntitySpecialRenderer {

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
        TileEntityTanning scraping = (TileEntityTanning) tileEntity;
        Block block = world.getBlock(scraping.xCoord, scraping.yCoord, scraping.zCoord);
        if (!(block instanceof BlockTanning)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot = scraping.getInventoryStack(0);

        int facing = scraping.facingMeta;
        renderSlotItem(slot, x + 0.5, y, z + 0.5, facing);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust, int facing) {
        GL11.glPushMatrix();
        if (stack != null) {
            this.slotEntity.setEntityItemStack(stack);
            this.slotEntity.hoverStart = 0.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 0.41, zAdjust);
            GL11.glScalef(1.25F, 1.25F, 1.25F);
            if (facing == 1) {
                GL11.glTranslatef(0.0F, 0.0F, -0.11F);
                GL11.glRotatef(30, 1.0F, 0.0F, 0.0F);
            }
            if (facing == 2) {
                GL11.glTranslatef(-0.11F, 0.0F, 0.0F);
                GL11.glRotatef(-30, 0.0F, 0.0F, 1.0F);
            }
            if (facing == 3) {
                GL11.glTranslatef(0.0F, 0.0F, 0.11F);
                GL11.glRotatef(-30, 1.0F, 0.0F, 0.0F);
            }
            if (facing == 4) {
                GL11.glTranslatef(0.11F, 0.0F, 0.0F);
                GL11.glRotatef(30, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(ModelPrimal.getFacingAngleDegree(facing), 0.0F, 1.0F, 0.0F);
            RenderItem.renderInFrame = true;
            try {
                this.itemRenderer.doRender(slotEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            } catch (RuntimeException ignored) {}
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
    }
}
