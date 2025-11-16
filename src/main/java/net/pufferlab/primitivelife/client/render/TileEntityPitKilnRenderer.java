package net.pufferlab.primitivelife.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primitivelife.blocks.BlockPitKiln;
import net.pufferlab.primitivelife.tileentities.TileEntityPitKiln;

import org.lwjgl.opengl.GL11;

public class TileEntityPitKilnRenderer extends TileEntitySpecialRenderer {

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
        TileEntityPitKiln kiln = (TileEntityPitKiln) tileEntity;
        Block block = world.getBlock(kiln.xCoord, kiln.yCoord, kiln.zCoord);
        if (!(block instanceof BlockPitKiln)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot1 = kiln.getInventoryStack(0);
        ItemStack slot2 = kiln.getInventoryStack(1);
        ItemStack slot3 = kiln.getInventoryStack(2);
        ItemStack slot4 = kiln.getInventoryStack(3);

        double xOffset = x + 0.25;
        double zOffset = z + 0.25;
        renderSlotItem(slot1, xOffset + 0.5, y, zOffset + 0.5);
        renderSlotItem(slot2, xOffset, y, zOffset + 0.5);
        renderSlotItem(slot3, xOffset + 0.5, y, zOffset);
        renderSlotItem(slot4, xOffset, y, zOffset);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            this.slotEntity.setEntityItemStack(stack);
            this.slotEntity.hoverStart = 0.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 0.0625, zAdjust - 0.0975);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
            RenderItem.renderInFrame = true;
            try {
                this.itemRenderer.doRender(slotEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            } catch (RuntimeException ignored) {}
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
    }
}
