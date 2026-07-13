package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.client.renderer.RenderProjection;

import org.lwjgl.opengl.GL11;

public class VirtualBlock {

    public int coordX;
    public int coordY;
    public int coordZ;

    public VirtualBlock(int x, int y, int z) {
        coordX = x;
        coordY = y;
        coordZ = z;
    }

    public VirtualBlock() {}

    public void copy(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlock(x, y, z);
                    if (block.getMaterial() == Material.air || block == Registry.moving_block) continue;
                    int meta = world.getBlockMetadata(x, y, z);
                    placeBlock(
                        world,
                        coordX + (x - (minX + 1)),
                        coordY + (y - (minY)),
                        coordZ + (z - (minZ + 1)),
                        block,
                        meta,
                        null);

                    world.setBlockToAir(x, y, z);
                }
            }
        }
    }

    public void placeBlock(World world, Block block, int meta, NBTTagCompound nbt) {
        placeBlock(world, coordX, coordY, coordZ, block, meta, nbt);
    }

    public void placeBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt) {
        world.getBlock(x, y, z);
        world.setBlock(x, y, z, block, meta, 2);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            if (nbt != null) {
                NBTTagCompound nbt2 = (NBTTagCompound) nbt.copy();
                nbt2.setInteger("x", x);
                nbt2.setInteger("y", y);
                nbt2.setInteger("z", z);
                te.readFromNBT(nbt2);
                te.markDirty();
            }
        }

        Primal.debugLog("Placed Block at" + x + ":" + y + ":" + z);
    }

    public void renderISBRH(World world, int x, int y, int z, RenderBlocks renderBlocks, float partialTicks) {
        RenderProjection.instance.renderISBRH(world, coordX, coordY, coordZ, x, y, z, renderBlocks, partialTicks);
    }

    public void renderTESR(World world, int x, int y, int z, float partialTicks) {
        // Primal.debugLog("Tried to render TESR");

        TileEntity te = world.getTileEntity(coordX, coordY, coordZ);
        if (te != null) {
            // Primal.debugLog("TileName: " + te.getClass());
            GL11.glPushMatrix();
            GL11.glTranslated(-coordX, -coordY, -coordZ);
            GL11.glTranslated(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(te, coordX, coordY, coordZ, partialTicks);
            GL11.glPopMatrix();
        }

    }

    public void rotateBlock(World world, ForgeDirection direction) {
        Block block = world.getBlock(coordX, coordY, coordZ);
        block.rotateBlock(world, coordX, coordY, coordZ, direction);
    }

    public Block getBlock(World world) {
        return world.getBlock(coordX, coordY, coordZ);
    }

    public int getBlockMetadata(World world) {
        return world.getBlockMetadata(coordX, coordY, coordZ);
    }

    public TileEntity getTileEntity(World world) {
        return world.getTileEntity(coordX, coordY, coordZ);
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("xTarget", this.coordX);
        compound.setInteger("yTarget", this.coordY);
        compound.setInteger("zTarget", this.coordZ);
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.coordX = compound.getInteger("xTarget");
        this.coordY = compound.getInteger("yTarget");
        this.coordZ = compound.getInteger("zTarget");
    }
}
