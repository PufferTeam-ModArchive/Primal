package net.pufferlab.primal.client.models;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.utils.ModelBase;
import net.pufferlab.primal.client.utils.ModelRenderer;

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

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int index) {
        modelTESS
            .renderBlock(renderblocks, tess, block, bb_main, Constants.modelConst, x, y, z, 0.0F, 0.0F, 0.0F, index);
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, double offsetX,
        double offsetY, double offsetZ, int index) {
        modelTESS.renderBlock(
            renderblocks,
            tess,
            block,
            bb_main,
            Constants.modelConst,
            x,
            y,
            z,
            offsetX,
            offsetY,
            offsetZ,
            index);
    }

    public String getName() {
        return null;
    }

    public void bindTex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(Primal.asResource("textures/" + getName() + ".png"));
    }

    double add = 0;

    public void setFacing(int meta) {
        int meta2 = meta + 1;
        if (invertRot()) {
            add = Math.PI;
        }
        bb_main.rotateAngleYGlobal = (float) ((Math.toRadians(90 * meta2) + add) % 360);
    }

    public boolean invertRot() {
        return false;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
