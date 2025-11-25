package net.pufferlab.primal.client.models;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ModelTESS;

public abstract class ModelPrimal extends ModelBase {

    ModelTESS modelTESS = new ModelTESS();

    public final ModelRenderer bb_main;

    public ModelPrimal(int size) {
        textureWidth = size;
        textureHeight = size;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render() {
        bindTex();
        bb_main.render(Constants.modelConst);
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int meta) {
        modelTESS.renderBlock(renderblocks, tess, block, bb_main, Constants.modelConst, x, y, z, meta);
    }

    public String getName() {
        return null;
    }

    public void bindTex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(Primal.asResource("textures/items/" + getName() + ".png"));
    }
}
