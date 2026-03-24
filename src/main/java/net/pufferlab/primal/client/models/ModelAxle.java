package net.pufferlab.primal.client.models;

public class ModelAxle extends ModelPrimal {

    public ModelAxle() {
        super(32);

        bb_main.addBox(0, 0, -2.0F, -8.0F, -2.0F, 4, 16, 4, 0.0F);
    }

    @Override
    public String getName() {
        return "blocks/axle";
    }
}
