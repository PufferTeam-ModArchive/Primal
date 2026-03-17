package net.pufferlab.primal.client.models.entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.utils.client.MCModelRenderer;

public class ModelBipedPrimal extends ModelBiped implements IWearableModel {

    public MCModelRenderer modelHead;
    public MCModelRenderer modelHeadwear;
    public MCModelRenderer modelBody;
    public MCModelRenderer modelRightArm;
    public MCModelRenderer modelLeftArm;
    public MCModelRenderer modelRightLeg;
    public MCModelRenderer modelLeftLeg;
    public MCModelRenderer modelEars;
    public MCModelRenderer modelCloak;
    public static final List<ModelBiped> modelBipeds = new ArrayList<>();

    public ModelBipedPrimal(float t) {
        super(t);
        modelBipeds.add(this);
    }

    public ModelBipedPrimal() {
        super(0.0F, 0.0F, 64, 64);

        modelHead = new MCModelRenderer(this);
        modelHeadwear = new MCModelRenderer(this);
        modelBody = new MCModelRenderer(this);
        modelRightArm = new MCModelRenderer(this);
        modelLeftArm = new MCModelRenderer(this);
        modelRightLeg = new MCModelRenderer(this);
        modelLeftLeg = new MCModelRenderer(this);
        modelEars = new MCModelRenderer(this);
        modelCloak = new MCModelRenderer(this);

        modelBipeds.add(this);
    }

    public void addModels() {
        // Head
        boolean head = modelHead.syncBox(bipedHead);
        bipedHead.showModel = head;
        bipedHead.isHidden = !head;

        // Headwear
        boolean headwear = modelHeadwear.syncBox(bipedHeadwear);
        bipedHeadwear.showModel = headwear;
        bipedHeadwear.isHidden = !headwear;

        // Body
        boolean body = modelBody.syncBox(bipedBody);
        bipedBody.showModel = body;
        bipedBody.isHidden = !body;

        // Right Arm
        boolean rightArm = modelRightArm.syncBox(bipedRightArm);
        bipedRightArm.showModel = rightArm;
        bipedRightArm.isHidden = !rightArm;

        // Left Arm
        boolean leftArm = modelLeftArm.syncBox(bipedLeftArm);
        bipedLeftArm.showModel = leftArm;
        bipedLeftArm.isHidden = !leftArm;

        // Right Leg
        boolean rightLeg = modelRightLeg.syncBox(bipedRightLeg);
        bipedRightLeg.showModel = rightLeg;
        bipedRightLeg.isHidden = !rightLeg;

        // Left Leg
        boolean leftLeg = modelLeftLeg.syncBox(bipedLeftLeg);
        bipedLeftLeg.showModel = leftLeg;
        bipedLeftLeg.isHidden = !leftLeg;

        // Ears
        boolean ears = modelEars.syncBox(bipedEars);
        bipedEars.showModel = ears;
        bipedEars.isHidden = !ears;

        // Cloak
        boolean cloak = modelCloak.syncBox(bipedCloak);
        bipedCloak.showModel = cloak;
        bipedCloak.isHidden = !cloak;
    }

    public void transformModel(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_,
        float p_78088_5_, float p_78088_6_, float p_78088_7_, float partialTicks) {

    }

    public String getName() {
        return null;
    }

    public void bindTex() {
        Minecraft.getMinecraft().renderEngine
            .bindTexture(new ResourceLocation(Primal.MODID, "textures/" + getName() + ".png"));
    }
}
