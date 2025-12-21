package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelFaucet;
import net.pufferlab.primal.client.models.ModelValve;

import org.lwjgl.opengl.GL11;

public class ItemFaucetRenderer extends ItemPrimalRenderer {

    ModelFaucet modelFaucet = new ModelFaucet();
    ModelValve modelValve = new ModelValve();

    int faucetMeta = 0;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (item.getItemDamage() == faucetMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (item.getItemDamage() == faucetMeta) {
            return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItemDamage() == faucetMeta) {
            GL11.glPushMatrix();
            if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            }
            if (type == ItemRenderType.INVENTORY) {
                GL11.glScalef(1.3F, 1.3F, 1.3F);
                GL11.glTranslatef(-0.18F, -0.8F, 0.0F);
            }
            modelFaucet.bb_main.rotateAngleY = (float) Math.toRadians(-180);
            modelFaucet.render();
            modelValve.render();
            GL11.glPopMatrix();
        }
    }

}
