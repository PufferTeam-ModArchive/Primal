package net.pufferlab.primal.client.models;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelBarrel extends ModelPrimal {

    public ModelRenderer top;

    public ModelBarrel() {
        super(64);

        top = new ModelRenderer(this);
        top.setRotationPoint(0.0F, 0.0F, 0.0F);
        top.cubeList.add(new ModelBox(top, 0, 0, -5.0F, 14.0F, -5.0F, 10, 1, 10, 0.0F));
        bb_main.addChild(top);

        bb_main.cubeList.add(new ModelBox(bb_main, 22, 20, -6.0F, 0.0F, 5.0F, 12, 16, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 22, 20, -6.0F, 0.0F, -6.0F, 12, 16, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 11, 5.0F, 0.0F, -5.0F, 1, 16, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 11, -6.0F, 0.0F, -5.0F, 1, 16, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -5.0F, 1.0F, -5.0F, 10, 1, 10, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/barrel";
    }
}
