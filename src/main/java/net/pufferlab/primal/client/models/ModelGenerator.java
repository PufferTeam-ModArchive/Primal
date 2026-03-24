package net.pufferlab.primal.client.models;

public class ModelGenerator extends ModelPrimal {

    public ModelGenerator() {
        super(32);

        bb_main.addBox(0, 0, -4.0F, 1.0F - 8F, -4.0F, 8, 14, 8, 0.0F);
    }

    @Override
    public String getName() {
        return "blocks/generator";
    }
}
