package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelPrimal;

import org.lwjgl.opengl.GL11;

public class ItemPrimalRenderer implements IItemRenderer {

    public ModelPrimal[] getItemBlockModel(ItemStack stack) {
        return null;
    }

    public int[] getItemBlockMeta() {
        return null;
    }

    public boolean isItemBlock(ItemStack stack) {
        return false;
    }

    public boolean needsNormalItemRender() {
        return false;
    }

    public boolean hasTemperature() {
        return false;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (Utils.containsExactMatch(getItemBlockMeta(), item.getItemDamage())) {
            return true;
        }
        return false;
    }

    public boolean hasBigModel(ItemStack stack) {
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (Utils.containsExactMatch(getItemBlockMeta(), item.getItemDamage())) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (isItemBlock(item)) {
            if (Utils.containsExactMatch(getItemBlockMeta(), item.getItemDamage())) {
                GL11.glPushMatrix();
                if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                    GL11.glTranslatef(0.5F, 0.0F, 0.5F);
                }
                if (type == ItemRenderType.INVENTORY) {
                    GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                }
                if (needsNormalItemRender()) {
                    if (type == ItemRenderType.ENTITY) {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }
                }
                if (hasBigModel(item)) {
                    GL11.glScalef(1.5F, 1.5F, 1.5F);
                }
                int index = Utils.getItemFromArray(getItemBlockMeta(), item.getItemDamage());
                ModelPrimal model = getItemBlockModel(item)[index];

                if (hasTemperature()) {
                    RenderTemperature.renderTemperature(model, Utils.getTemperatureFromNBT(item.getTagCompound()));
                } else {
                    model.render();
                }
                GL11.glPopMatrix();
            }
        }
    }
}
