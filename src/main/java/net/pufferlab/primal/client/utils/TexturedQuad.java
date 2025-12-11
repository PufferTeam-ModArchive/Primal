package net.pufferlab.primal.client.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class TexturedQuad {

    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    public Vec3 normal;

    public TexturedQuad(PositionTextureVertex[] p_i1152_1_) {
        this.vertexPositions = p_i1152_1_;
        this.nVertices = p_i1152_1_.length;
    }

    public TexturedQuad(PositionTextureVertex[] vertices, int u1, int v1, int u2, int v2, float textureWidth,
        float textureHeight) {
        this(vertices);
        float offsetU = 0.0F / textureWidth;
        float offsetV = 0.0F / textureHeight;
        vertices[0] = vertices[0].setTexturePosition(u2 / textureWidth - offsetU, v1 / textureHeight + offsetV);
        vertices[1] = vertices[1].setTexturePosition(u1 / textureWidth + offsetU, v1 / textureHeight + offsetV);
        vertices[2] = vertices[2].setTexturePosition(u1 / textureWidth + offsetU, v2 / textureHeight - offsetV);
        vertices[3] = vertices[3].setTexturePosition(u2 / textureWidth - offsetU, v2 / textureHeight - offsetV);
    }

    public void flipFace() {
        PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];

        for (int i = 0; i < this.vertexPositions.length; ++i) {
            apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }

        this.vertexPositions = apositiontexturevertex;
    }

    public Vec3 getNormal() {
        if (this.normal == null) {
            Vec3 vec3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
            Vec3 vec31 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
            this.normal = vec31.crossProduct(vec3)
                .normalize();
        }
        return this.normal;
    }

    public void draw(Tessellator tess, float scale) {
        Vec3 vec32 = this.getNormal();
        tess.startDrawingQuads();

        if (this.invertNormal) {
            tess.setNormal(-((float) vec32.xCoord), -((float) vec32.yCoord), -((float) vec32.zCoord));
        } else {
            tess.setNormal((float) vec32.xCoord, (float) vec32.yCoord, (float) vec32.zCoord);
        }

        for (int i = 0; i < 4; ++i) {
            PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
            tess.addVertexWithUV(
                (double) ((float) positiontexturevertex.vector3D.xCoord * scale),
                (double) ((float) positiontexturevertex.vector3D.yCoord * scale),
                (double) ((float) positiontexturevertex.vector3D.zCoord * scale),
                (double) positiontexturevertex.texturePositionX,
                (double) positiontexturevertex.texturePositionY);
        }

        tess.draw();
    }
}
