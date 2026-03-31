package net.pufferlab.primal.blocks;

import net.pufferlab.primal.utils.CropType;

public class BlockBerryBush extends BlockCropsBush {

    public BlockBerryBush(CropType cropType) {
        super(cropType);
    }

    @Override
    public boolean needsFarmland() {
        return false;
    }
}
