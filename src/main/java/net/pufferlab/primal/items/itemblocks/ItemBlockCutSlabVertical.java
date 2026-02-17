package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockCutSlabVertical;
import net.pufferlab.primal.utils.CutUtils;
import net.pufferlab.primal.utils.FacingUtils;

public class ItemBlockCutSlabVertical extends ItemBlock {

    private final boolean isFull;
    private final BlockCutSlabVertical slabBlock;
    private final BlockCutSlabVertical fullBlock;

    public ItemBlockCutSlabVertical(Block block, BlockCutSlabVertical blockSlab, BlockCutSlabVertical blockFullSlab,
        Boolean isFull) {
        super(block);
        this.slabBlock = (BlockCutSlabVertical) blockSlab;
        this.fullBlock = (BlockCutSlabVertical) blockFullSlab;
        this.isFull = isFull;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (field_150939_a instanceof BlockCutSlabVertical block2) {
            return block2.func_150002_b(stack.getItemDamage());
        }
        return null;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (this.isFull) {
            return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        } else {
            Block block = world.getBlock(x, y, z);
            int i1 = world.getBlockMetadata(x, y, z);
            int materialID = -1;
            if (block == this.slabBlock) {
                if (block instanceof BlockCutSlabVertical block2) {
                    materialID = block2.getMaterialMeta(world, x, y, z);
                }
            }

            boolean b = (side == 5 && i1 == 1) || (side == 2 && i1 == 2)
                || (side == 4 && i1 == 0)
                || (side == 3 && i1 == 3);
            int num = 0;
            if (i1 == 2 || i1 == 3) {
                num = 1;
            }
            if (b && materialID == stack.getItemDamage()) {
                if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, this.fullBlock, num, 3)) {
                    Primal.proxy.packet.sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage());
                    Utils.playSound(world, x, y, z, this.fullBlock);
                    --stack.stackSize;
                    return true;
                }
                return false;
            } else if (b && block == slabBlock) {
                if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, this.fullBlock, num + 2, 3)) {
                    if (i1 == 1 || i1 == 3) {
                        Primal.proxy.packet
                            .sendMaterialPacket(world, x, y, z, field_150939_a, materialID, stack.getItemDamage());
                    }
                    if (i1 == 2 || i1 == 0) {
                        Primal.proxy.packet
                            .sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage(), materialID);
                    }
                    Utils.playSound(world, x, y, z, this.fullBlock);
                    --stack.stackSize;
                    return true;
                }
                return false;
            } else {
                return this.func_150946_a(stack, player, world, x, y, z, side) ? true
                    : super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
            }
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
            Primal.proxy.packet.sendMaterialPacket(
                world,
                x,
                y,
                z,
                field_150939_a,
                player.getHeldItem()
                    .getItemDamage());
        }

        return true;
    }

    @Override
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
        boolean success = super.func_150936_a(world, x, y, z, side, player, stack);
        x = FacingUtils.getBlockX(side, x);
        y = FacingUtils.getBlockY(side, y);
        z = FacingUtils.getBlockZ(side, z);

        Block block = world.getBlock(x, y, z);
        if (block == this.slabBlock) {
            success = true;
        }
        return success;
    }

    private boolean func_150946_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        x = FacingUtils.getBlockX(side, x);
        y = FacingUtils.getBlockY(side, y);
        z = FacingUtils.getBlockZ(side, z);

        Block block = world.getBlock(x, y, z);
        int i1 = world.getBlockMetadata(x, y, z);
        int materialID = -1;
        if (block == this.slabBlock) {
            if (block instanceof BlockCutSlabVertical block2) {
                materialID = block2.getMaterialMeta(world, x, y, z);
            }
        }

        int num = 0;
        if (i1 == 2 || i1 == 3) {
            num = 1;
        }
        if (materialID == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, this.fullBlock, num, 3)) {
                Primal.proxy.packet.sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage());
                Utils.playSound(world, x, y, z, this.fullBlock);
                --stack.stackSize;
            }
            return true;
        } else if (block == slabBlock) {
            if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, this.fullBlock, num + 2, 3)) {
                if (i1 == 1 || i1 == 3) {
                    Primal.proxy.packet
                        .sendMaterialPacket(world, x, y, z, field_150939_a, materialID, stack.getItemDamage());
                }
                if (i1 == 2 || i1 == 0) {
                    Primal.proxy.packet
                        .sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage(), materialID);
                }
                Utils.playSound(world, x, y, z, this.fullBlock);
                --stack.stackSize;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IIcon getIconFromDamage(int id) {
        return CutUtils.getIcon(2, id);
    }

}
