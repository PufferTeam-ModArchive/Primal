package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;

public class SoundTypePrimal extends Block.SoundType {

    public static final Block.SoundType soundTypeCharcoal = new SoundTypePrimal("charcoal", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeThatch = new SoundTypePrimal("thatch", 1.0F, 1.2F);

    public static final SoundTypePrimal soundScraping = new SoundTypePrimal("scraping", 1.0F, 1.2F);
    public static final SoundTypePrimal soundFaucetOpen = new SoundTypePrimal("open.faucet", 1.0F, 1.0F);
    public static final SoundTypePrimal soundFaucetClose = new SoundTypePrimal("close.faucet", 1.0F, 1.0F);
    public static final SoundTypePrimal soundQuernGrinding = new SoundTypePrimal("grinding.quern", 1.0F, 1.2F);

    public static final Random rand = new Random();

    public SoundTypePrimal(String name, float volume, float frequency) {
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
