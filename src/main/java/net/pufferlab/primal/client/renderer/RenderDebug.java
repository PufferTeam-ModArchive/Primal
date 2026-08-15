package net.pufferlab.primal.client.renderer;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.Mth;
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

    public static void handleDebugMenuText(EntityPlayer player, List<String> left, List<String> right) {
        instance.handleDebugDebugMenuTextImpl(player, left, right);
    }

    public void handleDebugDebugMenuTextImpl(EntityPlayer player, List<String> left, List<String> right) {
        int x = Mth.floor(player.posX);
        int z = Mth.floor(player.posZ);
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        int localX = x & 15;
        int localZ = z & 15;

        ChunkDataTF manager = ChunkDataTF.getClient(chunkX, chunkZ);

        float temperature = manager.getTemperature(localX, localZ);
        float rainfall = manager.getRainfall(localX, localZ);
        float vegetation = manager.getVegetation(localX, localZ);

        if (Primal.proxy.hasDebugMenu()) {
            if (Config.simplifyDebugMenu.getBoolean()) {
                left.removeIf(s -> {
                    if (s == null) return false;
                    return s.startsWith("[ChunkGen]") || s.startsWith("DynLights");
                });
                right.removeIf(s -> {
                    if (s == null) return false;
                    return s.startsWith("Minecraft Forge") || s.startsWith("FML")
                        || s.startsWith("MCP")
                        || s.startsWith("animationsMode")
                        || s.startsWith("LWJGL")
                        || s.contains("Angelica")
                        || s.startsWith("Viewport")
                        || s.startsWith("G:")
                        || s.startsWith("Transfer Queue")
                        || s.startsWith("Chunk Queues")
                        || s.startsWith("solid")
                        || s.startsWith("cutout_mipped")
                        || s.startsWith("translucent")
                        || s.startsWith("Sorting")
                        || s.startsWith("Chunk Workers")
                        || s.startsWith("MT Queue")
                        || s.startsWith("FFP")
                        || s.startsWith("Stream")
                        || s.startsWith("Dynamic Light");
                });
            }
            left.add("[Primal] ChunkDataTF");
            left.add("temperature: " + temperature);
            left.add("rainfall: " + rainfall);
            left.add("vegetation: " + vegetation);
        }
    }

}
