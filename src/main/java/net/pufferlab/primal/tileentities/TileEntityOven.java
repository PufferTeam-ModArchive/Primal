package net.pufferlab.primal.tileentities;

import net.pufferlab.primal.Config;

public class TileEntityOven extends TileEntityCampfire {

    @Override
    public int getSmeltTime() {
        return Config.ovenSmeltTime.getInt();
    }
}
