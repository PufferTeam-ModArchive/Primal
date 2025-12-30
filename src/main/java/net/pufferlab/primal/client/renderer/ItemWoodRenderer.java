package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelFirewood;

import org.lwjgl.opengl.GL11;

public class ItemWoodRenderer extends ItemPrimalRenderer {

    ModelFirewood modelFirewood = new ModelFirewood();
    public int firewoodMeta = Utils.getIndex(Constants.woodItems, "firewood");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (item.getItemDamage() == firewoodMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (item.getItemDamage() == firewoodMeta) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItemDamage() == firewoodMeta) {
            GL11.glPushMatrix();
            if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            if (type == ItemRenderType.INVENTORY) {
                GL11.glTranslatef(-0.2F, -0.1F, 0.0F);
                GL11.glScalef(1.25F, 1.25F, 1.25F);
            }
            modelFirewood.render();
            GL11.glPopMatrix();
        }
    }

}
