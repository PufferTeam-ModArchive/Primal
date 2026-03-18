package net.pufferlab.primal.client.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.client.models.entities.ModelAccessory;
import net.pufferlab.primal.client.renderer.entities.IAccessoryRenderer;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

public class RenderAccessory {

    public static final RenderAccessory instance = new RenderAccessory();
    public static final Map<Item, IAccessoryRenderer> renderingMap = new HashMap<>();
    public static final List<ModelAccessory> models = new ArrayList<>();

    public static void registerRenderer(Item item, IAccessoryRenderer renderer) {
        renderingMap.put(item, renderer);
    }

    public static IAccessoryRenderer getRenderer(Item item) {
        return renderingMap.get(item);
    }

    public void registerRenderers(RenderPlayer player) {
        for (Map.Entry<Item, IAccessoryRenderer> entry : renderingMap.entrySet()) {
            ModelAccessory[] rendererAccessory = entry.getValue()
                .getModel();
            for (ModelAccessory renderer : rendererAccessory) {
                models.add(renderer);
                player.modelBipedMain.bipedHead.addChild(renderer.modelHead);
                player.modelBipedMain.bipedHeadwear.addChild(renderer.modelHeadwear);
                player.modelBipedMain.bipedBody.addChild(renderer.modelBody);
                player.modelBipedMain.bipedRightArm.addChild(renderer.modelRightArm);
                player.modelBipedMain.bipedLeftArm.addChild(renderer.modelLeftArm);
                player.modelBipedMain.bipedRightLeg.addChild(renderer.modelRightLeg);
                player.modelBipedMain.bipedLeftLeg.addChild(renderer.modelLeftLeg);
                player.modelBipedMain.bipedEars.addChild(renderer.modelEars);
                player.modelBipedMain.bipedCloak.addChild(renderer.modelCloak);
            }
        }
    }

    public void resetVisibility() {
        for (ModelAccessory renderer : models) {
            renderer.setHidden(true);
        }
    }

    public void showModels(ItemStack stack) {
        if (stack != null) {
            IAccessoryRenderer accessoryRenderer = getRenderer(stack.getItem());
            if (accessoryRenderer != null) {
                ModelAccessory[] models0 = accessoryRenderer.getModel();
                for (ModelAccessory renderer : models0) {
                    renderer.setHidden(false);
                }
            }
        }
    }

    public RenderPlayer player;

    public void handleRendering(EntityLivingBase entity, RenderPlayer renderer) {
        if (player != renderer) {
            registerRenderers(renderer);
            player = renderer;
        }
        if (entity instanceof EntityPlayer player) {
            if (Mods.baubles.isLoaded()) {
                InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack itemstack = inv.getStackInSlot(i);
                    if (itemstack != null) {
                        resetVisibility();
                        showModels(itemstack);
                    }
                }
            }
        }
    }
}
