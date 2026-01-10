package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.client.renderer.RenderContainer;
import net.pufferlab.primal.client.renderer.RenderHeat;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.utils.TemperatureUtils;

import org.lwjgl.opengl.GL11;

public class ItemPrimalRenderer implements IItemRenderer {

    float offsetX;
    float offsetY;
    float offsetZ;
    private final boolean hasOffset;
    private ModelPrimal[] model;
    private int[] meta;

    protected RenderContainer containerRenderer = new RenderContainer();
    protected RenderHeat heatRenderer = new RenderHeat();

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

    public boolean shouldRenderOffset(ItemStack stack) {
        return true;
    }

    public ModelPrimal[] getModel(ItemStack stack) {
        return null;
    }

    public ModelPrimal[] getModelToRender(int index) {
        return null;
    }

    public int[] getMeta() {
        return null;
    }

    public int[] getMetaBlacklist() {
        return null;
    }

    public boolean handleRendering(ItemStack stack) {
        return false;
    }

    public boolean handleTemperatureRendering() {
        return false;
    }

    public boolean handleContainerRendering() {
        return false;
    }

    public float getScale() {
        return 1.5F;
    }

    public boolean shouldScaleModel(ItemStack stack) {
        return false;
    }

    public boolean shouldRotateModel(ItemStack stack) {
        return false;
    }

    public void updateRotation(ItemStack stack) {}

    public void updateOffset(ItemStack stack, ItemRenderType type) {

    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (Utils.contains(getMetaBlacklist(), item.getItemDamage())) return false;
        if (Utils.contains(getMeta(), item.getItemDamage()) || getMeta() == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (Utils.contains(getMetaBlacklist(), item.getItemDamage())) return false;
        if (handleContainerRendering() || (getModel(item) == null && handleTemperatureRendering())) {
            if (helper == ItemRendererHelper.BLOCK_3D) {
                return false;
            }
            return type == ItemRenderType.ENTITY;
        }
        if (Utils.contains(getMeta(), item.getItemDamage()) || getMeta() == null) {
            return true;
        }

        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (Utils.contains(getMetaBlacklist(), item.getItemDamage())) return;
        if (handleRendering(item)) {
            model = getModel(item);
            meta = getMeta();
            if (Utils.contains(meta, item.getItemDamage()) || meta == null) {
                GL11.glPushMatrix();
                if (model != null) {
                    baseTranslation(type, item.getItem());
                    updateOffset(item, type);
                    if (hasOffset && shouldRenderOffset(item)) {
                        GL11.glTranslatef(offsetX, offsetY, offsetZ);
                    }
                }
                if (shouldRotateModel(item)) {
                    updateRotation(item);
                }
                if (shouldScaleModel(item)) {
                    float scale = getScale();
                    GL11.glScalef(scale, scale, scale);
                }
                int index = 0;
                if (meta != null) {
                    index = Utils.getIndex(meta, item.getItemDamage());
                }
                if (handleTemperatureRendering()) {
                    int temperature = TemperatureUtils
                        .getInterpolatedTemperature(GlobalTickingData.getClientTickTime(), item.getTagCompound());
                    if (this.model != null) {
                        ModelPrimal model = this.model[index];
                        heatRenderer.renderHeat(model, temperature);
                    } else {
                        heatRenderer.renderHeat(item, temperature, type);
                    }
                } else if (handleContainerRendering()) {
                    containerRenderer.renderContainer(item, type);
                } else {
                    if (this.model != null) {
                        ModelPrimal model = this.model[index];
                        ModelPrimal[] models = getModelToRender(index);
                        if (models != null) {
                            for (ModelPrimal modelCurrent : models) {
                                renderModel(modelCurrent);
                            }
                        } else {
                            renderModel(model);
                        }
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }

    public void renderModel(ModelPrimal model) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDisable(GL11.GL_CULL_FACE);

        model.render();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void baseTranslation(ItemRenderType type, Item item) {
        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
        }
        if (type == ItemRenderType.INVENTORY) {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        }
        if (!(item instanceof ItemBlock)) {
            if (type == ItemRenderType.ENTITY) {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
            }
        }
        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        }
    }
}
