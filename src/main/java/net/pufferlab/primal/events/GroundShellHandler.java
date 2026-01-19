package net.pufferlab.primal.events;

import static net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundShell;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundcover;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GroundShellHandler extends PileHandler {

    public static WorldGenGroundcover worldGenShell = new WorldGenGroundShell();
    public static int shellPerChunk = 3;
    public static boolean enableShell = Config.shellWorldGen.getDefaultBoolean();

    public GroundShellHandler() {
        enableShell = Config.shellWorldGen.getBoolean();
    }

    @Override
    public Block getPileBlock() {
        return Registry.ground_shell;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundShellOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }

    @SubscribeEvent
    public void playerInteractEventHandler2(PlayerInteractEvent event) {
        if (event.action == RIGHT_CLICK_BLOCK) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (block == getPileBlock()) {
                event.world.setBlockToAir(event.x, event.y, event.z);
                event.entityPlayer.swingItem();
            }
        }
    }

    @SubscribeEvent
    public void onDecoratePost(DecorateBiomeEvent.Post event) {
        World world = event.world;
        Random rand = event.rand;
        int x = event.chunkX;
        int z = event.chunkZ;

        BiomeGenBase biome = world.getBiomeGenForCoords(x + 8, z + 8);

        if (enableShell && Utils.isBeachBiome(biome)) {
            for (int i = 0; i < shellPerChunk; i++) {
                int px = x + rand.nextInt(16) + 8;
                int pz = z + rand.nextInt(16) + 8;
                int py = world.getHeightValue(px, pz);
                Block block = world.getBlock(px, py - 1, pz);
                if (block == Blocks.sand) {
                    worldGenShell.generate(world, rand, px, py, pz);
                }
            }
        }
    }
}
