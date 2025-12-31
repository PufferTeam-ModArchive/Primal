package net.pufferlab.primal.client.helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;
import net.pufferlab.primal.client.utils.PositionTextureVertex;
import net.pufferlab.primal.client.utils.TexturedQuad;

public class ModelTESS {

    static double epsilon = 2e-5;

    public void renderBlock(RenderBlocks renderblocks, Tessellator tess, Block block, ModelRenderer renderer,
        float scale, int x, int y, int z, double offsetX, double offsetY, double offsetZ, int index) {

        if (renderblocks.hasOverrideBlockTexture()) {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            renderblocks.renderStandardBlock(block, x, y, z);
            return;
        }

        if (!renderer.isHidden && renderer.showModel) {

            // Render children first
            if (renderer.childModels != null) {
                for (int i = 0; i < renderer.childModels.size(); ++i) {
                    ModelRenderer child = (ModelRenderer) renderer.childModels.get(i);

                    // Backup child state
                    float oldRotateX = child.rotateAngleX;
                    float oldRotateY = child.rotateAngleY;
                    float oldRotateZ = child.rotateAngleZ;
                    float oldPivotX = child.rotationPointX;
                    float oldPivotY = child.rotationPointY;
                    float oldPivotZ = child.rotationPointZ;

                    // Apply parent rotation/pivot to child
                    child.rotateAngleX += renderer.rotateAngleX;
                    child.rotateAngleY += renderer.rotateAngleY;
                    child.rotateAngleZ += renderer.rotateAngleZ;

                    child.rotationPointX += renderer.rotationPointX;
                    child.rotationPointY += renderer.rotationPointY;
                    child.rotationPointZ += renderer.rotationPointZ;

                    child.rotateAngleYGlobal = renderer.rotateAngleYGlobal;

                    // Recurse
                    renderBlock(renderblocks, tess, block, child, scale, x, y, z, offsetX, offsetY, offsetZ, index);

                    // Restore child state
                    child.rotateAngleX = oldRotateX;
                    child.rotateAngleY = oldRotateY;
                    child.rotateAngleZ = oldRotateZ;
                    child.rotationPointX = oldPivotX;
                    child.rotationPointY = oldPivotY;
                    child.rotationPointZ = oldPivotZ;
                }
            }

            double r = renderer.rotateAngleYGlobal;

            double cosR = Math.cos(r);
            double sinR = Math.sin(r);

            IIcon icon = block.getIcon(renderblocks.blockAccess, x, y, z, index);
            if (index < 16) {
                icon = block.getIcon(0, index);
            }

            tess.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
            int i1 = block.colorMultiplier(renderblocks.blockAccess, x, y, z);
            float f = (float) (i1 >> 16 & 255) / 255.0F;
            float f1 = (float) (i1 >> 8 & 255) / 255.0F;
            float f2 = (float) (i1 & 255) / 255.0F;

            if (EntityRenderer.anaglyphEnable) {
                float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
                float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
                f = f3;
                f1 = f4;
                f2 = f5;
            }

            if (renderer.cubeList != null) {
                for (int i = 0; i < renderer.cubeList.size(); ++i) {
                    ModelBox box = renderer.cubeList.get(i);

                    double cosX = Math.cos(renderer.rotateAngleX), sinX = Math.sin(renderer.rotateAngleX);
                    double cosY = Math.cos(renderer.rotateAngleY), sinY = Math.sin(renderer.rotateAngleY);
                    double cosZ = Math.cos(renderer.rotateAngleZ), sinZ = Math.sin(renderer.rotateAngleZ);

                    for (int j = 0; j < box.quadList.length; ++j) {
                        TexturedQuad quad = box.quadList[j];
                        if (quad == null) continue;

                        // --- Base normal (unrotated) ---
                        Vec3 normal = quad.getNormal();

                        // --- Rotate the normal ---
                        double nx = normal.xCoord, ny = normal.yCoord, nz = normal.zCoord;

                        double ny1 = ny * cosX - nz * sinX;
                        double nz1 = ny * sinX + nz * cosX;

                        double nx2 = nx * cosY + nz1 * sinY;
                        double nz2 = -nx * sinY + nz1 * cosY;

                        double nx3 = nx2 * cosZ - ny1 * sinZ;
                        double ny3 = nx2 * sinZ + ny1 * cosZ;

                        double vx20 = nx3 * cosR - nz2 * sinR;
                        double vz20 = nx3 * sinR + nz2 * cosR;

                        nx3 = vx20;
                        nz2 = vz20;

                        double len = Math.sqrt(nx3 * nx3 + ny3 * ny3 + nz2 * nz2);
                        if (len > 0) {
                            nx3 /= len;
                            ny3 /= len;
                            nz2 /= len;
                        }

                        tess.setNormal((float) nx3, (float) ny3, (float) nz2);

                        float shade = 1.0F;
                        if (ny3 > 0.5F) shade = 1.0F; // top
                        else if (ny3 < -0.5F) shade = 0.5F; // bottom
                        else if (nx3 > 0.5F || nx3 < -0.5F) shade = 0.6F; // east/west
                        else if (nz2 > 0.5F || nz2 < -0.5F) shade = 0.8F; // north/south

                        tess.setColorOpaque_F(f * shade, f1 * shade, f2 * shade);

                        PositionTextureVertex pos1 = quad.vertexPositions[0];
                        double u1 = icon.getMinU() + pos1.texturePositionX * (icon.getMaxU() - icon.getMinU());
                        double v1 = icon.getMinV() + pos1.texturePositionY * (icon.getMaxV() - icon.getMinV());

                        PositionTextureVertex pos2 = quad.vertexPositions[1];
                        double u2 = icon.getMinU() + pos2.texturePositionX * (icon.getMaxU() - icon.getMinU());
                        double v2 = icon.getMinV() + pos2.texturePositionY * (icon.getMaxV() - icon.getMinV());

                        PositionTextureVertex pos3 = quad.vertexPositions[2];
                        double u3 = icon.getMinU() + pos3.texturePositionX * (icon.getMaxU() - icon.getMinU());
                        double v3 = icon.getMinV() + pos3.texturePositionY * (icon.getMaxV() - icon.getMinV());

                        PositionTextureVertex pos4 = quad.vertexPositions[3];
                        double u4 = icon.getMinU() + pos4.texturePositionX * (icon.getMaxU() - icon.getMinU());
                        double v4 = icon.getMinV() + pos4.texturePositionY * (icon.getMaxV() - icon.getMinV());

                        double[] U = { u1, u2, u3, u4 };
                        double[] V = { v1, v2, v3, v4 };

                        U = addEpsilonOffset(U);
                        V = addEpsilonOffset(V);

                        // --- Add rotated vertices ---
                        for (int p = 0; p < 4; ++p) {
                            PositionTextureVertex pos = quad.vertexPositions[p];
                            double u = U[p];
                            double v = V[p];

                            double px = pos.vector3D.xCoord * scale;
                            double py = pos.vector3D.yCoord * scale;
                            double pz = pos.vector3D.zCoord * scale;

                            // --- Translate into pivot space ---
                            px -= renderer.rotationPointX * scale;
                            py -= renderer.rotationPointY * scale;
                            pz -= renderer.rotationPointZ * scale;

                            // --- Rotate vertex same as normal ---
                            double y1 = py * cosX - pz * sinX;
                            double z1 = py * sinX + pz * cosX;

                            double x2 = px * cosY + z1 * sinY;
                            double z2v = -px * sinY + z1 * cosY;

                            double x3 = x2 * cosZ - y1 * sinZ;
                            double y3 = x2 * sinZ + y1 * cosZ;

                            // --- Translate back from pivot ---
                            x3 += renderer.rotationPointX * scale;
                            y3 += renderer.rotationPointY * scale;
                            z2v += renderer.rotationPointZ * scale;

                            // --- Apply world offset ---

                            double vx2 = x3 * cosR - z2v * sinR;
                            double vz2 = x3 * sinR + z2v * cosR;

                            x3 = vx2;
                            z2v = vz2;

                            double vx = x3 + x + 0.5 + offsetX;
                            double vy = y3 + y + offsetY;
                            double vz = z2v + z + 0.5 + offsetZ;

                            tess.addVertexWithUV(vx, vy, vz, u, v);
                        }
                    }
                }
            }
        }
    }

    public void renderFluid(RenderBlocks renderblocks, Tessellator tess, int x, int y, int z, FluidStack fs,
        double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean renderAllSides) {
        renderFluid(renderblocks, tess, x, y, z, fs, minX, minY, minZ, maxX, maxY, maxZ, renderAllSides, false);
    }

    public void renderFluid(RenderBlocks renderblocks, Tessellator tess, int x, int y, int z, FluidStack fs,
        double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean renderAllSides,
        boolean isFlowing) {
        if (fs == null) return;
        Fluid fluid = fs.getFluid();
        if (fluid == null) return;

        Block block = fs.getFluid()
            .getBlock();
        if (block == null) return;

        if (renderblocks.hasOverrideBlockTexture()) {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            renderblocks.renderStandardBlock(block, x, y, z);
            return;
        }

        IIcon icon = fluid.getStillIcon();
        if (isFlowing) {
            icon = fluid.getFlowingIcon();
        }
        if (icon == null) icon = fluid.getIcon();
        if (icon == null) icon = block.getIcon(0, 0);
        if (icon == null) return;

        int color = fluid.getColor(fs);
        tess.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));

        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        tess.setColorOpaque_F(r, g, b);
        renderblocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
        renderblocks.renderFaceYPos(block, x, y, z, icon);
        if (renderAllSides) {
            renderblocks.renderFaceYNeg(block, x, y, z, icon);
            renderblocks.renderFaceXPos(block, x, y, z, icon);
            renderblocks.renderFaceXNeg(block, x, y, z, icon);
            renderblocks.renderFaceZPos(block, x, y, z, icon);
            renderblocks.renderFaceZNeg(block, x, y, z, icon);
        }
    }

    /*
     * --- CRASH FIX ----
     * Fix Vertex Crash (When using two different render pass);
     * This code is only used for that.
     * https://forums.minecraftforge.net/topic/22139-isbrh-alpha-blending-inconsistencies/
     */
    public void dumpVertices(Tessellator tess, int x, int y, int z) {
        for (int i = 0; i < 4; i++) {
            tess.addVertex(x, y, z);
        }
    }

    public double[] addEpsilonOffset(double[] coords) {
        double min = coords[0];
        double max = coords[0];
        for (int i = 1; i < coords.length; i++) {
            if (coords[i] < min) min = coords[i];
            if (coords[i] > max) max = coords[i];
        }

        for (int i = 0; i < coords.length; i++) {
            if (Math.abs(coords[i] - min) < 1e-9) {
                coords[i] = min + epsilon;
            } else if (Math.abs(coords[i] - max) < 1e-9) {
                coords[i] = max - epsilon;
            }
        }

        return coords;
    }
}
