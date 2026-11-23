package net.pufferlab.primal.client.gui;

import static net.pufferlab.primal.utils.HashUtils.getRGB;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.VertexCache;
import net.pufferlab.primal.inventory.ContainerQuest;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.QuestType;

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
        this.xSize = 376;
        this.ySize = 386;
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

    public static final List<QuestType> quests = new ArrayList<>();

    public static final Matrix4f matrix = new Matrix4f();
    public static final VertexCache cache = new VertexCache();

    public static void updateRendering() {
        cache.reset();
    }

    public static final int lockedColor = HashUtils.getRGB(0.5F, 0.5F, 0.5F);
    public static final int unlockedColor = HashUtils.getRGB(1.0F, 1.0F, 1.0F);

    static {
        QuestType.registerQuest(QuestType.adv1);
        QuestType.registerQuest(QuestType.adv2);
        QuestType.registerQuest(QuestType.adv3);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int centerX = ((this.width - this.xSize) / 2);
        int centerY = ((this.height - this.ySize) / 2);

        int guiX = ((this.width) / 2) + this.offsetX;
        int guiY = ((this.height) / 2) + this.offsetY;

        this.mc.getTextureManager()
            .bindTexture(textureQuest);
        Tessellator tess = Tessellator.instance;

        if(cache.isEmpty()) {
            cache.setStartDrawing(true);
            for (QuestType adv : quests) {
                int advX = (adv.x * 26);
                int advY = (adv.y * 26);

                int color = getColor(adv.isUnlocked(player.player));

                for(QuestType adv2 : adv.getDependencies()) {
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
                        drawTexturedModalRect(-13, offset, advX + 13, advY + 13, 0, 26, 26, 26, color, matrix, cache);
                    }
                    double offset2 = 26 * length;
                    drawTexturedModalRect(-13, offset2, advX + 13, advY + 13, 0, 26, Mth.floor(26 * diff), Mth.floor(26 * diff), color, matrix, cache);
                }
            }
            for(QuestType adv : quests) {
                int advX = (adv.x * 26);
                int advY = (adv.y * 26);

                int color = getColor(adv.isUnlocked(player.player));

                this.drawTexturedModalRect(0, 0, advX, advY, 0, 0, 26, 26, color, cache);
            }
        }
        cache.renderStart(tess, guiX, guiY, 0, 0, 0, 0);

        for(QuestType adv : quests) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            this.drawSimpleItemStack(advX + 5, advY + 5, adv.stack);
        }
        for (QuestType adv : quests) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            int selX = mouseX - this.guiLeft;
            int selY = mouseY - this.guiTop;

            boolean hovered = selX >= advX && selX < advX + 26
                && selY >= advY && selY < advY + 26;

            if(hovered) {
                String text = "TEST";

                int adjX = mouseX - this.guiLeft + 13;
                int adjY = mouseY - this.guiTop;

                int length = Math.max(this.fontRendererObj.getStringWidth(text), 120);

                this.drawGradientRect(adjX - 3, adjY - 3, adjX + length + 3, adjY + 12 + 3, -1073741824, -1073741824);
                this.fontRendererObj.drawStringWithShadow(text, adjX, adjY + 2, getRGB(255, 255, 255));
                break;
            }
        }
    }


    public static int getColor(boolean unlocked) {
        if(unlocked) {
            return unlockedColor;
        } else {
            return lockedColor;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    }

    public Vector3f temp = new Vector3f();

    public void drawTexturedModalRect(double x, double y, double offsetX, double offsetY, int textureX, int textureY, int width, int height, int color, VertexCache cache) {
        drawTexturedModalRect(x, y, offsetX, offsetY, textureX, textureY, width, height, color, null, cache);
    }

    public void drawTexturedModalRect(double x, double y, double offsetX, double offsetY, int textureX, int textureY, int width, int height, int color, Matrix4f matrix, VertexCache cache)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        addVertexRotated((x + 0), (y + height), offsetX, offsetY, ((float)(textureX + 0) * f), ((float)(textureY + height) * f1), color, matrix, cache);
        addVertexRotated((x + width), (y + height), offsetX, offsetY, ((float)(textureX + width) * f), ((float)(textureY + height) * f1), color, matrix, cache);
        addVertexRotated((x + width), (y + 0), offsetX, offsetY, ((float)(textureX + width) * f), ((float)(textureY + 0) * f1), color, matrix, cache);
        addVertexRotated((x + 0), (y + 0), offsetX, offsetY, ((float)(textureX + 0) * f), ((float)(textureY + 0) * f1), color, matrix, cache);
    }

    public void addVertexRotated(double x, double y, double offsetX, double offsetY, float u, float v, int color, Matrix4f matrix, VertexCache cache) {
        temp.set(x, y, this.zLevel);
        if(matrix != null) {
            matrix.transformPosition(temp);
        }
        float r = HashUtils.getR(color);
        float g = HashUtils.getG(color);
        float b = HashUtils.getB(color);
        cache.addVertexWithUV(temp.x + (float) offsetX, temp.y + (float) offsetY, this.zLevel, u, v, r, g, b);
    }
    // spotless:on
}
