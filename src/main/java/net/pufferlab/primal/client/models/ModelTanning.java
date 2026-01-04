package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelTanning extends ModelPrimal {

    public ModelRenderer cube_r1;

    public ModelTanning() {
        super(64);

        // spotless:off
        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, -5.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.1745F, 0.0F, 0.0F);
        cube_r1.addBox(0, 0, -1.1F - 8F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        cube_r1.addBox(0, 0, 13.1F - 8F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        // spotless:on

    }

    @Override
    public String getName() {
        return "blocks/tanning_frame";
    }
}
