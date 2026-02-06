package net.pufferlab.primal.inventory;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class ContainerAnvilPlan extends Container {

    public TileEntityAnvil tileAnvil;
    public InventoryHolder inv;

    public ContainerAnvilPlan(InventoryPlayer playerInv, TileEntityAnvil te) {
        EntityPlayer player = playerInv.player;
        ItemStack stack = player.getHeldItem();
        if (AnvilRecipe.hasRecipe(stack)) {
            List<AnvilRecipe> recipe = AnvilRecipe.getRecipes(stack);
            ItemStack[] items = new ItemStack[recipe.size()];
            String[] id = new String[recipe.size()];
            for (int i = 0; i < items.length; i++) {
                items[i] = recipe.get(i).output;
                id[i] = recipe.get(i).recipeID;
            }
            inv = new InventoryHolder(items);
            int totalWidth = items.length * 18;
            int startX = (176 - totalWidth) / 2;

            for (int j = 0; j < items.length; ++j) {
                this.addSlotToContainer(new SlotPlan(inv, j, startX + j * 18, 19, id[j]));
            }
        }

        this.tileAnvil = te;
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileAnvil.isUseableByPlayer(player);
    }
}
