package net.pufferlab.primal.client.models;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.utils.ModelBase;
import net.pufferlab.primal.client.utils.ModelRenderer;

public abstract class ModelPrimal extends ModelBase {

    ModelTESS modelTESS = new ModelTESS();

    public final ModelRenderer bb_main;
    public float facingOffsetXSet;
    public float facingOffsetZSet;
    public float facingOffsetX;
    public float facingOffsetY;
    public float facingOffsetZ;
    int type;
    boolean overlay;

    public ModelPrimal(int size) {
        textureWidth = size;
        textureHeight = size;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render() {
        bindTex();
        bb_main.render(Constants.modelConst);
    }

    public void setOverlay(boolean state) {
        overlay = state;
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int index) {
        modelTESS.renderBlock(
            renderblocks,
            tess,
            block,
            bb_main,
            Constants.modelConst,
            x,
            y,
            z,
            facingOffsetX,
            0.0F + facingOffsetY,
            facingOffsetZ,
            index);
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, double offsetX,
        double offsetY, double offsetZ, int index) {
        modelTESS.renderBlock(
            renderblocks,
            tess,
            block,
            bb_main,
            Constants.modelConst,
            x,
            y,
            z,
            offsetX + facingOffsetX,
            offsetY + facingOffsetY,
            offsetZ + facingOffsetZ,
            index);
    }

    public String getName() {
        return null;
    }

    public void bindTex() {
        String suffix = "";
        if (this.overlay) {
            suffix = "_overlay";
        }
        Minecraft.getMinecraft().renderEngine
            .bindTexture(new ResourceLocation(Primal.MODID, "textures/" + getName() + suffix + ".png"));
    }

    public ModelPrimal setType(int type) {
        this.type = type;
        return this;
    }

    public void setFacing(int meta) {
        float angle = getFacingAngle(meta);
        float add = 0.0F;
        if (invertRot()) {
            add = (float) Math.toRadians(180);
        }
        updateFacingOffset(meta);

        bb_main.rotateAngleYGlobal = (angle + add) % ((float) Math.PI * 2);
        bb_main.facingMetaGlobal = meta;
    }

    public void setFacingFromAxis(int facingMeta, int axisMeta) {
        float angle = getFacingAngle(facingMeta);

        if (axisMeta == 1) {
            bb_main.rotateAngleX = (float) (Math.PI / 2);
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleZ = angle;
            facingOffsetX = 0.0F;
            facingOffsetY = 0.5F;
            facingOffsetZ = -0.5f;
        } else if (axisMeta == 2) {
            bb_main.rotateAngleZ = (float) (-Math.PI / 2);
            bb_main.rotateAngleY = angle;
            bb_main.rotateAngleX = 0;
            facingOffsetX = -0.5f;
            facingOffsetY = 0.5F;
            facingOffsetZ = 0.0F;
        } else if (axisMeta == 0) {
            bb_main.rotateAngleX = 0;
            bb_main.rotateAngleY = angle;
            bb_main.rotateAngleZ = 0;
            facingOffsetX = 0.0f;
            facingOffsetY = 0.0F;
            facingOffsetZ = 0.0F;
        }
    }

    public void setAxis(int meta) {
        if (meta == 1) {
            bb_main.rotateAngleX = (float) (Math.PI / 2);
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleZ = 0;
        } else if (meta == 2) {
            bb_main.rotateAngleZ = (float) (-Math.PI / 2);
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleX = 0;
        } else if (meta == 0) {
            bb_main.rotateAngleX = 0;
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleZ = 0;
        }
    }

    public void setAxisReversed(int meta) {
        if (meta == 1) {
            bb_main.rotateAngleX = (float) (Math.PI / 2);
            bb_main.rotateAngleY = (float) Math.PI;
            bb_main.rotateAngleZ = 0;
        } else if (meta == 2) {
            bb_main.rotateAngleZ = (float) (-Math.PI / 2);
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleX = (float) Math.PI;
        } else if (meta == 0) {
            bb_main.rotateAngleX = 0;
            bb_main.rotateAngleY = 0;
            bb_main.rotateAngleZ = (float) Math.PI;
        }
    }

    public static float getFacingAngle(int meta) {
        float angle = 0.0F;
        switch (meta) {
            case 1:
                angle = (float) Math.toRadians(-180);
                break;
            case 2:
                angle = (float) Math.toRadians(90);
                break;
            case 3:
                angle = (float) Math.toRadians(0);
                break;
            case 4:
                angle = (float) Math.toRadians(270);
                break;
            default:
                angle = (float) Math.toRadians(90 * meta);
                break;
        }
        return angle;
    }

    public static int getFacingAngleDegree(int meta) {
        int angle = 0;
        switch (meta) {
            case 1:
                angle = -180;
                break;
            case 2:
                angle = 90;
                break;
            case 3:
                angle = 0;
                break;
            case 4:
                angle = 270;
                break;
            default:
                angle = 90 * meta;
                break;
        }
        return angle;
    }

    public void setFacingOffset(float x, float y, float z) {
        this.facingOffsetXSet = x;
        this.facingOffsetY = y;
        this.facingOffsetZSet = z;
    }

    public void updateFacingOffset(int meta) {
        switch (meta) {
            case 1:
                facingOffsetX = -facingOffsetXSet;
                facingOffsetZ = -facingOffsetZSet;
                break;
            case 2:
                facingOffsetZ = facingOffsetXSet;
                facingOffsetX = -facingOffsetZSet;
                break;
            case 3:
                facingOffsetX = facingOffsetXSet;
                facingOffsetZ = facingOffsetZSet;
                break;
            case 4:
                facingOffsetZ = -facingOffsetXSet;
                facingOffsetX = facingOffsetZSet;
                break;
        }
    }

    public boolean invertRot() {
        return false;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
