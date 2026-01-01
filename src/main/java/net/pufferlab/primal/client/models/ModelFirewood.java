package net.pufferlab.primal.client.models;

public class ModelFirewood extends ModelPrimal {

    public ModelFirewood() {
        super(64);
        bb_main.addBox(0, 0, 0.0F, -1.0F, -8.0F, 5, 5, 16, 0.0F);
    }

    @Override
    public String getName() {
        return "items/firewood";
    }

}
