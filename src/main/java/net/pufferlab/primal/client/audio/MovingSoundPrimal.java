package net.pufferlab.primal.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.pufferlab.primal.utils.SoundTypePrimal;

public abstract class MovingSoundPrimal extends MovingSound {

    protected MovingSoundPrimal(SoundTypePrimal sound) {
        super(sound.getResourceLocation());
    }
}
