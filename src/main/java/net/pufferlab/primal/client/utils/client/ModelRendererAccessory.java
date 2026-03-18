package net.pufferlab.primal.client.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.entities.ModelAccessory;

import org.lwjgl.opengl.GL11;

public class ModelRendererAccessory extends ModelRenderer {

    public ModelAccessory modelAccessory;

    public ModelRendererAccessory(ModelAccessory base) {
        super(base);
        this.modelAccessory = base;
    }

    public ModelRendererAccessory(ModelAccessory base, int i, int j) {
        super(base, i, j);
        this.modelAccessory = base;
    }

    public ModelRendererAccessory(ModelAccessory base, String str) {
        super(base, str);
        this.modelAccessory = base;
    }

    public void render(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);

        bindTex();
        super.render(scale);

        GL11.glPopAttrib();
    }

    public void renderWithRotation(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);

        bindTex();
        super.renderWithRotation(scale);

        GL11.glPopAttrib();
    }

    public void postRender(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);

        bindTex();
        super.postRender(scale);

        GL11.glPopAttrib();
    }

    public String getName() {
        return modelAccessory.getName();
    }

    public ModelRendererAccessory addBox(int U, int V, float x, float y, float z, int xWidth, int yHeight, int zDepth,
        float scaleFactor) {
        this.cubeList.add(new ModelBox(this, U, V, x, y, z, xWidth, yHeight, zDepth, scaleFactor));
        return this;
    }

    public void bindTex() {
        Minecraft.getMinecraft().renderEngine
            .bindTexture(new ResourceLocation(Primal.MODID, "textures/" + getName() + ".png"));
    }
}
