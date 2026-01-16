package net.pufferlab.primal.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.pufferlab.primal.utils.SoundType;

public abstract class MovingSoundPrimal extends MovingSound {

    protected MovingSoundPrimal(SoundType sound) {
        super(sound.getResourceLocation());
    }
}
