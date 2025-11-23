package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

public class CharcoalPileHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.charcoal_pile;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.charcoalPileOreDicts;
    }

}
