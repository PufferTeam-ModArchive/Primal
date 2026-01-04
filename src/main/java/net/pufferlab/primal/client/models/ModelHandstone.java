package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelQuad;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelHandstone extends ModelPrimal {

    public ModelRenderer handle;
    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;
    public ModelRenderer cube_r3;
    public ModelRenderer cube_r4;

    public ModelHandstone() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 26, -5.0F, 3.0F, -5.0F, 10, 4, 10, 0.0F);
        bb_main.addBox(34, 54, -5.0F, 8.0F, -5.0F, 10, 0, 10, 0.0F, new ModelQuad(true).setUp(true));
        bb_main.addBox(0, 51, 1.0F, 7.0F, -5.0F, 4, 1, 10, 0.0F);
        bb_main.addBox(0, 40, -5.0F, 7.0F, -5.0F, 4, 1, 10, 0.0F);
        bb_main.addBox(29, 46, -1.0F, 7.0F, -5.0F, 2, 1, 4, 0.0F, new ModelQuad().setEast(false).setWest(false));
        bb_main.addBox(41, 46, -1.0F, 7.0F, 1.0F, 2, 1, 4, 0.0F, new ModelQuad().setEast(false).setWest(false));

        handle = new ModelRenderer(this);
        handle.setRotationPoint(0.0F, 5.0F, 0.0F);
        bb_main.addChild(handle);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(1.0F, 0.0F, -1.0F);
        handle.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
        cube_r1.addBox(0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(1.0F, 0.0F, 1.0F);
        handle.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -2.3562F, 0.0F);
        cube_r2.addBox(0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(-1.0F, 0.0F, -1.0F);
        handle.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.7854F, 0.0F);
        cube_r3.addBox(0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(-1.0F, 0.0F, 1.0F);
        handle.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 2.3562F, 0.0F);
        cube_r4.addBox(0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F);
        // spotless:on
    }

    @Override
    public String getName() {
        return "blocks/handstone";
    }
}
