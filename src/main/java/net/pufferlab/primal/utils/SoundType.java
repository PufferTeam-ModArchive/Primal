package net.pufferlab.primal.utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;

public class SoundType extends Block.SoundType {

    public static final Block.SoundType soundTypeCharcoal = new SoundType("charcoal", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeThatch = new SoundType("thatch", 1.0F, 1.2F);

    public static final SoundType soundScraping = new SoundType("scraping", 1.0F, 1.2F);
    public static final SoundType soundFaucetOpen = new SoundType("open.faucet", 1.0F, 1.0F);
    public static final SoundType soundFaucetClose = new SoundType("close.faucet", 1.0F, 1.0F);
    public static final SoundType soundQuernGrinding = new SoundType("grinding.quern", 1.0F, 1.2F);

    public static final Random rand = new Random();

    public SoundType(String name, float volume, float frequency) {
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

    public String getPath() {
        return Primal.MODID + ":" + this.soundName;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(getPath());
    }
}
