package net.pufferlab.primal.client.models.blocks;

import net.pufferlab.primal.client.utils.ModelConfig;

public class ModelBloomery extends ModelPrimal {

    public ModelBloomery() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 3, 4.0F, 0.0F, -8.0F, 4, 13, 16, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(0, 3, -8.0F, 0.0F, -8.0F, 4, 13, 16, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(0, 32, -4.0F, 0.0F, -8.0F, 8, 13, 4, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(0, 32, -4.0F, 5.0F, 4.0F, 8, 8, 4, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(24, 32, 2.0F, 18.0F, -6.0F, 4, 5, 12, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(24, 32, -6.0F, 18.0F, -6.0F, 4, 5, 12, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(0, 0, -2.0F, 18.0F, 2.0F, 4, 5, 4, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(0, 0, -2.0F, 18.0F, -6.0F, 4, 5, 4, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(24, 0, 3.0F, 13.0F, -7.0F, 4, 5, 14, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(24, 0, -7.0F, 13.0F, -7.0F, 4, 5, 14, 0.0F, new ModelConfig().setNorth(false).setSouth(false));
        bb_main.addBox(0, 49, -3.0F, 13.0F, 3.0F, 6, 5, 4, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(0, 49, -3.0F, 13.0F, -7.0F, 6, 5, 4, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(16, 49, -7.0F, 13.0F, 7.0F, 14, 5, 0, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(23, 49, -6.0F, 18.0F, 6.0F, 12, 5, 0, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(16, 49, -8.0F, 5.0F, 8.0F, 16, 8, 0, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(16, 49, -7.0F, 13.0F, -7.0F, 14, 5, 0, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(23, 49, -6.0F, 18.0F, -6.0F, 12, 5, 0, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(16, 49, -8.0F, 0.0F, -8.0F, 16, 13, 0, 0.0F, new ModelConfig().setSouth(false));
        bb_main.addBox(28, 49, 4.0F, 0.0F, 8.0F, 4, 5, 0, 0.0F, new ModelConfig().setNorth(false));
        bb_main.addBox(28, 49, -8.0F, 0.0F, 8.0F, 4, 5, 0, 0.0F, new ModelConfig().setNorth(false));
        // spotless:on
    }

    @Override
    public String getName() {
        return "blocks/bloomery";
    }
}
