package net.pufferlab.primal.client.utils.client;

public class MCModelBox {

    public final int U;
    public final int V;
    public final float x;
    public final float y;
    public final float z;
    public final int xWidth;
    public final int yHeight;
    public final int zDepth;
    public final float scaleFactor;

    public MCModelBox(MCModelRenderer body, int U, int V, float x, float y, float z, int xWidth, int yHeight,
        int zDepth, float scaleFactor) {
        this.U = U;
        this.V = V;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xWidth = xWidth;
        this.yHeight = yHeight;
        this.zDepth = zDepth;
        this.scaleFactor = scaleFactor;
    }
}
