package net.pufferlab.primal.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.world.gen.WorldGenGround;

import java.util.Random;

public class GroundRockHandler extends PileHandler {

    public static WorldGenGround worldGenRock = new WorldGenGround(Registry.ground_rock, 0);
    @Override
    public Block getPileBlock() {
        return Registry.ground_rock;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundRockOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }

    @SubscribeEvent
    public void onDecoratePost(DecorateBiomeEvent.Post event) {
        World world = event.world;
        Random rand = event.rand;
        int x = event.chunkX;
        int z = event.chunkZ;

        BiomeGenBase biome = world.getBiomeGenForCoords(x + 8, z + 8);

        for (int i = 0; i < 1; i++) {
            int px = x + rand.nextInt(16) + 8;
            int pz = z + rand.nextInt(16) + 8;
            int py = world.getHeightValue(px, pz);
            worldGenRock.generate(world, rand, px, py, pz);
        }
    }
}
