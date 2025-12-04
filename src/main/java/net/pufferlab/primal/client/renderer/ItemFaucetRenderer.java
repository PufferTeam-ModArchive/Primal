package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.pufferlab.primal.client.models.ModelFaucet;

import org.lwjgl.opengl.GL11;

public class ItemFaucetRenderer implements IItemRenderer {

    ModelFaucet modelFaucet = new ModelFaucet();

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
                GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            }
            modelFaucet.setFacing(3);
            modelFaucet.render();
            GL11.glPopMatrix();
        }
    }

}
