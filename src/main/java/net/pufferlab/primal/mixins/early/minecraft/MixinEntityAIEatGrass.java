package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAIEatGrass.class)
public class MixinEntityAIEatGrass {

    @Shadow
    private EntityLiving field_151500_b;
    @Shadow
    private World field_151501_c;
    @Shadow
    int field_151502_a;

    @Inject(method = "shouldExecute", at = @At("HEAD"), cancellable = true)
    public void shouldExecute$primal(CallbackInfoReturnable<Boolean> cir) {
        if (this.field_151500_b.getRNG()
            .nextInt(this.field_151500_b.isChild() ? 50 : 1000) != 0) {
            cir.setReturnValue(false);
        } else {
            int i = MathHelper.floor_double(this.field_151500_b.posX);
            int j = MathHelper.floor_double(this.field_151500_b.posY);
            int k = MathHelper.floor_double(this.field_151500_b.posZ);
            boolean isPossible = this.field_151501_c.getBlock(i, j, k) == Blocks.tallgrass
                && this.field_151501_c.getBlockMetadata(i, j, k) == 1 ? true
                    : Utils.isGrassBlock(this.field_151501_c.getBlock(i, j - 1, k));
            cir.setReturnValue(isPossible);
        }
    }

    @Inject(method = "updateTask", at = @At("HEAD"), cancellable = true)
    public void updateTask$primal(CallbackInfo cir) {
        this.field_151502_a = Math.max(0, this.field_151502_a - 1);

        if (this.field_151502_a == 4) {
            int i = MathHelper.floor_double(this.field_151500_b.posX);
            int j = MathHelper.floor_double(this.field_151500_b.posY);
            int k = MathHelper.floor_double(this.field_151500_b.posZ);

            if (this.field_151501_c.getBlock(i, j, k) == Blocks.tallgrass) {
                if (this.field_151501_c.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing")) {
                    this.field_151501_c.func_147480_a(i, j, k, false);
                }

                this.field_151500_b.eatGrassBonus();
            } else if (Utils.isGrassBlock(this.field_151501_c.getBlock(i, j - 1, k))
                && this.field_151501_c.getBlock(i, j - 1, k) != Blocks.grass) {
                    if (this.field_151501_c.getGameRules()
                        .getGameRuleBooleanValue("mobGriefing")) {
                        int meta = this.field_151501_c.getBlockMetadata(i, j - 1, k);
                        this.field_151501_c.playAuxSFX(2001, i, j - 1, k, Block.getIdFromBlock(Blocks.grass));
                        this.field_151501_c.setBlock(i, j - 1, k, Registry.dirt, meta, 2);
                    }

                    this.field_151500_b.eatGrassBonus();
                }
        }
    }
}
