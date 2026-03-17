package net.pufferlab.primal.client.models.entities;

import net.minecraft.entity.Entity;

public class ModelStrawHat extends ModelBipedPrimal {

    public static final ModelStrawHat instance = new ModelStrawHat();

    public ModelStrawHat() {
        super();
        modelHeadwear.rotateAngleX = (float) Math.PI / 4;
        modelHeadwear.addBox(0, 0, 4.0F, -3.0F - 5, -7.0F, 3, 1, 14, 0.0F);
        modelHeadwear.addBox(0, 0, -7.0F, -3.0F - 5, -7.0F, 3, 1, 14, 0.0F);
        modelHeadwear.addBox(0, 26, -4.0F, -3.0F - 5, -7.0F, 8, 1, 3, 0.0F);
        modelHeadwear.addBox(0, 26, -4.0F, -3.0F - 5, 4.0F, 8, 1, 3, 0.0F);
        modelHeadwear.addBox(0, 15, -4.0F, -6.0F - 5, -4.0F, 8, 3, 8, 0.0F);

        addModels();
    }

    @Override
    public void transformModel(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_,
        float p_78088_5_, float p_78088_6_, float p_78088_7_, float partialTicks) {
        super.transformModel(
            p_78088_1_,
            p_78088_2_,
            p_78088_3_,
            p_78088_4_,
            p_78088_5_,
            p_78088_6_,
            p_78088_7_,
            partialTicks);
    }

    @Override
    public String getName() {
        return "models/straw_hat";
    }
}
