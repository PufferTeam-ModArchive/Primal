package net.pufferlab.primal.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.ContainerAnvilWork;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilOrder;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

import org.lwjgl.opengl.GL11;

public class GuiAnvilWork extends GuiContainerPrimal {

    protected int xSize = 176;
    protected int ySize = 166;

    public static final ResourceLocation textureAnvil = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/anvil.png");

    private GuiButton[] anvilButtons = new GuiButton[AnvilAction.values().length];
    private GuiButtonAnvilStep[] anvilWidgets = new GuiButtonAnvilStep[6];
    TileEntityAnvil te;
    boolean[] validSteps = new boolean[3];

    public GuiAnvilWork(TileEntityAnvil te) {
        super(new ContainerAnvilWork(te));
        this.te = te;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.guiLeft = 0;
        this.guiTop = 0;

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
            anvilButtons[i] = new GuiButtonAnvil(
                this,
                id,
                centerX + offsetG + offsetX,
                centerY + offsetY,
                size,
                size,
                "");
            buttonList.add(anvilButtons[i]);
        }
        for (int i = 0; i < 3; i++) {
            int u = ((width) / 2) - 29;
            int v = ((height) / 2) - 54;
            GuiButtonAnvilStep widget = new GuiButtonAnvilStep(this, null, null, i, u + (i * 19), v, 19, 16, "");
            anvilWidgets[i] = widget;
            invButtonList.add(anvilWidgets[i]);
        }
        for (int i = 0; i < 3; i++) {
            int u = ((width) / 2) - 29;
            int v = ((height) / 2) - 76;
            GuiButtonAnvilStep widget = new GuiButtonAnvilStep(this, null, null, i, u + (i * 19), v, 19, 22, "");
            anvilWidgets[i + 3] = widget;
            invButtonList.add(anvilWidgets[i + 3]);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiButtonAnvil) {
            Primal.proxy.packet.sendAnvilWorkPacket(te, button.id);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.getTextureManager()
            .bindTexture(textureAnvil);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        drawActions(false, this.te.workActions);
        drawLineArrows(false, this.te.workLine);
        AnvilRecipe recipe = this.te.getRecipe();
        if (recipe != null) {
            drawActions(true, recipe.recipeActions);
            drawLineArrows(true, recipe.recipeLine);

            drawOrders(recipe.recipeOrders, recipe.recipeActions, this.te.workActions);
        }
        renderButtonTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); // Reset color
        int u = (width - xSize) / 2;
        int v = (height - ySize) / 2;
        this.drawTexturedModalRect(u, v, 0, 0, xSize, ySize);
    }

    public void drawActions(boolean isRecipe, AnvilAction[] actions) {
        int offsetY = 0;
        int offsetList = 0;
        if (isRecipe) {
            offsetY = -21;
            offsetList = 3;
        }
        int u = ((width) / 2) - 24;
        int v = ((height) / 2) - 52;
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 0);
        float scale = 0.65F;
        GL11.glScalef(scale, scale, 1f);
        for (int i = 0; i < actions.length; i++) {
            AnvilAction action = actions[i];
            if (action == null) continue;
            this.drawTexturedModalRect((u + (i * 19)) / scale, (v + offsetY) / scale, action.id * 16, 240, 16, 16);
            this.anvilWidgets[i + offsetList].action = action;
        }
        GL11.glPopMatrix();
    }

    public void drawOrders(AnvilOrder[] orders, AnvilAction[] actions, AnvilAction[] workActions) {
        for (int i = 0; i < actions.length; i++) {
            validSteps[i] = false;
            AnvilAction recipeAction = actions[i];
            AnvilOrder recipeOrder = orders[i];

            for (int j = 0; j < workActions.length; j++) {
                if (workActions[j] == null) continue;
                if (recipeAction.equals(workActions[j])) {
                    if (recipeOrder.isValidOrder(j)) {
                        if (validSteps[i]) continue;
                        validSteps[i] = true;
                        break;
                    }
                }
            }
        }
        int u = ((width) / 2) - 29;
        int v = ((height) / 2) - 76;
        for (int i = 0; i < orders.length; i++) {
            AnvilOrder order = orders[i];
            if (order == null) continue;
            int offsetP = 0;
            if (validSteps[i]) {
                offsetP = 20;
            }
            this.drawTexturedModalRect(u + (i * 19), v, 178 + offsetP, order.id * 22, 20, 22);
            this.anvilWidgets[i + 3].order = order;
        }

    }

    public void drawLineArrows(boolean isRecipe, int progress) {
        int offsetTex = 0;
        int offsetY = 0;
        if (isRecipe) {
            offsetTex = 5;
            offsetY = -8;
        }
        int u = ((width) / 2) - 74;
        int v = ((height) / 2) + 18;
        this.drawTexturedModalRect(u + progress, v + offsetY, 220 + offsetTex, 34, 5, 5);

    }

}
