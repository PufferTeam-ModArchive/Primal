package net.pufferlab.primitivelife.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.recipes.KnappingRecipes;
import net.pufferlab.primitivelife.recipes.KnappingType;

public class ContainerKnapping extends Container {

    public IInventory craftResult = new InventoryCraftResult();
    public InventoryPlayer invPlayer;

    public boolean[][] knappingIcons = new boolean[5][5];
    public int type;

    public ContainerKnapping(int type, InventoryPlayer playerInventory) {
        invPlayer = playerInventory;
        this.type = type;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 20));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new SlotLocked(invPlayer, i, 8 + i * 18, 142 + 20));
        }
        this.addSlotToContainer(new SlotKnapping(this, this.craftResult, 0, 143, 46));
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                this.knappingIcons[x][y] = false;
            }
        }

    }

    public void clickedOnSlot(int x, int y) {
        if (!this.invPlayer.player.worldObj.isRemote) {
            float pitch = 0.7F + (invPlayer.player.worldObj.rand.nextFloat() * 0.3F);
            if (!this.knappingIcons[x][y]) {
                this.knappingIcons[x][y] = true;
                if (this.type == KnappingType.clay) {
                    this.playSound(invPlayer.player, PrimitiveLife.MODID + ":knapping.clay", pitch - 0.3F);
                } else {
                    this.playSound(invPlayer.player, PrimitiveLife.MODID + ":knapping.flint", pitch);
                }
            }
            ItemStack itemStack = KnappingRecipes.getOutput(this.type, this.knappingIcons);
            this.craftResult.setInventorySlotContents(0, itemStack);
            this.detectAndSendChanges();
        }
    }

    public void resetKnapping(int size) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                this.knappingIcons[x][y] = true;
            }
        }
        if (this.type == KnappingType.clay) {
            this.invPlayer.decrStackSize(invPlayer.currentItem, 2 * size);
        } else {
            this.invPlayer.decrStackSize(invPlayer.currentItem, size);
        }
        this.detectAndSendChanges();
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
        if (slotId == 36) {
            Slot slot = this.inventorySlots.get(slotId);
            if (slot.getStack() != null) {
                int stackSize = slot.getStack().stackSize;
                this.resetKnapping(stackSize);
            }
        }

        return super.slotClick(slotId, clickedButton, mode, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            int playerInvStart = 0;
            int playerInvEnd = 36;
            int resultSlot = 36;

            if (index == resultSlot) {
                if (!this.mergeItemStack(stack, playerInvStart, playerInvEnd, true)) {
                    return null;
                }
                slot.onPickupFromSlot(player, stack);
            } else {
                return null;
            }

            if (stack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        this.detectAndSendChanges();

        return itemstack;
    }

    public void playSound(EntityPlayer player, String name, float pitch) {
        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, name, 1.0F, pitch);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
