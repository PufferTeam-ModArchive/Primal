package net.pufferlab.primal.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.tileentities.ITile;
import net.pufferlab.primal.tileentities.TileEntityFarmland;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.PositionUtils;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;

public class NetworkMoisture {

    List<ITile> tiles = new ArrayList<>();
    TLongList waterBlocks = new TLongArrayList();

    public static void generateNetwork(ITile te) {
        if (te == null) return;
        NetworkMoisture network = new NetworkMoisture();
        network.recurseTile(te);

        for (ITile tile : network.tiles) {
            if (tile instanceof TileEntityFarmland tef) {
                float best = 0.0F;

                for (int i = 0; i < network.waterBlocks.size(); i++) {
                    long coord = network.waterBlocks.get(i);
                    float moisture = getMoisture(tef, coord);

                    if (moisture > best) {
                        best = moisture;
                    }
                }

                tef.setMoisture(best);
            }
        }

    }

    private static float getMoisture(TileEntityFarmland tef, long coord) {
        int waterX = PositionUtils.unpackX(coord);
        int waterZ = PositionUtils.unpackZ(coord);

        int dx = Math.abs(tef.getX() - waterX);
        int dz = Math.abs(tef.getZ() - waterZ);

        float dist = Math.max(dx, dz);

        float moisture = Math.max(0.0F, 1.25F - (dist / 4.0F));
        return moisture;
    }

    public void recurseTile(ITile currentTe) {
        for (ITile te : getConnectedTiles(currentTe)) {
            if (!this.tiles.contains(te)) {
                this.tiles.add(te);
                recurseTile(te);
            }
        }
    }

    public List<ITile> getConnectedTiles(ITile te) {
        List<ITile> connected = new ArrayList<>();
        connected.add(te);
        if (te instanceof TileEntityFarmland) {
            for (ForgeDirection direction : BlockUtils.sideXZDirections) {
                int offsetX = te.getX() + direction.offsetX;
                int offsetY = te.getY() + direction.offsetY;
                int offsetZ = te.getZ() + direction.offsetZ;
                Block block = te.getWorld()
                    .getBlock(offsetX, offsetY, offsetZ);
                if (BlockUtils.isWaterBlock(block)) {
                    long coord = PositionUtils.packCoord(offsetX, offsetY, offsetZ);
                    if (!this.waterBlocks.contains(coord)) {
                        this.waterBlocks.add(coord);
                    }
                }
                TileEntity neighbourTE = te.getWorld()
                    .getTileEntity(offsetX, offsetY, offsetZ);
                if (neighbourTE instanceof TileEntityFarmland farmland) {
                    connected.add(farmland);
                }
            }
        }

        return connected;
    }
}
