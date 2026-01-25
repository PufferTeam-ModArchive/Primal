package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

public class GroundRockHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.ground_rock;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundRockOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }
}
