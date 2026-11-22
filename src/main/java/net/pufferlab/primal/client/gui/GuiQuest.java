package net.pufferlab.primal.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.ContainerQuest;
import net.pufferlab.primal.utils.Mth;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.input.Mouse;

public class GuiQuest extends GuiContainerPrimal {
    // spotless:off

    public static final ResourceLocation textureQuest = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/quest.png");

    public boolean pressed;

    public int mouseX;
    public int mouseY;
    public int offsetX;
    public int offsetY;
    public int lastOffsetX;
    public int lastOffsetY;

    public InventoryPlayer player;

    public GuiQuest(InventoryPlayer inv) {
        super(new ContainerQuest(inv));
        this.player = inv;
        this.xSize = 176;
        this.ySize = 186;
    }

    @Override
    public void initGui() {
        super.initGui();

    }

    @Override
    protected void actionPerformed(GuiButton button) {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(Mouse.isButtonDown(0)) {
            if(!pressed) {
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                pressed = true;
            }
        } else {
            if(pressed) {
                this.lastOffsetX = this.offsetX;
                this.lastOffsetY = this.offsetY;
                pressed = false;
            }
        }

        if(pressed) {
            this.offsetX = (mouseX - this.mouseX) + this.lastOffsetX;
            this.offsetY = (mouseY - this.mouseY) + this.lastOffsetY;
        }
    }

    public static final List<Advancement> quests = new ArrayList<>();

    static {
        quests.add(Advancement.adv1);
        quests.add(Advancement.adv2);
        quests.add(Advancement.adv3);
    }

    Matrix4f matrix =  new Matrix4f();

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int guiX = ((this.width - this.xSize) / 2) + this.offsetX;
        int guiY = ((this.height - this.ySize) / 2) + this.offsetY;

        for (Advancement adv : quests) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            for(Advancement adv2 : adv.getDependencies()) {
                int dx = adv2.x - adv.x;
                int dy = adv2.y - adv.y;

                double realLength = Mth.sqrt(Mth.pow((dx)) + Mth.pow((dy)));
                double length = Mth.floor(realLength);
                double diff = realLength - length;
                double angle = Math.atan2(dy, dx) - Math.PI / 2;
                matrix.identity();
                matrix.rotateZ((float) angle);
                for(int i = 0; i < length; i++) {
                    int offset = 26 * i;
                    drawTexturedModalRect(-13, offset, advX + 13, advY + 13, 0, 26, 26, 26, matrix);
                }
                double offset2 = 26 * length;
                drawTexturedModalRect(-13, offset2, advX + 13, advY + 13, 0, 26, Mth.floor(26 * diff), Mth.floor(26 * diff), matrix);
            }
        }
        for(Advancement adv : quests) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            this.drawTexturedModalRect(advX, advY, 0, 0, 26, 26);
        }
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager()
            .bindTexture(textureQuest);
    }

    public Vector3f temp = new Vector3f();

    public void drawTexturedModalRect(double x, double y, double offsetX, double offsetY, int textureX, int textureY, int width, int height, Matrix4f matrix)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        addVertexRotated(tessellator, (double)(x + 0), (double)(y + height), offsetX, offsetY, (double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1), matrix);
        addVertexRotated(tessellator, (double)(x + width), (double)(y + height), offsetX, offsetY, (double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1), matrix);
        addVertexRotated(tessellator, (double)(x + width), (double)(y + 0), offsetX, offsetY, (double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1), matrix);
        addVertexRotated(tessellator, (double)(x + 0), (double)(y + 0), offsetX, offsetY, (double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1), matrix);
        tessellator.draw();
    }

    public void addVertexRotated(Tessellator tess, double x, double y, double offsetX, double offsetY, double u, double v, Matrix4f matrix) {
        temp.set(x, y, this.zLevel);
        matrix.transformPosition(temp);
        tess.addVertexWithUV(temp.x + offsetX, temp.y + offsetY, this.zLevel, u, v);
    }
    // spotless:on
}
