package net.pufferlab.primal.compat.wdmla;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;

public enum WDUIIcons implements IIcon {

    // spotless:off
    FURNACE_FAIL(4, 0, 28, 16, 256, 256),
    FURNACE_BG_FAIL(4, 16, 28, 16, 256, 256);
    // spotless:on

    public final int texWidth;
    public final int texHeight;
    public final ResourceLocation texPath;

    /**
     * u, v, sizeU, sizeV
     */
    public final int u, v, su, sv;
    public final ResourceLocation sprites = new ResourceLocation(Primal.MODID, "textures/gui/wdmla/sprites.png");

    WDUIIcons(int u, int v, int su, int sv, int texWidth, int texHeight) {
        this.u = u;
        this.v = v;
        this.su = su;
        this.sv = sv;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.texPath = sprites;
    }

    @Override
    public int getIconWidth() {
        return su;
    }

    @Override
    public int getIconHeight() {
        return sv;
    }

    @Override
    public float getMinU() {
        return (float) u / texWidth;
    }

    @Override
    public float getMaxU() {
        return (float) (u + su) / texWidth;
    }

    @Override
    public float getInterpolatedU(double interpolation) {
        return (u + su * (float) interpolation) / texWidth;
    }

    @Override
    public float getMinV() {
        return (float) v / texHeight;
    }

    @Override
    public float getMaxV() {
        return (float) (v + sv) / texHeight;
    }

    @Override
    public float getInterpolatedV(double interpolation) {
        return (v + sv * (float) interpolation) / texHeight;
    }

    @Override
    public String getIconName() {
        return this.name();
    }
}
