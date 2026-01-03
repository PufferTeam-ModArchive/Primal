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

    public ModelPrimal[] getModel(ItemStack stack) {
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

    public boolean isNormal() {
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
        if (handleContainerRendering()) {
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
                    baseTranslation(type);
                }
                if (isNormal()) {
                    if (type == ItemRenderType.ENTITY) {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }
                }
                if (shouldScaleModel(item)) {
                    float scale = getScale();
                    GL11.glScalef(scale, scale, scale);
                }
                int index = 0;
                if (meta != null) {
                    index = Utils.getIndex(meta, item.getItemDamage());
                }

                if (hasOffset) {
                    GL11.glTranslatef(offsetX, offsetY, offsetZ);
                }
                if (handleTemperatureRendering()) {
                    if (this.model != null) {
                        ModelPrimal model = this.model[index];
                        heatRenderer.renderHeat(model, Utils.getTemperatureFromNBT(item.getTagCompound()));
                    }
                } else if (handleContainerRendering()) {
                    containerRenderer.renderContainer(item, type);
                } else {
                    if (this.model != null) {
                        ModelPrimal model = this.model[index];
                        renderModel(model);
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

    public void baseTranslation(ItemRenderType type) {
        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
        }
        if (type == ItemRenderType.INVENTORY) {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        }
    }
}
