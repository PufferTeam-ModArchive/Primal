package net.pufferlab.primal.client.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;

import org.lwjgl.opengl.GL11;

public class ItemSpecialRenderer implements IItemRenderer {

    ModelFirewood modelFirewood = new ModelFirewood();
    public int firewoodMeta = Utils.getItemFromArray(Constants.miscItems, "firewood");

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
            GL11.glTranslatef(0.0F, 0.0F, 0.0F);
            modelFirewood.render();
            GL11.glPopMatrix();
        }
    }

}
