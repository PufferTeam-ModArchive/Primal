package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCampfire;
import net.pufferlab.primal.tileentities.TileEntityCampfire;

import org.lwjgl.opengl.GL11;

public class TileEntityCampfireRenderer extends TileEntitySpecialRenderer {

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
        TileEntityCampfire kiln = (TileEntityCampfire) tileEntity;
        Block block = world.getBlock(kiln.xCoord, kiln.yCoord, kiln.zCoord);
        if (!(block instanceof BlockCampfire)) return;

        block.setBlockBoundsForItemRender();
        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot1 = kiln.getInventoryStack(6);
        ItemStack slot2 = kiln.getInventoryStack(7);
        ItemStack slot3 = kiln.getInventoryStack(8);
        ItemStack slot4 = kiln.getInventoryStack(9);

        double iX = 0.25F;
        double iZ = 0.75F;
        double offsetX = 0;
        double offsetZ = 0;
        int facing = kiln.facingMeta;
        double multiplier = 1;
        if (facing == 1 || facing == 2) {
            multiplier = -1;
            iX = 0.75F;
            iZ = 0.25F;
        }
        if (facing == 1 || facing == 3) {
            offsetX = 0.25 * multiplier;
            iX = iX - 0.1F * multiplier;
            iZ = iZ - 0.25F * multiplier;
        }
        if (facing == 2 || facing == 4) {
            offsetZ = -0.25 * multiplier;
            iX = iX + 0.25F * multiplier;
            iZ = iZ + 0.1F * multiplier;
        }
        double offsetY = 0.9F;

        renderSlotItem(slot1, x + iX, y + offsetY, z + iZ, facing);
        renderSlotItem(slot2, x + (offsetX * 1) + iX, y + offsetY, z + (offsetZ * 1) + iZ, facing);
        renderSlotItem(slot3, x + (offsetX * 2) + iX, y + offsetY, z + (offsetZ * 2) + iZ, facing);
        renderSlotItem(slot4, x + (offsetX * 3) + iX, y + offsetY, z + (offsetZ * 3) + iZ, facing);

    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust, int facing) {
        GL11.glPushMatrix();
        if (stack != null) {
            this.slotEntity.setEntityItemStack(stack);
            this.slotEntity.hoverStart = 0.0F;
            GL11.glTranslated(xAdjust, yAdjust, zAdjust);
            setFacing(facing);
            RenderItem.renderInFrame = true;
            try {
                this.itemRenderer.doRender(slotEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            } catch (RuntimeException ignored) {}
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
    }

    public void setFacing(int meta) {
        int meta2 = meta + 2;
        GL11.glRotatef(90 * meta2, 0, 1.0F, 0.0F);
    }
}
