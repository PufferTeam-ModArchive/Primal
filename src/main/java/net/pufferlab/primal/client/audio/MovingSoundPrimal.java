package net.pufferlab.primal.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.pufferlab.primal.blocks.SoundTypePrimal;

public abstract class MovingSoundPrimal extends MovingSound {

    protected MovingSoundPrimal(SoundTypePrimal sound) {
        super(sound.getResourceLocation());
    }
}
