package net.pufferlab.primal.client.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelRenderer {

    /** The size of the texture file's width in pixels. */
    public float textureWidth;
    /** The size of the texture file's height in pixels. */
    public float textureHeight;
    /** The X offset into the texture used for displaying this model */
    private int textureOffsetX;
    /** The Y offset into the texture used for displaying this model */
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;
    /** The GL display list rendered by the Tessellator for this model */
    private int displayList;
    public boolean mirror;
    public boolean showModel;
    /** Hides the model. */
    public boolean isHidden;
    public List<ModelBox> cubeList;
    public List<ModelRenderer> childModels;
    public final String boxName;
    private ModelBase baseModel;
    public float offsetX;
    public float offsetY;
    public float offsetZ;

    public float rotateAngleYGlobal;
    public int facingMetaGlobal;

    public ModelRenderer(ModelBase baseModel, String boxName) {
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ArrayList();
        this.baseModel = baseModel;
        baseModel.boxList.add(this);
        this.boxName = boxName;
        this.setTextureSize(baseModel.textureWidth, baseModel.textureHeight);
    }

    public ModelRenderer(ModelBase base) {
        this(base, (String) null);
    }

    public ModelRenderer(ModelBase base, int textureOffsetX, int textureOffsetY) {
        this(base);
        this.setTextureOffset(textureOffsetX, textureOffsetY);
    }

    /**
     * Sets the current box's rotation points and rotation angles to another box.
     */
    public void addChild(ModelRenderer p_78792_1_) {
        if (this.childModels == null) {
            this.childModels = new ArrayList();
        }

        this.childModels.add(p_78792_1_);
    }

    public ModelRenderer setTextureOffset(int p_78784_1_, int p_78784_2_) {
        this.textureOffsetX = p_78784_1_;
        this.textureOffsetY = p_78784_2_;
        return this;
    }

    public ModelRenderer addBox(String boxNameSuffix, float x, float y, float z, int width, int height, int depth) {
        boxNameSuffix = this.boxName + "." + boxNameSuffix;
        TextureOffset textureoffset = this.baseModel.getTextureOffset(boxNameSuffix);
        this.setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
        this.cubeList.add(
            (new ModelBox(this, this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F))
                .func_78244_a(boxNameSuffix));
        return this;
    }

    public ModelRenderer addBox(float x, float y, float z, int width, int height, int depth) {
        this.cubeList
            .add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F));
        return this;
    }

    public void addBox(float x, float y, float z, int width, int height, int depth, float scaleFactor) {
        this.cubeList.add(
            new ModelBox(this, this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, scaleFactor));
    }

    public void setRotationPoint(float rotPointX, float rotPointY, float rotPointZ) {
        this.rotationPointX = rotPointX;
        this.rotationPointY = rotPointY;
        this.rotationPointZ = rotPointZ;
    }

    @SideOnly(Side.CLIENT)
    public void render(float scale) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(scale);
                }

                GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
                int i;

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null) {
                            for (i = 0; i < this.childModels.size(); ++i) {
                                ((ModelRenderer) this.childModels.get(i)).render(scale);
                            }
                        }
                    } else {
                        GL11.glTranslatef(
                            this.rotationPointX * scale,
                            this.rotationPointY * scale,
                            this.rotationPointZ * scale);
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null) {
                            for (i = 0; i < this.childModels.size(); ++i) {
                                ((ModelRenderer) this.childModels.get(i)).render(scale);
                            }
                        }

                        GL11.glTranslatef(
                            -this.rotationPointX * scale,
                            -this.rotationPointY * scale,
                            -this.rotationPointZ * scale);
                    }
                } else {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(
                        this.rotationPointX * scale,
                        this.rotationPointY * scale,
                        this.rotationPointZ * scale);

                    if (this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                    }

                    GL11.glCallList(this.displayList);

                    if (this.childModels != null) {
                        for (i = 0; i < this.childModels.size(); ++i) {
                            ((ModelRenderer) this.childModels.get(i)).render(scale);
                        }
                    }

                    GL11.glPopMatrix();
                }

                GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderWithRotation(float scale) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(scale);
                }

                GL11.glPushMatrix();
                GL11.glTranslatef(
                    this.rotationPointX * scale,
                    this.rotationPointY * scale,
                    this.rotationPointZ * scale);

                if (this.rotateAngleY != 0.0F) {
                    GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F) {
                    GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                }

                if (this.rotateAngleZ != 0.0F) {
                    GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                }

                GL11.glCallList(this.displayList);
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Allows the changing of Angles after a box has been rendered
     */
    @SideOnly(Side.CLIENT)
    public void postRender(float scale) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(scale);
                }

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
                        GL11.glTranslatef(
                            this.rotationPointX * scale,
                            this.rotationPointY * scale,
                            this.rotationPointZ * scale);
                    }
                } else {
                    GL11.glTranslatef(
                        this.rotationPointX * scale,
                        this.rotationPointY * scale,
                        this.rotationPointZ * scale);

                    if (this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                }
            }
        }
    }

    /**
     * Compiles a GL display list for this model
     */
    @SideOnly(Side.CLIENT)
    private void compileDisplayList(float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < this.cubeList.size(); ++i) {
            ((ModelBox) this.cubeList.get(i)).render(tessellator, scale);
        }

        GL11.glEndList();
        this.compiled = true;
    }

    /**
     * Returns the model renderer with the new texture parameters.
     */
    public ModelRenderer setTextureSize(int p_78787_1_, int p_78787_2_) {
        this.textureWidth = (float) p_78787_1_;
        this.textureHeight = (float) p_78787_2_;
        return this;
    }
}
