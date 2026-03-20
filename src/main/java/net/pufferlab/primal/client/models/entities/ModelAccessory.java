package net.pufferlab.primal.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.pufferlab.primal.client.utils.vanilla.ModelRendererAccessory;

public class ModelAccessory extends ModelBase {

    public ModelRendererAccessory modelHead;
    public ModelRendererAccessory modelHeadwear;
    public ModelRendererAccessory modelBody;
    public ModelRendererAccessory modelRightArm;
    public ModelRendererAccessory modelLeftArm;
    public ModelRendererAccessory modelRightLeg;
    public ModelRendererAccessory modelLeftLeg;
    public ModelRendererAccessory modelEars;
    public ModelRendererAccessory modelCloak;

    private ModelRendererAccessory[] allParts;

    public ModelAccessory(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;

        modelHead = new ModelRendererAccessory(this);
        modelHeadwear = new ModelRendererAccessory(this);
        modelBody = new ModelRendererAccessory(this);
        modelRightArm = new ModelRendererAccessory(this);
        modelLeftArm = new ModelRendererAccessory(this);
        modelRightLeg = new ModelRendererAccessory(this);
        modelLeftLeg = new ModelRendererAccessory(this);
        modelEars = new ModelRendererAccessory(this);
        modelCloak = new ModelRendererAccessory(this);

        allParts = new ModelRendererAccessory[] { modelHead, modelHeadwear, modelBody, modelRightArm, modelLeftArm,
            modelRightLeg, modelLeftLeg, modelEars, modelCloak };
    }

    public void setHidden(boolean state) {
        for (ModelRendererAccessory part : allParts) {
            part.isHidden = state;
        }
    }

    public String getName() {
        return null;
    }
}
