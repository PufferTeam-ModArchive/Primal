package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

public class GroundShellHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.ground_shell;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundShellOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }
}
