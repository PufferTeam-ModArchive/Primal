package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelArmor extends ModelBipedPrimal {

    public static float float1 = 1.0F;
    public static float float2 = 0.5F;

    public ModelArmor(String string) {
        this(getArmor(string));
    }

    public ModelArmor(int p_77032_2_) {
        super(p_77032_2_ == 2 ? float2 : float1);
        ModelBipedPrimal modelbiped = this;
        modelbiped.bipedHead.showModel = p_77032_2_ == 0;
        modelbiped.bipedHeadwear.showModel = p_77032_2_ == 0;
        modelbiped.bipedBody.showModel = p_77032_2_ == 1 || p_77032_2_ == 2;
        modelbiped.bipedRightArm.showModel = p_77032_2_ == 1;
        modelbiped.bipedLeftArm.showModel = p_77032_2_ == 1;
        modelbiped.bipedRightLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
        modelbiped.bipedLeftLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
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
}
