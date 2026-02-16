package net.pufferlab.primal.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.inventory.ContainerAnvilPlan;
import net.pufferlab.primal.inventory.SlotPlan;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class GuiAnvilPlan extends GuiContainerPrimal {

    public static final ResourceLocation textureAnvilPlan = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/anvil_plan.png");

    public InventoryPlayer player;
    public TileEntityAnvil tileAnvil;

    public GuiAnvilPlan(InventoryPlayer inv, TileEntityAnvil te) {
        super(new ContainerAnvilPlan(inv, te));
        this.player = inv;
        this.tileAnvil = te;
        this.ySize = 110;
    }

    @Override
    public void initGui() {
        super.initGui();

        List<Slot> slots = this.inventorySlots.inventorySlots;
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (slot instanceof SlotPlan slot2) {
                buttonList.add(
                    new GuiButtonAnvilPlan(
                        this,
                        i,
                        slot2.xDisplayPosition + this.guiLeft - 1,
                        slot2.yDisplayPosition + this.guiTop - 1,
                        18,
                        18,
                        slot2.recipeID));
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiButtonAnvilPlan button2) {
            Primal.proxy.packet.sendAnvilPlanPacket(this.tileAnvil, button2.recipeID);
            this.player.player.closeScreen();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.getTextureManager()
            .bindTexture(textureAnvilPlan);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("gui." + Primal.MODID + ".anvil.plans.name");
        this.fontRendererObj
            .drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, Utils.getRGB(255, 255, 255));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
