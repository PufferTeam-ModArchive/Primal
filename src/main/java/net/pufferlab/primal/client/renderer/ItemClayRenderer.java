package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelLargeVessel;

import org.lwjgl.opengl.GL11;

public class ItemClayRenderer extends ItemPrimalRenderer {

    ModelLargeVessel modelLargeVessel = new ModelLargeVessel();
    public final int largeVesselMeta = Utils.getItemFromArray(Constants.clayItems, "clay_large_vessel");

    public ItemClayRenderer() {
        modelLargeVessel.setType(1);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (item.getItemDamage() == largeVesselMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (item.getItemDamage() == largeVesselMeta) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItemDamage() == largeVesselMeta) {
            GL11.glPushMatrix();
            if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            }
            if (type == ItemRenderType.INVENTORY) {
                GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            }
            if (type == ItemRenderType.ENTITY) {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
            }
            modelLargeVessel.render();
            GL11.glPopMatrix();
        }
    }

}
