package net.pufferlab.primal.client.utils.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class MCModelRenderer {

    public List<MCModelBox> modelBoxList = new ArrayList<>();
    public List<MCModelRenderer> childModels = new ArrayList<>();

    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;

    public MCModelRenderer(ModelBase base) {

    }

    public MCModelRenderer addBox(int U, int V, float x, float y, float z, int xWidth, int yHeight, int zDepth,
        float scaleFactor) {
        modelBoxList.add(new MCModelBox(this, U, V, x, y, z, xWidth, yHeight, zDepth, scaleFactor));
        return this;
    }

    public MCModelRenderer addChild(MCModelRenderer child) {
        this.childModels.add(child);
        return this;
    }

    public boolean syncBox(ModelRenderer renderer) {
        for (MCModelRenderer renderer0 : childModels) {
            ModelRenderer renderer2 = new ModelRenderer(renderer.baseModel);
            renderer0.syncBox(renderer2);
            renderer.addChild(renderer2);
        }
        renderer.rotateAngleX = rotateAngleX;
        renderer.rotateAngleY = rotateAngleY;
        renderer.rotateAngleZ = rotateAngleZ;
        renderer.cubeList.clear();
        if (modelBoxList.isEmpty()) {
            return false;
        } else {
            for (MCModelBox box : modelBoxList) {
                renderer.cubeList.add(
                    new ModelBox(
                        renderer,
                        box.U,
                        box.V,
                        box.x,
                        box.y,
                        box.z,
                        box.xWidth,
                        box.yHeight,
                        box.zDepth,
                        box.scaleFactor));
            }
            return true;
        }

    }
}
