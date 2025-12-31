package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelPrimal;

import org.lwjgl.opengl.GL11;

public class ItemPrimalRenderer implements IItemRenderer {

    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final boolean hasOffset;
    private ModelPrimal[] models;
    private int[] metas;

    public ItemPrimalRenderer() {
        this.offsetX = 0.0F;
        this.offsetY = 0.0F;
        this.offsetZ = 0.0F;
        hasOffset = false;
    }

    public ItemPrimalRenderer(float offsetX, float offsetY, float offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        hasOffset = true;
    }

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
        if (Utils.contains(getItemBlockMeta(), item.getItemDamage()) || getItemBlockMeta() == null) {
            return true;
        }
        return false;
    }

    public boolean hasBigModel(ItemStack stack) {
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (Utils.contains(getItemBlockMeta(), item.getItemDamage()) || getItemBlockMeta() == null) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (isItemBlock(item)) {
            models = getItemBlockModel(item);
            metas = getItemBlockMeta();
            if (Utils.contains(metas, item.getItemDamage())) {
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
                int index = 0;
                if (metas != null) {
                    index = Utils.getIndex(metas, item.getItemDamage());
                }
                ModelPrimal model = models[index];

                if (hasOffset) {
                    GL11.glTranslatef(offsetX, offsetY, offsetZ);
                }
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
