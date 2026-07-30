package net.pufferlab.primal.client.renderer;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class RenderDebug {

    public static final RenderDebug instance = new RenderDebug();

    public static boolean renderDebugData = false;

    public synchronized static void receiveDataDebug(int x, int z) {
        if (renderDebugData) {
            int x2 = x << 4;
            int y = 200;
            int z2 = z << 4;
            RenderBounds.addTemporaryAABB(AxisAlignedBB.getBoundingBox(x2, y, z2, x2 + 16, y, z2 + 16));
        }
    }

    public static void handleDebug(EntityPlayer player, List<String> list) {
        instance.handleDebugImpl(player, list);
    }

    public void handleDebugImpl(EntityPlayer player, List<String> list) {
        int x = Utils.floor(player.posX);
        int z = Utils.floor(player.posZ);
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        int localX = x & 15;
        int localZ = z & 15;

        ChunkDataTF manager = ChunkDataTF.getClient(chunkX, chunkZ);

        float rockness = manager.rockness[localX][localZ];
        float rainfall = manager.rainfall[localX][localZ];

        list.add("[Primal] ChunkDataTF");
        list.add("rockness: " + rockness + ", rainfall: " + rainfall);
    }

}
