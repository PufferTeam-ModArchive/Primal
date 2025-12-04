package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.client.models.ModelBarrel;

import org.lwjgl.opengl.GL11;

public class ItemBarrelRenderer implements IItemRenderer {

    ModelBarrel modelBarrel = new ModelBarrel();

    int barrelMeta = 0;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (item.getItemDamage() == barrelMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (item.getItemDamage() == barrelMeta) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItemDamage() == barrelMeta) {
            GL11.glPushMatrix();
            if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            }
            if (type == ItemRenderType.INVENTORY) {
                GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            }
            modelBarrel.render();
            GL11.glPopMatrix();
        }
    }

}
