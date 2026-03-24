package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelCampfireKindling extends ModelPrimal {

    public ModelRenderer kindling;

    public ModelCampfireKindling() {
        super(16);

        kindling = new ModelRenderer(this);
        kindling.setRotationPoint(0.0F, 0.0F, 1.0F);
        kindling.addBox(1, 1, -1.0F, 0.0F - 8F, -3.0F, 2, 1, 1, 0.0F);
        kindling.addBox(1, 1, -1.0F, 0.0F - 8F, 0.0F, 2, 1, 1, 0.0F);
        kindling.addBox(1, 0, 0.0F, 2.0F - 8F, -2.0F, 1, 1, 2, 0.0F);
        kindling.addBox(1, 0, -2.0F, 0.0F - 8F, -2.0F, 4, 2, 2, 0.0F);
        bb_main.addChild(kindling);
    }
}
