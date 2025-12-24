package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCast;
import net.pufferlab.primal.tileentities.TileEntityCast;

import org.lwjgl.opengl.GL11;

public class TileEntityMoldRenderer extends TileEntitySpecialRenderer {

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
        TileEntityCast mold = (TileEntityCast) tileEntity;
        Block block = world.getBlock(mold.xCoord, mold.yCoord, mold.zCoord);
        if (!(block instanceof BlockCast)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot1 = mold.getInventoryStack(0);

        double xOffset = x + 0.5;
        double zOffset = z + 0.5;
        renderSlotItem(slot1, xOffset, y, zOffset);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            this.slotEntity.setEntityItemStack(stack);
            this.slotEntity.hoverStart = 0.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 0.0625, zAdjust - 0.1725F);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);

            GL11.glScalef(1.65F, 1.65F, 3.0F);

            RenderItem.renderInFrame = true;
            try {
                this.itemRenderer.doRender(slotEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            } catch (RuntimeException ignored) {}
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
    }
}
