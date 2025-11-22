package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.pufferlab.primal.Primal;

public class SoundTypeCustom extends Block.SoundType {

    public static final Block.SoundType soundTypeCharcoal = new SoundTypeCustom("charcoal", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeThatch = new SoundTypeCustom("thatch", 1.0F, 1.2F);

    public static final Random rand = new Random();

    public SoundTypeCustom(String name, float volume, float frequency) {
        super(name, volume, frequency);
    }

    public float getPitch() {
        return (frequency / 1.3F) + (rand.nextFloat());
    }

    public String getBreakSound() {
        return Primal.MODID + ":dig." + this.soundName;
    }

    public String getStepResourcePath() {
        return Primal.MODID + ":step." + this.soundName;
    }
}
