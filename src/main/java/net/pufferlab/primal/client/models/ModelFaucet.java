package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelFaucet extends ModelPrimal {

    public ModelRenderer valve;

    public ModelFaucet() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 6, -11.0F + 8F, 8.0F, 14.0F - 8F, 6, 6, 2, 0.0F);
        bb_main.addBox(0, 14, -10.0F + 8F, 9.0F, 10.0F - 8F, 4, 4, 4, 0.0F);
        bb_main.addBox(16, 6, -10.0F + 8F, 9.0F, 6.0F - 8F, 4, 4, 4, 0.0F);
        bb_main.addBox(16, 14, -10.0F + 8F, 6.0F, 6.0F - 8F, 4, 3, 4, 0.0F);
        bb_main.addBox(0, 22, -11.0F + 8F, 5.0F, 5.0F - 8F, 1, 2, 6, 0.0F);
        bb_main.addBox(0, 22, -6.0F + 8F, 5.0F, 5.0F - 8F, 1, 2, 6, 0.0F);
        bb_main.addBox(16, 21, -10.0F + 8F, 5.0F, 5.0F - 8F, 4, 2, 1, 0.0F);
        bb_main.addBox(16, 21, -10.0F + 8F, 5.0F, 10.0F - 8F, 4, 2, 1, 0.0F);
        bb_main.addBox(20, 3, -9.0F + 8F, 13.0F, 0.0f, 2, 1, 2, 0.0F);

        valve = new ModelRenderer(this);
        valve.setRotationPoint(0.0F, 0.0F, 0.0F);
        valve.addBox(0, 0, -3.0F, 14.01F, -2.0F, 6, 0, 6, 0.0F);
        // spotless:on
    }

    @Override
    public String getName() {
        return "blocks/faucet";
    }

    @Override
    public boolean invertRot() {
        return true;
    }
}
