package net.pufferlab.primal.world;

import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.pufferlab.primal.Primal;

public class ChunkLoadingCallback implements ForgeChunkManager.LoadingCallback {

    public static void registerCallback() {
        ForgeChunkManager.setForcedChunkLoadingCallback(Primal.instance, new ChunkLoadingCallback());
    }

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        for (ForgeChunkManager.Ticket ticket : tickets) {
            // restore forced chunks here
        }
    }
}
