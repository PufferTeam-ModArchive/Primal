package net.pufferlab.primal.client.renderer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.client.models.entities.IWearableModel;
import net.pufferlab.primal.client.models.entities.ModelBipedPrimal;
import net.pufferlab.primal.items.IWearable;

import org.lwjgl.opengl.GL11;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

public class RenderWearable {

    public static RenderWearable wearableHandler = new RenderWearable();

    public void handleRenderingPre(RendererLivingEntity thiz) {
        if (thiz.mainModel instanceof ModelBiped modelBiped) {
            for (ModelBiped biped : ModelBipedPrimal.modelBipeds) {

                biped.aimedBow = modelBiped.aimedBow;
                biped.isSneak = modelBiped.isSneak;
                biped.heldItemRight = modelBiped.heldItemRight;
            }
        }
    }

    public void handleRendering(RendererLivingEntity thiz, EntityLivingBase entity, float f7, float f6, float f4,
        float f3, float f2, float f13, float f5, float p_76986_9_) {
        if (entity instanceof EntityPlayer player) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null) {
                if (heldItem.getItem() instanceof IWearable item) {
                    if (item.getWearableType() == IWearable.WearableType.ITEM) {
                        renderModelBiped(heldItem, thiz, entity, f7, f6, f4, f3, f2, f13, f5, p_76986_9_);
                    }
                }
            }
            if (Mods.baubles.isLoaded()) {
                InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack itemstack = inv.getStackInSlot(i);
                    if (itemstack != null) {
                        if (itemstack.getItem() instanceof IWearable item) {
                            if (item.getWearableType() == IWearable.WearableType.BAUBLES) {
                                renderModelBiped(itemstack, thiz, entity, f7, f6, f4, f3, f2, f13, f5, p_76986_9_);
                            }
                        }
                    }
                }
            }
        }
    }

    public void renderModelBiped(ItemStack stack, RendererLivingEntity thiz, EntityLivingBase entity, float f7,
        float f6, float f4, float f3, float f2, float f13, float f5, float p_76986_9_) {
        if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof IWearable item2) {
                thiz.setRenderPassModel(getModel(item2, entity, stack));
                if (thiz.renderPassModel instanceof ModelBipedPrimal model) {
                    renderModel(model, thiz, entity, f7, f6, f4, f3, f2, f13, f5, p_76986_9_);
                }
                ModelBipedPrimal model2 = getModelExtra(item2, entity, stack);
                if (model2 != null) {
                    thiz.setRenderPassModel(model2);
                    if (thiz.renderPassModel instanceof ModelBipedPrimal model) {
                        renderModel(model, thiz, entity, f7, f6, f4, f3, f2, f13, f5, p_76986_9_);
                    }
                }
            }
        }
    }

    public ModelBipedPrimal getModel(IWearable wearable, EntityLivingBase entity, ItemStack stack) {
        IWearableModel modelW = wearable.getWearableModel(entity, stack);
        if (modelW instanceof ModelBipedPrimal model2) {
            return model2;
        }
        return null;
    }

    public ModelBipedPrimal getModelExtra(IWearable wearable, EntityLivingBase entity, ItemStack stack) {
        IWearableModel modelW = wearable.getWearableModelExtra(entity, stack);
        if (modelW instanceof ModelBipedPrimal model2) {
            return model2;
        }
        return null;
    }

    public void renderModel(ModelBipedPrimal model, RendererLivingEntity thiz, EntityLivingBase entity, float f7,
        float f6, float f4, float f3, float f2, float f13, float f5, float p_76986_9_) {
        GL11.glPushMatrix();
        model.bindTex();
        model.onGround = thiz.mainModel.onGround;
        model.isRiding = thiz.mainModel.isRiding;
        model.isChild = thiz.mainModel.isChild;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        model.transformModel(entity, f7, f6, f4, f3 - f2, f13, f5, p_76986_9_);
        model.setLivingAnimations(entity, f7, f6, p_76986_9_);
        model.render(entity, f7, f6, f4, f3 - f2, f13, f5);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}
