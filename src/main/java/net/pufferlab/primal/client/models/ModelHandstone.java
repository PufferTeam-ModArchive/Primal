package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelHandstone extends ModelPrimal {

    public ModelRenderer handle;
    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;
    public ModelRenderer cube_r3;
    public ModelRenderer cube_r4;

    public ModelHandstone() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 26, -5.0F, 3.0F, -5.0F, 10, 4, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 51, 1.0F, 7.0F, -5.0F, 4, 1, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 40, -5.0F, 7.0F, -5.0F, 4, 1, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 29, 46, -1.0F, 7.0F, -5.0F, 2, 1, 4, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 41, 46, -1.0F, 7.0F, 1.0F, 2, 1, 4, 0.0F));

        handle = new ModelRenderer(this);
        handle.setRotationPoint(0.0F, 5.0F, 0.0F);
        bb_main.addChild(handle);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(1.0F, 0.0F, -1.0F);
        handle.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(1.0F, 0.0F, 1.0F);
        handle.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -2.3562F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F));

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(-1.0F, 0.0F, -1.0F);
        handle.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.7854F, 0.0F);
        cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F));

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(-1.0F, 0.0F, 1.0F);
        handle.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 2.3562F, 0.0F);
        cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 0, -0.7071F, 0.0F, 6.7782F, 1, 1, 4, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/quern";
    }
}
