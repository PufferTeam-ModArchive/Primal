package net.pufferlab.primal.client.renderer;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemBucketMeta;

import org.lwjgl.opengl.GL11;

public class RenderContainer {

    public static final double epsilon = 0.0D;

    public void renderContainer(ItemStack stack, IItemRenderer.ItemRenderType type) {
        if (stack == null) return;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        Tessellator tess = Tessellator.instance;

        Item item = stack.getItem();
        int meta = stack.getItemDamage();

        if (item instanceof ItemBucketMeta item2) {
            IIcon mask = item2.getIconFromDamageForRenderPass(meta, 1);
            if (meta >= item2.getFluidBlocks().length) return;
            Fluid fluid = item2.getFluidObjects()[meta];
            if (type == IItemRenderer.ItemRenderType.INVENTORY) {
                GL11.glScalef(16.0F, 16.0F, 16.0F);
                GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            }
            if (type == IItemRenderer.ItemRenderType.ENTITY) {
                GL11.glTranslatef(-0.5F, -0.3F, 0.0F);
            }

            IIcon blockIcon = fluid.getIcon();
            int color = fluid.getColor();

            if (fluid.getBlock() != null) {
                blockIcon = fluid.getBlock()
                    .getBlockTextureFromSide(0);

                color = fluid.getBlock()
                    .colorMultiplier(
                        Minecraft.getMinecraft().theWorld,
                        (int) Minecraft.getMinecraft().thePlayer.posX,
                        (int) Minecraft.getMinecraft().thePlayer.posY,
                        (int) Minecraft.getMinecraft().thePlayer.posZ);
            }

            renderIconWithMask(blockIcon, mask, color);

            bindItemAtlas();
            IIcon iicon = item2.getIconFromDamageForRenderPass(meta, 0);
            ItemRenderer.renderItemIn2D(
                tess,
                iicon.getMaxU(),
                iicon.getMinV(),
                iicon.getMinU(),
                iicon.getMaxV(),
                iicon.getIconWidth(),
                iicon.getIconHeight(),
                0.0625F);
            GL11.glDisable(GL11.GL_BLEND);
            TextureUtil.func_147945_b();
            GL11.glPopMatrix();
        }

    }

    public void renderIconWithMask(IIcon icon, IIcon mask, int color) {

        if ((mask == null) || (icon == null)) {
            return;
        }

        float r = Utils.getR(color);
        float g = Utils.getG(color);
        float b = Utils.getB(color);
        GL11.glColor4f(r, g, b, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);

        bindItemAtlas();
        drawIcon(mask);

        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDepthMask(false);

        bindBlockAtlas();
        drawIcon(icon);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawIcon(IIcon icon) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        tess.addVertexWithUV(0.0D, 1.0D, 0D + epsilon, icon.getMinU(), icon.getMaxV());
        tess.addVertexWithUV(1.0D, 1.0D, 0D + epsilon, icon.getMaxU(), icon.getMaxV());
        tess.addVertexWithUV(1.0D, 0.0D, 0D + epsilon, icon.getMaxU(), icon.getMinV());
        tess.addVertexWithUV(0.0D, 0.0D, 0D + epsilon, icon.getMinU(), icon.getMinV());

        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        tess.addVertexWithUV(0.0D, 1.0D, -0.0625D - epsilon, icon.getMinU(), icon.getMaxV());
        tess.addVertexWithUV(1.0D, 1.0D, -0.0625D - epsilon, icon.getMaxU(), icon.getMaxV());
        tess.addVertexWithUV(1.0D, 0.0D, -0.0625D - epsilon, icon.getMaxU(), icon.getMinV());
        tess.addVertexWithUV(0.0D, 0.0D, -0.0625D - epsilon, icon.getMinU(), icon.getMinV());
        tess.draw();
    }

    public static void bindBlockAtlas() {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
    }

    public static void bindItemAtlas() {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
    }
}
