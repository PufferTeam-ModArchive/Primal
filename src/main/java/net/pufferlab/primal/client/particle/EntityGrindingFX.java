package net.pufferlab.primal.client.particle;

import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityGrindingFX extends EntityBreakingFX {

    public EntityGrindingFX(World p_i1195_1_, double p_i1195_2_, double p_i1195_4_, double p_i1195_6_, Item p_i1195_8_,
        int meta) {
        super(p_i1195_1_, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_, meta);
        this.particleScale /= 3F;
        this.motionX *= 0.4D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.4D;
    }
}
