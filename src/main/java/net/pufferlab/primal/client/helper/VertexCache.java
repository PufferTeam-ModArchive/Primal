package net.pufferlab.primal.client.helper;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class VertexCache {

    private boolean empty = true;
    private boolean startDrawing = false;
    private int renderType = 0;
    private float[] vertices = new float[64];
    private int size;
    private int vertexInQuad = 0;

    public int size() {
        return this.size;
    }

    public void reset() {
        vertices = new float[64];
        size = 0;
        vertexInQuad = 0;
        renderType = 0;
        empty = true;
    }

    public void setStartDrawing(boolean state) {
        this.startDrawing = state;
    }

    public float[] getVertices() {
        return this.vertices;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    private void setRenderType(int type) {
        if (this.renderType == type) return;
        if (renderType != 0) {
            throw new IllegalStateException("Can't change render type in the same cache.");
        }
        this.renderType = type;
    }

    public void addVertexWithUV(float x, float y, float z, float u, float v, float r, float g, float b, float nX,
        float nY, float nZ) {
        setRenderType(1);
        extendCache(11);
        int s = size;
        vertices[s] = x;
        vertices[s + 1] = y;
        vertices[s + 2] = z;
        vertices[s + 3] = u;
        vertices[s + 4] = v;
        vertices[s + 5] = r;
        vertices[s + 6] = g;
        vertices[s + 7] = b;
        vertices[s + 8] = nX;
        vertices[s + 9] = nY;
        vertices[s + 10] = nZ;
        size = s + 11;

        vertexInQuad++;

        if (vertexInQuad == 4) {
            vertexInQuad = 0;
        }
    }

    public void addVertexWithUV(float x, float y, float z, float u, float v, float r, float g, float b) {
        setRenderType(2);
        extendCache(8);
        int s = size;
        vertices[s] = x;
        vertices[s + 1] = y;
        vertices[s + 2] = z;
        vertices[s + 3] = u;
        vertices[s + 4] = v;
        vertices[s + 5] = r;
        vertices[s + 6] = g;
        vertices[s + 7] = b;
        size = s + 8;

        vertexInQuad++;

        if (vertexInQuad == 4) {
            vertexInQuad = 0;
        }
    }

    public void addVertexWithUV(float x, float y, float z, float u, float v) {
        setRenderType(3);
        extendCache(5);
        int s = size;
        vertices[s] = x;
        vertices[s + 1] = y;
        vertices[s + 2] = z;
        vertices[s + 3] = u;
        vertices[s + 4] = v;
        size = s + 5;

        vertexInQuad++;

        if (vertexInQuad == 4) {
            vertexInQuad = 0;
        }
    }

    public void addVertex(float x, float y, float z, float x2, float y2, float z2) {
        setRenderType(10);
        extendCache(6);
        int s = size;
        vertices[s] = x;
        vertices[s + 1] = y;
        vertices[s + 2] = z;
        vertices[s + 3] = x2;
        vertices[s + 4] = y2;
        vertices[s + 5] = z2;
        size = s + 6;
    }

    private void extendCache(int additional) {
        if (size + additional > vertices.length) {
            int newCapacity = Math.max(vertices.length * 2, size + additional);
            float[] newVertices = new float[newCapacity];
            System.arraycopy(vertices, 0, newVertices, 0, size);
            vertices = newVertices;
        }
        empty = false;
    }

    public void renderStart(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0, double offsetZ0) {
        render(tess, x, y, z, offsetX0, offsetY0, offsetZ0);
    }

    public void render(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0, double offsetZ0) {
        if (startDrawing) {
            if (renderType == 1 || renderType == 2 || renderType == 3) {
                tess.startDrawingQuads();
            }
            if (renderType == 10) {
                tess.startDrawing(GL11.GL_LINES);
            }
        }
        if (renderType == 1) {
            renderQuadColorNormal(tess, x, y, z, offsetX0, offsetY0, offsetZ0);
        }
        if (renderType == 2) {
            renderQuadColor(tess, x, y, z, offsetX0, offsetY0, offsetZ0);
        }
        if (renderType == 3) {
            renderQuad(tess, x, y, z, offsetX0, offsetY0, offsetZ0);
        }
        if (renderType == 10) {
            renderLine(tess, x, y, z, offsetX0, offsetY0, offsetZ0);
        }
        if (startDrawing) {
            if (renderType != 0) {
                tess.draw();
            }
        }
    }

    private void renderQuadColorNormal(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0,
        double offsetZ0) {
        if (vertexInQuad > 0) {
            throw new IllegalStateException("Incomplete quad detected before render!");
        }

        int stride = 11;
        int quadSize = stride * 4;

        int safeSize = size - (size % quadSize);

        for (int s = 0; s < safeSize; s += quadSize) {

            for (int i = 0; i < 4; i++) {
                int idx = s + i * stride;

                double vX = vertices[idx];
                double vY = vertices[idx + 1];
                double vZ = vertices[idx + 2];
                float u = vertices[idx + 3];
                float v = vertices[idx + 4];
                float r = vertices[idx + 5];
                float g = vertices[idx + 6];
                float b = vertices[idx + 7];
                float nX = vertices[idx + 8];
                float nY = vertices[idx + 9];
                float nZ = vertices[idx + 10];

                tess.setNormal(nX, nY, nZ);
                tess.setColorOpaque_F(r, g, b);
                tess.addVertexWithUV(vX + x + offsetX0, vY + y + offsetY0, vZ + z + offsetZ0, u, v);
            }
        }
    }

    private void renderQuadColor(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0,
        double offsetZ0) {
        if (vertexInQuad > 0) {
            throw new IllegalStateException("Incomplete quad detected before render!");
        }

        int stride = 8;
        int quadSize = stride * 4;

        int safeSize = size - (size % quadSize);

        for (int s = 0; s < safeSize; s += quadSize) {

            for (int i = 0; i < 4; i++) {
                int idx = s + i * stride;

                double vX = vertices[idx];
                double vY = vertices[idx + 1];
                double vZ = vertices[idx + 2];
                float u = vertices[idx + 3];
                float v = vertices[idx + 4];
                float r = vertices[idx + 5];
                float g = vertices[idx + 6];
                float b = vertices[idx + 7];

                tess.setColorOpaque_F(r, g, b);
                tess.addVertexWithUV(vX + x + offsetX0, vY + y + offsetY0, vZ + z + offsetZ0, u, v);
            }
        }
    }

    private void renderQuad(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0, double offsetZ0) {
        if (vertexInQuad > 0) {
            throw new IllegalStateException("Incomplete quad detected before render!");
        }

        int stride = 5;
        int quadSize = stride * 4;

        int safeSize = size - (size % quadSize);

        for (int s = 0; s < safeSize; s += quadSize) {

            for (int i = 0; i < 4; i++) {
                int idx = s + i * stride;

                double vX = vertices[idx];
                double vY = vertices[idx + 1];
                double vZ = vertices[idx + 2];
                float u = vertices[idx + 3];
                float v = vertices[idx + 4];

                tess.addVertexWithUV(vX + x + offsetX0, vY + y + offsetY0, vZ + z + offsetZ0, u, v);
            }
        }
    }

    public void renderLine(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0, double offsetZ0) {
        int stride = 6;
        int lineSize = stride * 2;
        int safeSize = size - (size % lineSize);
        for (int s = 0; s < safeSize; s += lineSize) {
            for (int i = 0; i < 2; i++) {
                int idx = s + i * stride;
                double x1 = vertices[idx];
                double y1 = vertices[idx + 1];
                double z1 = vertices[idx + 2];
                double x2 = vertices[idx + 3];
                double y2 = vertices[idx + 4];
                double z2 = vertices[idx + 5];

                tess.addVertex(x1 + x + offsetX0, y1 + y + offsetY0, z1 + offsetZ0);
                tess.addVertex(x2 + x + offsetX0, y2 + y + offsetY0, z2 + offsetZ0);
            }
        }
    }
}
