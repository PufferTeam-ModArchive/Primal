package net.pufferlab.primal.client.audio;

import net.pufferlab.primal.blocks.SoundTypePrimal;
import net.pufferlab.primal.tileentities.TileEntityQuern;

public class SoundQuernGrinding extends MovingSoundPrimal {

    private final TileEntityQuern te;

    public SoundQuernGrinding(TileEntityQuern te) {
        super(SoundTypePrimal.soundQuernGrinding);
        this.te = te;
        this.repeat = true;
        this.field_147665_h = 0;
        this.volume = 0.8F;
        this.xPosF = te.xCoord + 0.5F;
        this.yPosF = te.yCoord + 0.5F;
        this.zPosF = te.zCoord + 0.5F;
    }

    @Override
    public void update() {
        if (!te.isMoving || te.isInvalid()) {
            this.donePlaying = true;
            return;
        }

        this.volume = te.getPercentageSpeed();
    }
}
