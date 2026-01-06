package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockMotion;
import net.pufferlab.primal.tileentities.TileEntityWaterWheel;

public class ItemBlockWaterWheel extends ItemBlockMotion {

    public ItemBlockWaterWheel(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        int axis = Utils.getAxis(side);
        boolean isValid = placeExtensionAt(world, x, y, z, axis, true);
        if (isValid) {
            if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
                return false;
            }

            placeExtensionAt(world, x, y, z, axis, false);

            if (world.getBlock(x, y, z) == field_150939_a) {
                field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
                field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
                if (field_150939_a instanceof BlockMotion block) {
                    block.onBlockSidePlacedBy(world, x, y, z, player, stack, side);
                }
            }
            return true;
        }
        return false;
    }

    public boolean placeExtensionAt(World world, int x, int y, int z, int axis, boolean check) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            for (ForgeDirection direction0 : ForgeDirection.VALID_DIRECTIONS) {
                if (Utils.getAxis(direction.ordinal()) != axis && Utils.getAxis(direction0.ordinal()) != axis) {
                    for (ForgeDirection direction2 : ForgeDirection.VALID_DIRECTIONS) {
                        if (direction2 != direction && direction2 != direction.getOpposite()
                            && Utils.getAxis(direction2.ordinal()) != axis) {
                            int x2 = x + direction.offsetX + direction2.offsetX;
                            int y2 = y + direction.offsetY + direction2.offsetY;
                            int z2 = z + direction.offsetZ + direction2.offsetZ;
                            if (direction2 == direction0) {
                                x2 = x + direction.offsetX;
                                y2 = y + direction.offsetY;
                                z2 = z + direction.offsetZ;
                            }
                            Block block = world.getBlock(x2, y2, z2);
                            if (check) {
                                if (block.getMaterial() != Material.air && block.getMaterial() != Material.water) {
                                    return false;
                                }
                            } else {
                                world.setBlock(x2, y2, z2, Registry.waterwheel, 1, 3);
                                TileEntity te = world.getTileEntity(x2, y2, z2);
                                if (te instanceof TileEntityWaterWheel tef) {
                                    tef.axisMeta = axis;
                                    tef.isExtension = true;
                                    tef.baseXCoord = x;
                                    tef.baseYCoord = y;
                                    tef.baseZCoord = z;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
