package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelRock extends ModelPrimal {

    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;

    public ModelRock() {
        super(16);

        // spotless:off
        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.1745F, 0.0F);
        cube_r1.addBox(0, 0, -5.0F, 0.0F, -6.0F, 3, 1, 3, 0.0F);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -0.3054F, 0.0F);
        cube_r2.addBox(0, 5, 3.0F, 0.0F, -1.0F, 1, 2, 4, 0.0F);
        cube_r2.addBox(2, 5, 2.0F, 0.0F, 3.0F, 2, 2, 1, 0.0F);
        cube_r2.addBox(0, 9, -1.0F, 0.0F, -1.0F, 4, 2, 4, 0.0F);
        cube_r2.addBox(2, 2, -1.0F, 0.0F, 3.0F, 3, 2, 1, 0.0F);
        // spotless:on

    }
}
