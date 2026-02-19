package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockCutSlab;
import net.pufferlab.primal.items.IMetaItem;
import net.pufferlab.primal.utils.CutUtils;
import net.pufferlab.primal.utils.FacingUtils;

public class ItemBlockCutSlab extends ItemBlock implements IMetaItem {

    private final boolean isFull;
    private final BlockCutSlab slabBlock;
    private final BlockCutSlab fullBlock;

    public ItemBlockCutSlab(Block block, BlockCutSlab blockSlab, BlockCutSlab blockFullSlab, Boolean isFull) {
        super(block);
        this.slabBlock = (BlockCutSlab) blockSlab;
        this.fullBlock = (BlockCutSlab) blockFullSlab;
        this.isFull = isFull;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (field_150939_a instanceof BlockCutSlab block2) {
            return block2.func_150002_b(stack.getItemDamage());
        }
        return null;
    }

    @Override
    public IIcon getIconFromDamage(int id) {
        return CutUtils.getIcon(2, id);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
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
                if (block instanceof BlockCutSlab block2) {
                    materialID = block2.getMaterialMeta(world, x, y, z);
                }
            }
            boolean flag = (i1 & 8) != 0;

            if ((side == 1 && !flag || side == 0 && flag) && materialID == stack.getItemDamage()) {
                if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, this.fullBlock, 0, 3)) {
                    Primal.proxy.packet.sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage());
                    Utils.playSound(world, x, y, z, this.fullBlock);
                    --stack.stackSize;
                    return true;
                }
                return false;
            } else if ((side == 1 && !flag || side == 0 && flag) && block == slabBlock) {
                if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, this.fullBlock, 1, 3)) {
                    if (i1 == 0) {
                        Primal.proxy.packet
                            .sendMaterialPacket(world, x, y, z, field_150939_a, materialID, stack.getItemDamage());
                    }
                    if (i1 == 8) {
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
            if (block instanceof BlockCutSlab block2) {
                materialID = block2.getMaterialMeta(world, x, y, z);
            }
        }

        if (materialID == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, this.fullBlock, 0, 3)) {
                Primal.proxy.packet.sendMaterialPacket(world, x, y, z, field_150939_a, stack.getItemDamage());
                Utils.playSound(world, x, y, z, this.fullBlock);
                --stack.stackSize;
            }
            return true;
        } else if (block == slabBlock) {
            if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, this.fullBlock, 1, 3)) {
                if (i1 == 0) {
                    Primal.proxy.packet
                        .sendMaterialPacket(world, x, y, z, field_150939_a, materialID, stack.getItemDamage());
                }
                if (i1 == 8) {
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
    public String[] getElements() {
        return CutUtils.getModNames();
    }

    @Override
    public String getElementName() {
        if(isFull) {
            return "double_slab";
        }
        return "slab";
    }

    @Override
    public boolean hasSuffix() {
        return true;
    }
}
