package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

public class GroundOreHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.ground_ore;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundOreOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }
}
