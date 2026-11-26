package net.pufferlab.primal.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ClientTickHolder;
import net.pufferlab.primal.client.helper.VertexCache;
import net.pufferlab.primal.client.helper.VertexModifier;
import net.pufferlab.primal.inventory.ContainerQuest;
import net.pufferlab.primal.quests.QuestType;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.Utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.input.Mouse;

public class GuiQuest extends GuiContainerPrimal {
    // spotless:off

    public static final ResourceLocation textureQuest = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/quest.png");

    public boolean justPressed;
    public boolean pressed;

    public int mouseX;
    public int mouseY;
    public int offsetX;
    public int offsetY;
    public int lastOffsetX;
    public int lastOffsetY;

    public boolean hasQuestOpened;
    public QuestType questOpened;
    public static final VertexCache questOpenedCache = new VertexCache().setStartDrawing(true);

    public InventoryPlayer player;

    public GuiQuest(InventoryPlayer inv) {
        super(new ContainerQuest(inv));
        this.player = inv;
        this.xSize = 376;
        this.ySize = 386;
        questOpenedCache.reset();
        updateRendering();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = 0;
        this.guiTop = 0;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        justPressed = false;
        if(Mouse.isButtonDown(0)) {
            if(!pressed) {
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                justPressed = true;
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


    public static final Matrix4f matrix = new Matrix4f();
    public static final VertexCache cache = new VertexCache().setStartDrawing(true);

    public static void updateRendering() {
        cache.reset();
    }

    public static final int veryLockedColor = HashUtils.getRGB(0.2F, 0.2F, 0.2F);
    public static final int lockedColor = HashUtils.getRGB(0.5F, 0.5F, 0.5F);
    public static final int flickeringColor = HashUtils.getRGB(0, 0, 0);
    public static final int unlockedColor = HashUtils.getRGB(1.0F, 1.0F, 1.0F);
    public static final int bookColor = HashUtils.getRGB(128, 37, 19);

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int centerX = (this.width) / 2;
        int centerY = (this.height) / 2;
        int guiX = centerX + this.offsetX;
        int guiY = centerY + this.offsetY;

        this.mc.getTextureManager()
            .bindTexture(textureQuest);
        Tessellator tess = Tessellator.instance;

        if(cache.isEmpty()) {
            for (QuestType adv : QuestType.values()) {
                int advX = (adv.x * 26);
                int advY = (adv.y * 26);

                int color = getColor(player.player, adv);

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
            for(QuestType adv : QuestType.values()) {
                int advX = (adv.x * 26);
                int advY = (adv.y * 26);

                int color = getColor(player.player, adv);

                this.drawTexturedModalRect(0, 0, advX, advY, 0, 0, 26, 26, color, cache);
            }
        }
        cache.render(tess, guiX, guiY, 0, 0, 0, 0);
        for(QuestType adv : QuestType.values()) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            int color = getColor(player.player, adv);
            VertexModifier modifier = getModifier(color);
            this.drawSimpleItemStack(advX + 5, advY + 5, adv.stack, color, modifier);
        }
        for (QuestType adv : QuestType.values()) {
            int advX = guiX + (adv.x * 26);
            int advY = guiY + (adv.y * 26);

            int selX = mouseX - this.guiLeft;
            int selY = mouseY - this.guiTop;

            boolean hovered = selX >= advX && selX < advX + 26
                && selY >= advY && selY < advY + 26;

            if(hovered) {
                if(justPressed) {
                    Primal.proxy.packet.sendClaimedQuest(player.player, adv);
                    if(questOpened != adv) {
                        hasQuestOpened = true;
                        questOpened = adv;
                        questOpenedCache.reset();
                    }
                }
                String name = Utils.translate(adv.nameLangKey);
                String desc = Utils.translate(adv.descLangKey);

                int adjX = mouseX - this.guiLeft + 13;
                int adjY = mouseY - this.guiTop;

                int color = getColor(player.player, adv);
                VertexModifier modifier = getModifier(color);
                VertexModifier modifierSec = getModifierSec(color);
                if(color == veryLockedColor) {
                    name = "???";
                    desc = "???";
                }

                int length = Math.max(Math.max(getStringWidth(name), getStringWidth(desc)), 120);
                int width = 24;

                this.drawGradientRect(adjX - 3, adjY - 3, adjX + length + 3, adjY + width + 3, -1073741824, -1073741824);
                drawStringWithShadow(name, adjX, adjY + 2, color, modifier);
                drawStringWithShadow(desc, adjX, adjY + 15, color, modifierSec);
            }
        }
        this.mc.getTextureManager()
            .bindTexture(textureQuest);
        if(hasQuestOpened && questOpened != null) {
            QuestType adv = questOpened;
            if(questOpenedCache.isEmpty()) {
                int bookWidth = 295;
                int bookHeight = 180;
                double bookWidthOffset = -((double) bookWidth / 2);
                double bookHeightOffset = -((double) bookHeight / 2);
                drawTexturedModalRect(0, 0, bookWidthOffset, bookHeightOffset, 0, 52, bookWidth, bookHeight, bookColor, null, questOpenedCache);
                drawTexturedModalRect(0, 0, bookWidthOffset, bookHeightOffset, 0, 233, bookWidth, bookHeight, unlockedColor, null, questOpenedCache);
            }
            questOpenedCache.render(tess, centerX, centerY, 0, 0, 0, 0);
        }
    }


    public static int getColor(EntityPlayer player, QuestType adv) {
        boolean claimed = adv.isClaimed(player);
        boolean unlocked = adv.isUnlocked(player);
        boolean canBeUnlocked = adv.canBeUnlocked(player);
        if(claimed) {
            return unlockedColor;
        } else if(unlocked) {
            return flickeringColor;
        } else if(canBeUnlocked) {
            return lockedColor;
        } else {
            return veryLockedColor;
        }
    }

    public static VertexModifier getModifier(int color) {
        if(color == flickeringColor) {
            return flickeringQuest;
        }
        return null;
    }

    public static VertexModifier getModifierSec(int color) {
        if(color == flickeringColor) {
            return flickeringQuestSec;
        }
        return questSec;
    }

    public static final VertexModifier flickeringQuest = new QuestHighlight();
    public static final VertexModifier flickeringQuestSec = new VertexModifier.ColorMultiplier(new QuestHighlight(), 0.5F);
    public static final VertexModifier questSec = new VertexModifier.ColorMultiplier(0.5F);

    public static class QuestHighlight extends VertexModifier {
        public float getInterpolatedAnim() {
            int ticks = ClientTickHolder.getTicks() % 20;
            float progress = ticks <= 10 ? ticks / 10.0F : (20 - ticks) / 10.0F;
            float value = 0.5F + progress * 0.5F;
            return value;
        }

        @Override
        public float getR(float r) {
            return getInterpolatedAnim();
        }

        @Override
        public float getG(float g) {
            return getInterpolatedAnim();
        }

        @Override
        public float getB(float b) {
            return getInterpolatedAnim();
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
        float f = 0.001953125F;
        float f1 = 0.001953125F;
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
        VertexModifier modifier = getModifier(color);
        float r = HashUtils.getR(color);
        float g = HashUtils.getG(color);
        float b = HashUtils.getB(color);
        cache.addVertexWithUV(temp.x + (float) offsetX, temp.y + (float) offsetY, this.zLevel, u, v, r, g, b, modifier);
    }
    // spotless:on
}
