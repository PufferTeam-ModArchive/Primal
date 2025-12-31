package net.pufferlab.primal.client.utils;

public class ModelSquare extends ModelBox {

    public ModelSquare(ModelRenderer body, int U, int V, float x, float y, float z, int xWidth, int yHeight, int zDepth,
        float scaleFactor, int side) {
        this.posX1 = x;
        this.posY1 = y;
        this.posZ1 = z;
        this.posX2 = x + (float) xWidth;
        this.posY2 = y + (float) yHeight;
        this.posZ2 = z + (float) zDepth;
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float f4 = x + (float) xWidth;
        float f5 = y + (float) yHeight;
        float f6 = z + (float) zDepth;
        x -= scaleFactor;
        y -= scaleFactor;
        z -= scaleFactor;
        f4 += scaleFactor;
        f5 += scaleFactor;
        f6 += scaleFactor;

        if (body.mirror) {
            float f7 = f4;
            f4 = x;
            x = f7;
        }

        PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f4, y, z, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f4, f5, z, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f5, z, 8.0F, 0.0F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f6, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f4, y, f6, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f4, f5, f6, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f5, f6, 8.0F, 0.0F);
        this.vertexPositions[0] = positiontexturevertex7;
        this.vertexPositions[1] = positiontexturevertex;
        this.vertexPositions[2] = positiontexturevertex1;
        this.vertexPositions[3] = positiontexturevertex2;
        this.vertexPositions[4] = positiontexturevertex3;
        this.vertexPositions[5] = positiontexturevertex4;
        this.vertexPositions[6] = positiontexturevertex5;
        this.vertexPositions[7] = positiontexturevertex6;
        if (side == 0) {
            this.quadList[0] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1,
                    positiontexturevertex5 },
                U + zDepth + xWidth,
                V + zDepth,
                U + zDepth + xWidth + zDepth,
                V + zDepth + yHeight,
                body.textureWidth,
                body.textureHeight);
        }
        if (side == 1) {
            this.quadList[1] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6,
                    positiontexturevertex2 },
                U,
                V + zDepth,
                U + zDepth,
                V + zDepth + yHeight,
                body.textureWidth,
                body.textureHeight);
        }
        if (side == 2) {
            this.quadList[2] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7,
                    positiontexturevertex },
                U + zDepth,
                V,
                U + zDepth + xWidth,
                V + zDepth,
                body.textureWidth,
                body.textureHeight);
        }
        if (side == 3) {
            this.quadList[3] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6,
                    positiontexturevertex5 },
                U + zDepth + xWidth,
                V + zDepth,
                U + zDepth + xWidth + xWidth,
                V,
                body.textureWidth,
                body.textureHeight);
        }
        if (side == 4) {
            this.quadList[4] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2,
                    positiontexturevertex1 },
                U + zDepth,
                V + zDepth,
                U + zDepth + xWidth,
                V + zDepth + yHeight,
                body.textureWidth,
                body.textureHeight);
        }
        if (side == 5) {
            this.quadList[5] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5,
                    positiontexturevertex6 },
                U + zDepth + xWidth + zDepth,
                V + zDepth,
                U + zDepth + xWidth + zDepth + xWidth,
                V + zDepth + yHeight,
                body.textureWidth,
                body.textureHeight);
        }

        if (body.mirror) {
            for (int j1 = 0; j1 < this.quadList.length; ++j1) {
                this.quadList[j1].flipFace();
            }
        }
    }
}
