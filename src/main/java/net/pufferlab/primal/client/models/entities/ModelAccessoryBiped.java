package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelAccessoryBiped extends ModelAccessory {

    public ModelAccessoryBiped() {
        this(0.0F);
    }

    public ModelAccessoryBiped(float scale) {
        this(scale, 0.0F, 64, 32);
    }

    public static float epsilon = 0.005F;
    public static float float1 = 1.0F - epsilon;
    public static float float2 = 0.5F - epsilon;

    public ModelAccessoryBiped(String type) {
        this(getArmor(type));
    }

    public ModelAccessoryBiped(int p_77032_2_) {
        this(p_77032_2_ == 2 ? float2 : float1);
        ModelAccessoryBiped modelbiped = this;
        modelbiped.modelHead.showModel = p_77032_2_ == 0;
        modelbiped.modelHeadwear.showModel = p_77032_2_ == 0;
        modelbiped.modelBody.showModel = p_77032_2_ == 1 || p_77032_2_ == 2;
        modelbiped.modelRightArm.showModel = p_77032_2_ == 1;
        modelbiped.modelLeftArm.showModel = p_77032_2_ == 1;
        modelbiped.modelRightLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
        modelbiped.modelLeftLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
        modelbiped.modelCloak.showModel = false;
        modelbiped.modelEars.showModel = false;
    }

    public static int getArmor(String armorType) {
        return switch (armorType) {
            case Constants.helmet -> 0;
            case Constants.chestplate -> 1;
            case Constants.leggings -> 2;
            case Constants.boots -> 3;
            default -> 0;
        };
    }

    public ModelAccessoryBiped(float scale, float yOffset, int texWidth, int texHeight) {
        super(texWidth, texHeight);
        // Cloak
        modelCloak.setTextureOffset(0, 0);
        modelCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, scale);

        // Ears
        modelEars.setTextureOffset(24, 0);
        modelEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, scale);

        // Head
        modelHead.setTextureOffset(0, 0);
        modelHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);

        // Headwear
        modelHeadwear.setTextureOffset(32, 0);
        modelHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);

        // Body
        modelBody.setTextureOffset(16, 16);
        modelBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);

        // Right Arm
        modelRightArm.setTextureOffset(40, 16);
        modelRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);

        // Left Arm
        modelLeftArm.setTextureOffset(40, 16);
        modelLeftArm.mirror = true;
        modelLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);

        // Right Leg
        modelRightLeg.setTextureOffset(0, 16);
        modelRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);

        // Left Leg
        modelLeftLeg.setTextureOffset(0, 16);
        modelLeftLeg.mirror = true;
        modelLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
    }
}
