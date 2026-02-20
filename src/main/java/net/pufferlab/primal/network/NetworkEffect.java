package net.pufferlab.primal.network;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.network.packets.PacketEffect;

public class NetworkEffect {

    public void addBlockDestroyEffects(World worldObj, int x, int y, int z, Block block, int meta) {
        if (block instanceof IPrimalBlock block2) {
            if (!block.isAir(worldObj, x, y, z)) {
                byte b0 = block2.getBlockParticleAmount();

                for (int i1 = 0; i1 < b0; ++i1) {
                    for (int j1 = 0; j1 < b0; ++j1) {
                        for (int k1 = 0; k1 < b0; ++k1) {
                            double d0 = (double) x + ((double) i1 + 0.5D) / (double) b0;
                            double d1 = (double) y + ((double) j1 + 0.5D) / (double) b0;
                            double d2 = (double) z + ((double) k1 + 0.5D) / (double) b0;
                            Primal.proxy.renderFX(
                                new EntityDiggingFX(
                                    worldObj,
                                    d0,
                                    d1,
                                    d2,
                                    d0 - (double) x - 0.5D,
                                    d1 - (double) y - 0.5D,
                                    d2 - (double) z - 0.5D,
                                    block,
                                    meta).applyColourMultiplier(x, y, z));
                        }
                    }
                }
            }
        }
    }

    public void playAuxFX(World world, int x, int y, int z, Block block, int meta) {
        Primal.proxy.playPacketToClientNear(new PacketEffect(x, y, z, block, meta), world, x, y, z);
    }
}
