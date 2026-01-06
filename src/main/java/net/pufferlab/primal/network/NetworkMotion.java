package net.pufferlab.primal.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.tileentities.IMotion;

public class NetworkMotion {

    List<IMotion> tiles = new ArrayList<>();
    IMotion generator;
    float totalTorque;

    public NetworkMotion() {}

    public static void sendUpdate(IMotion te) {
        IMotion te2 = (IMotion) te.getWorld()
            .getTileEntity(te.getX(), te.getY(), te.getZ());
        generateNetwork(te2, false);
    }

    public static void sendStrongUpdate(IMotion te) {
        IMotion te2 = (IMotion) te.getWorld()
            .getTileEntity(te.getX(), te.getY(), te.getZ());
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            for (ForgeDirection direction2 : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity te3 = te2.getWorld()
                    .getTileEntity(
                        te2.getX() + direction.offsetX + direction2.offsetX,
                        te2.getY() + direction.offsetY + direction2.offsetY,
                        te2.getZ() + direction.offsetZ + direction2.offsetZ);
                if (te3 instanceof IMotion tef) {
                    tef.scheduleUpdate();
                }
            }
        }
        te2.scheduleUpdate();
    }

    public static void sendSpreadUpdate(IMotion te) {
        IMotion te2 = (IMotion) te.getWorld()
            .getTileEntity(te.getX(), te.getY(), te.getZ());
        generateNetwork(te2, true);
    }

    public static void generateNetwork(IMotion te, boolean spreadSpeed) {
        if (te == null) return;
        NetworkMotion network = new NetworkMotion();
        if (spreadSpeed) {
            network.generator = te;
        }
        recurseTile(network, te, spreadSpeed);

        if (!spreadSpeed) {
            for (IMotion tile : network.tiles) {
                if (tile.getGeneratedSpeed() != 0) {
                    if (network.generator == null) {
                        network.generator = tile;
                    } else {
                        if (Math.abs(network.generator.getGeneratedSpeed()) < Math.abs(tile.getGeneratedSpeed())) {
                            network.generator = tile;
                        }
                    }
                }
                network.totalTorque = network.totalTorque + tile.getTorque();
            }
            if (network.generator != null && network.totalTorque > 0) {
                network.generator.scheduleSpreadUpdate();
            } else {
                for (IMotion tile : network.tiles) {
                    tile.setSpeed(0);
                    tile.sendClientUpdate();
                }
            }
        } else {
            for (IMotion tile : network.tiles) {
                tile.setSpeed(network.generator.getGeneratedSpeed() * tile.getSpeedModifier());
                tile.sendClientUpdate();
            }
        }

    }

    public static void recurseTile(NetworkMotion network, IMotion currentTe, boolean spreadSpeed) {
        for (IMotion te : getConnectedTiles(network, currentTe, spreadSpeed)) {
            if (!network.tiles.contains(te)) {
                network.tiles.add(te);
                recurseTile(network, te, spreadSpeed);
            }
        }
    }

    public static List<IMotion> getConnectedTiles(NetworkMotion network, IMotion te, boolean spreadSpeed) {
        List<IMotion> connected = new ArrayList<>();
        connected.add(te);
        int connection = 0;
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            int ordinal = direction.ordinal();
            if (te != null) {
                int offsetX = direction.offsetX;
                int offsetY = direction.offsetY;
                int offsetZ = direction.offsetZ;
                if (te.hasConnection(ordinal)) {
                    TileEntity te2 = te.getWorld()
                        .getTileEntity(te.getX() + offsetX, te.getY() + offsetY, te.getZ() + offsetZ);
                    if (te2 instanceof IMotion tef) {
                        if (tef.hasConnection(
                            direction.getOpposite()
                                .ordinal())) {
                            if (spreadSpeed) {
                                tef.setSpeedModifier(te.getSpeedModifier());
                                tef.setHasOffset(te.hasOffset());
                            }
                            connected.add(tef);
                        }
                    }
                }
                if (te.hasGear(ordinal) && te.hasConnection(ordinal)) {
                    for (ForgeDirection direction2 : ForgeDirection.VALID_DIRECTIONS) {
                        if (direction2 != direction && direction2 != direction.getOpposite()) {
                            int offsetX2 = direction2.offsetX;
                            int offsetY2 = direction2.offsetY;
                            int offsetZ2 = direction2.offsetZ;
                            TileEntity te2 = te.getWorld()
                                .getTileEntity(
                                    te.getX() + offsetX + offsetX2,
                                    te.getY() + offsetY + offsetY2,
                                    te.getZ() + offsetZ + offsetZ2);
                            if (te2 instanceof IMotion tef) {
                                if (tef.hasGear(
                                    direction2.getOpposite()
                                        .ordinal())
                                    && tef.hasConnection(
                                        direction2.getOpposite()
                                            .ordinal())) {
                                    if (!network.tiles.contains(tef)) {
                                        connection++;
                                        if (connection <= 2) {
                                            if (spreadSpeed) {
                                                float modifier = getModifierFromSides(
                                                    direction,
                                                    direction2,
                                                    te.getSpeedModifier());
                                                tef.setSpeedModifier(modifier);
                                                tef.setHasOffset(!te.hasOffset());
                                            }
                                            connected.add(tef);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return connected;
    }

    public static float getModifierFromSides(ForgeDirection inSide, ForgeDirection outSide, float baseSign) {
        switch (inSide) {
            case DOWN:
                // Y- → X+ / Z+
                if (outSide == ForgeDirection.EAST || outSide == ForgeDirection.SOUTH) return -baseSign;
                break;

            case UP:
                // Y+ → X- / Z-
                if (outSide == ForgeDirection.WEST || outSide == ForgeDirection.NORTH) return -baseSign;
                break;

            case NORTH:
                // Z+ → X+ / Y+
                if (outSide == ForgeDirection.EAST || outSide == ForgeDirection.UP) return -baseSign;
                break;

            case SOUTH:
                // Z- → X- / Y-
                if (outSide == ForgeDirection.WEST || outSide == ForgeDirection.DOWN) return -baseSign;
                break;

            case WEST:
                // X- → Z+ / Y+
                if (outSide == ForgeDirection.SOUTH || outSide == ForgeDirection.UP) return -baseSign;
                break;

            case EAST:
                // X+ → Z- / Y-
                if (outSide == ForgeDirection.NORTH || outSide == ForgeDirection.DOWN) return -baseSign;
                break;

            default:
                break;
        }

        return baseSign;
    }

}
