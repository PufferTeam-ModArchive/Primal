package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockBarrel;
import net.pufferlab.primal.blocks.BlockContainerPrimal;
import net.pufferlab.primal.blocks.BlockLargeVessel;

public class ItemBlockPrimal extends ItemBlock {

    public ItemBlockPrimal(Block block) {
        super(block);
        if (block instanceof BlockLargeVessel || block instanceof BlockBarrel) {
            this.setMaxStackSize(1);
        }
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_,
        int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);

        if (block == Blocks.snow_layer && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 7) < 1) {
            p_77648_7_ = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass
            && block != Blocks.deadbush
            && !block.isReplaceable(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
                if (p_77648_7_ == 0) {
                    --p_77648_5_;
                }

                if (p_77648_7_ == 1) {
                    ++p_77648_5_;
                }

                if (p_77648_7_ == 2) {
                    --p_77648_6_;
                }

                if (p_77648_7_ == 3) {
                    ++p_77648_6_;
                }

                if (p_77648_7_ == 4) {
                    --p_77648_4_;
                }

                if (p_77648_7_ == 5) {
                    ++p_77648_4_;
                }
            }

        if (p_77648_1_.stackSize == 0) {
            return false;
        } else if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        } else if (p_77648_5_ == 255 && this.field_150939_a.getMaterial()
            .isSolid()) {
                return false;
            } else if (p_77648_3_.canPlaceEntityOnSide(
                this.field_150939_a,
                p_77648_4_,
                p_77648_5_,
                p_77648_6_,
                false,
                p_77648_7_,
                p_77648_2_,
                p_77648_1_)) {
                    int i1 = this.getMetadata(p_77648_1_.getItemDamage());
                    int j1 = this.field_150939_a.onBlockPlaced(
                        p_77648_3_,
                        p_77648_4_,
                        p_77648_5_,
                        p_77648_6_,
                        p_77648_7_,
                        p_77648_8_,
                        p_77648_9_,
                        p_77648_10_,
                        i1);

                    if (placeBlockAt(
                        p_77648_1_,
                        p_77648_2_,
                        p_77648_3_,
                        p_77648_4_,
                        p_77648_5_,
                        p_77648_6_,
                        p_77648_7_,
                        p_77648_8_,
                        p_77648_9_,
                        p_77648_10_,
                        j1)) {
                        p_77648_3_.playSoundEffect(
                            (double) ((float) p_77648_4_ + 0.5F),
                            (double) ((float) p_77648_5_ + 0.5F),
                            (double) ((float) p_77648_6_ + 0.5F),
                            this.field_150939_a.stepSound.func_150496_b(),
                            (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F,
                            this.field_150939_a.stepSound.getPitch() * 0.8F);
                        --p_77648_1_.stackSize;
                        return true;
                    }

                    return false;
                } else {
                    return false;
                }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        if (cancelPlacement(stack)) return false;

        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            placeBlock(world, x, y, z, player, stack, metadata, side);
        }

        return true;
    }

    public void placeBlock(World world, int x, int y, int z, EntityPlayer player, ItemStack stack, int metadata,
        int side) {
        field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
        field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
        if (field_150939_a instanceof BlockContainerPrimal block) {
            block.onBlockSidePlacedBy(world, x, y, z, player, stack, side);
        }
        Primal.proxy.packet.sendChunkUpdate(world);
    }

    public boolean cancelPlacement(ItemStack stack) {
        return false;
    }
}
