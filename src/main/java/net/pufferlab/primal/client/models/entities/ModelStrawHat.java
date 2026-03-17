package net.pufferlab.primal.client.models.entities;

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
    public String getName() {
        return "models/straw_hat";
    }
}
