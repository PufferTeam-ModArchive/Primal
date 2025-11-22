package net.pufferlab.primal.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public abstract class ModelMod extends ModelBase {

    public final ModelRenderer bb_main;

    public ModelMod(int size) {
        textureWidth = size;
        textureHeight = size;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render() {
        bindTex();
        bb_main.render(Constants.modelConst);
    }

    public String getName() {
        return null;
    }

    public void bindTex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(Primal.asResource("textures/items/" + getName() + ".png"));
    }
}
