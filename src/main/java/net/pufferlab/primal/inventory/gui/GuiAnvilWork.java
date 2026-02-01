package net.pufferlab.primal.inventory.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.network.packets.PacketAnvilButton;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

import org.lwjgl.opengl.GL11;

public class GuiAnvilWork extends GuiScreenPrimal {

    protected int xSize = 176;
    protected int ySize = 166;

    public static final ResourceLocation textureAnvil = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/anvil.png");

    private GuiButton[] anvilButtons = new GuiButton[AnvilAction.values().length];
    TileEntityAnvil te;

    public GuiAnvilWork(TileEntityAnvil te) {
        this.te = te;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        int centerX = ((this.width) / 2) - 15;
        int centerY = ((this.height) / 2) - 15;

        for (int i = 0; i < AnvilAction.values().length; i++) {
            int id = AnvilAction.values()[i].id;
            int size = 16;
            int offsetX = 0;
            int offsetY = 0;
            if ((i % 2) == 1) {
                offsetX = 18;
            }
            int offsetG = -20;
            if (i > 3) {
                offsetG = 16;
            }
            if (((i / 2) % 2 == 0)) {
                offsetY = -18;
            }
            anvilButtons[i] = new GuiButtonAnvilAction(
                this,
                id,
                centerX + offsetG + offsetX,
                centerY + offsetY,
                size,
                size,
                "");
            buttonList.add(anvilButtons[i]);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id < 8) {
            sendWorkPacket(button.id);
        }
    }

    public void sendWorkPacket(int buttonID) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketAnvilButton(te, buttonID));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    };

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawIcons();
        for (GuiButton button : this.buttonList) {
            if (button.func_146115_a()) { // hovered
                button.func_146111_b(mouseX, mouseY);
            }
        }

    }

    public void drawToolTip(List stringList, int x, int y) {
        this.func_146283_a(stringList, x, y);
    }

    @Override
    public void drawBackground(int tint) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); // Reset color
        this.mc.getTextureManager()
            .bindTexture(textureAnvil);
        int u = (width - xSize) / 2;
        int v = (height - ySize) / 2;
        this.drawTexturedModalRect(u, v, 0, 0, xSize, ySize);
    }

    public void drawIcons() {
        this.mc.getTextureManager()
            .bindTexture(textureAnvil);
        int u = ((width) / 2) - 24;
        int v = ((height) / 2) - 52;
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 0);
        float scale = 0.65F;
        GL11.glScalef(scale, scale, 1f);
        AnvilAction[] actions = this.te.actions;
        for (int i = 0; i < actions.length; i++) {
            AnvilAction action = actions[i];
            if (action == null) continue;
            this.drawTexturedModalRect((u + (i * 19)) / scale, v / scale, action.id * 16, 240, 16, 16);
        }
        GL11.glPopMatrix();
    }

    public void drawTexturedModalRect(float x, float y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.draw();
    }

}
