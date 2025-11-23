package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

public class AshPileHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.ash_pile;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.ashPileOreDicts;
    }

}
