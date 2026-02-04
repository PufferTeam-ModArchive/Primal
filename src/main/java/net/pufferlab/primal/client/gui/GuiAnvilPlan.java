package net.pufferlab.primal.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.ContainerAnvilPlan;
import net.pufferlab.primal.inventory.SlotPlan;
import net.pufferlab.primal.network.packets.PacketAnvilButton;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class GuiAnvilPlan extends GuiContainerPrimal {

    public InventoryPlayer player;
    public TileEntityAnvil tileAnvil;

    public GuiAnvilPlan(InventoryPlayer inv, TileEntityAnvil te) {
        super(new ContainerAnvilPlan(inv, te));
        this.player = inv;
        this.tileAnvil = te;
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
                        slot2.xDisplayPosition + this.guiLeft,
                        slot2.yDisplayPosition + this.guiTop,
                        18,
                        18,
                        slot2.recipeID));
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiButtonAnvilPlan button2) {
            sendRecipePacket(button2.recipeID);
            this.player.player.closeScreen();
        }
    }

    public void sendRecipePacket(int recipeID) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketAnvilButton(this.tileAnvil, recipeID, false));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
