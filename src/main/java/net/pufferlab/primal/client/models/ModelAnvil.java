package net.pufferlab.primal.client.models;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelAnvil extends ModelPrimal {

    public ModelRenderer anvil;

    public ModelAnvil() {
        super(64);

        anvil = new ModelRenderer(this);
        anvil.setRotationPoint(0.0F, 0.0F, 0.0F);
        anvil.rotateAngleY = (float) Math.PI / 2;
        anvil.addBox(0, 16, -4.0F, 0.0F, -6.0F, 8, 2, 11, 0.0F);
        anvil.addBox(0, 29, -2.0F, 2.0F, -4.0F, 4, 6, 7, 0.0F);
        anvil.addBox(0, 0, -3.0F, 8.0F, -8.0F, 6, 4, 12, 0.0F);
        anvil.addBox(22, 29, -3.0F, 8.0F, 4.0F, 6, 3, 2, 0.0F);
        anvil.addBox(22, 38, -2.0F, 9.0F, 7.0F, 4, 2, 1, 0.0F);
        anvil.addBox(22, 34, -2.0F, 7.0F, -7.0F, 4, 1, 3, 0.0F);
        anvil.addBox(38, 18, -2.0F, 7.0F, 3.0F, 4, 1, 2, 0.0F);
        anvil.addBox(36, 0, -5.0F, 0.0F, 1.0F, 1, 2, 4, 0.0F);
        anvil.addBox(36, 6, -5.0F, 0.0F, -6.0F, 1, 2, 4, 0.0F);
        anvil.addBox(36, 34, 4.0F, 0.0F, -6.0F, 1, 2, 4, 0.0F);
        anvil.addBox(38, 12, 4.0F, 0.0F, 1.0F, 1, 2, 4, 0.0F);
        anvil.addBox(38, 21, -2.0F, 8.0F, 6.0F, 4, 3, 1, 0.0F);
        bb_main.addChild(anvil);
    }

    @Override
    public String getName() {
        int index = Math.min(this.type, Constants.anvilMetalTypes.length);
        return "blocks/" + Constants.anvilMetalTypes[index].name + "_anvil";
    }
}
