package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.renderer.RenderMotion;
import net.pufferlab.primal.mixins.early.minecraft.client.MixinBlock;
import org.lwjgl.opengl.GL11;

public class VirtualBlock {
    public int coordX;
    public int coordY;
    public int coordZ;
    public Block blockRestore;
    public int metaRestore;

    public VirtualBlock() {
        coordX = 1;
        coordY = 255;
        coordZ = 1;
    }

    public VirtualBlock setTemp() {
        coordX = 1;
        coordY = 255;
        coordZ = 1;
        return this;
    }

    public void allocateNewCoordinate(World world) {
        while (world.getBlock(coordX, coordY, coordZ)
            .hasTileEntity(world.getBlockMetadata(coordX, coordY, coordZ))) {
            coordX++;
        }
    }

    public void placeBlock(World world, Block block, int meta, NBTTagCompound nbt) {
        allocateNewCoordinate(world);

        blockRestore = world.getBlock(coordX, coordY, coordZ);
        metaRestore = world.getBlockMetadata(coordX, coordY, coordZ);

        world.setBlock(coordX, coordY, coordZ, block, meta, 4);
        TileEntity te = world.getTileEntity(coordX, coordY, coordZ);
        if (te != null) {
            if (nbt != null) {
                NBTTagCompound nbt2 = (NBTTagCompound) nbt.copy();
                nbt2.setInteger("x", coordX);
                nbt2.setInteger("y", coordY);
                nbt2.setInteger("z", coordZ);
                te.readFromNBT(nbt2);
                te.markDirty();
            }
        }

        Primal.debugLog("Placed Block at" + coordX + ":" + coordY + ":" + coordZ);
    }

    public static boolean redirect = false;
    public static Block tempBlock;
    public static int tempX;
    public static int tempY;
    public static int tempZ;

    public void renderISBRH(World world, int x, int y, int z, RenderBlocks renderBlocks, float partialTicks) {
        //Primal.debugLog("Tried to render ISBRH");

        Block block = world.getBlock(coordX, coordY, coordZ);
        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();

        tess.startDrawingQuads();

        float angle = RenderMotion.getInterpolatedRotationDeg(1.0F, 0.0F, partialTicks);
        GL11.glTranslated(x -coordX - RenderManager.renderPosX, y -coordY - RenderManager.renderPosY, z -coordZ - RenderManager.renderPosZ);
        GL11.glTranslatef(coordX + 0.5F, coordY + 0.5F, coordZ + 0.5F);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-coordX -0.5F, -coordY -0.5F, -coordZ -0.5F);
        redirect = true;
        tempBlock = block;
        tempX = x;
        tempY = y;
        tempZ = z;
        //tess.setTranslation(x -coordX - RenderManager.renderPosX, y -coordY - RenderManager.renderPosY, z -coordZ - RenderManager.renderPosZ);
        renderBlocks.renderBlockAllFaces(block, coordX, coordY, coordZ);
        tess.draw();
        redirect = false;
        tess.setTranslation(0, 0, 0);
        GL11.glPopMatrix();
    }

    public void renderTESR(World world, int x, int y, int z, float partialTicks) {
        //Primal.debugLog("Tried to render TESR");

        TileEntity te = world.getTileEntity(coordX, coordY, coordZ);
        if(te != null) {
            //Primal.debugLog("TileName: " + te.getClass());
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

    public void restoreBlock(World world) {
        world.setBlock(coordX, coordY, coordZ, blockRestore, metaRestore, 4);
    }
}
