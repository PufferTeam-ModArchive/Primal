package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.blocks.BlockPile;

public class FacingUtils {

    public static int getBlockX(int side, int x) {
        if (side == 4) {
            x--;
        }
        if (side == 5) {
            x++;
        }
        return x;
    }

    public static int getBlockY(int side, int y) {
        if (side == 0) {
            y--;
        }
        if (side == 1) {
            y++;
        }
        return y;
    }

    public static int getBlockZ(int side, int z) {
        if (side == 2) {
            z--;
        }
        if (side == 3) {
            z++;
        }
        return z;
    }

    public static int getBlockXR(int side, int x) {
        if (side == 5) {
            x--;
        }
        if (side == 4) {
            x++;
        }
        return x;
    }

    public static int getBlockYR(int side, int y) {
        if (side == 1) {
            y--;
        }
        if (side == 0) {
            y++;
        }
        return y;
    }

    public static int getBlockZR(int side, int z) {
        if (side == 3) {
            z--;
        }
        if (side == 2) {
            z++;
        }
        return z;
    }

    public static boolean hasSolidWallsTop(World world, int x, int y, int z) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            boolean isSolid = block
                .isSideSolid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())
                || (block instanceof BlockPile)
                || (block.getMaterial() == Material.fire);
            if (!isSolid) {
                return false;
            }

        }
        return true;
    }

    public static boolean hasSolidWalls(World world, int x, int y, int z) {
        for (ForgeDirection dir : ItemUtils.sideDirections) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (!block.isSideSolid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())) {
                return false;
            }
        }
        return true;
    }

    public static Block getBlockDirection(World world, int x, int y, int z, ForgeDirection... directions) {
        int offsetX = x;
        int offsetY = y;
        int offsetZ = z;
        for (ForgeDirection direction : directions) {
            offsetX += direction.offsetX;
            offsetY += direction.offsetY;
            offsetZ += direction.offsetZ;
        }
        return world.getBlock(offsetX, offsetY, offsetZ);
    }

    public static boolean setBlockDirection(World world, int x, int y, int z, Block block, int meta,
        ForgeDirection... directions) {
        int offsetX = x;
        int offsetY = y;
        int offsetZ = z;
        for (ForgeDirection direction : directions) {
            offsetX += direction.offsetX;
            offsetY += direction.offsetY;
            offsetZ += direction.offsetZ;
        }
        return world.setBlock(offsetX, offsetY, offsetZ, block, meta, 2);
    }

    public static ForgeDirection getDirectionFromFacing(int facingMeta) {
        return switch (facingMeta) {
            case 1 -> ForgeDirection.SOUTH;
            case 2 -> ForgeDirection.EAST;
            case 3 -> ForgeDirection.NORTH;
            case 4 -> ForgeDirection.WEST;
            default -> ForgeDirection.UNKNOWN;
        };
    }

    public static int getFacingFromDirection(int direction) {
        return switch (direction) {
            case 3 -> 3;
            case 5 -> 4;
            case 2 -> 1;
            case 4 -> 2;
            default -> 0;
        };
    }

    public static int getFacingMeta(int side, int axis) {
        if (axis == 0) {
            return switch (side) {
                case 3 -> 1;
                case 4 -> 4;
                case 2 -> 3;
                case 5 -> 2;
                default -> 0;
            };
        }
        if (axis == 1) {
            return switch (side) {
                case 0 -> 1;
                case 4 -> 4;
                case 1 -> 3;
                case 5 -> 2;
                default -> 0;
            };
        }
        if (axis == 2) {
            return switch (side) {
                case 3 -> 1;
                case 1 -> 4;
                case 2 -> 3;
                case 0 -> 2;
                default -> 0;
            };
        }
        return 0;
    }

    public static int getSimpleAxisFromFacing(int facingMeta) {
        return switch (facingMeta) {
            case 1, 3 -> 1;
            case 2, 4 -> 2;
            default -> 0;
        };
    }

    public static int getAxis(int side) {
        if (side == 0 || side == 1) {
            return 0;
        } else if (side == 2 || side == 3) {
            return 1;
        } else if (side == 4 || side == 5) {
            return 2;
        }
        return 0;
    }

    public static boolean isSidePositive(int side) {
        if (side == 1 || side == 3 || side == 5) {
            return true;
        }
        return false;
    }

    public static boolean isSimpleAxisConnected(int facingMeta, int facingMeta2) {
        return getSimpleAxisFromFacing(facingMeta) == getSimpleAxisFromFacing(facingMeta2);
    }

    public static MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn,
        boolean useLiquids) {
        return ItemDummy.instance.getMovingObjectPositionFromPlayerPublic(worldIn, playerIn, useLiquids);
    }

    public static int getDirectionXZYaw(int yaw) {
        if (yaw == 0) {
            return 1;
        } else if (yaw == 1) {
            return 4;
        } else if (yaw == 2) {
            return 3;
        } else if (yaw == 3) {
            return 2;
        }

        return 0;
    }

    public static int getMetaYaw(float rotationYaw) {
        int yaw = MathHelper.floor_double((double) (rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return getDirectionXZYaw(yaw);
    }

    public static int getMetaYawSide(float rotationYaw, int side) {
        if (side == 1 || side == 0) {
            return getMetaYaw(rotationYaw);
        } else {
            return getFacingFromDirection(side);
        }
    }

    public static float getFacingAngle(int meta) {
        float angle;
        switch (meta) {
            case 1:
                angle = (float) -Math.PI;
                break;
            case 2:
                angle = (float) (-Math.PI / 2);
                break;
            case 3:
                angle = 0.0F;
                break;
            case 4:
                angle = (float) (-3 * Math.PI / 2);
                break;
            default:
                angle = (float) (meta * Math.PI / 2);
                break;
        }
        return angle;
    }

    public static int getFacingAngleDegree(int meta) {
        int angle = 0;
        switch (meta) {
            case 1:
                angle = -180;
                break;
            case 2:
                angle = -90;
                break;
            case 3:
                angle = 0;
                break;
            case 4:
                angle = -270;
                break;
            default:
                angle = 90 * meta;
                break;
        }
        return angle;
    }
}
