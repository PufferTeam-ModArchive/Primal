package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.utils.SoundTypePrimal;

public class TileEntityAnvil extends TileEntityInventory {

    public AnvilAction[] workActions = new AnvilAction[3];
    public int workLine = 0;
    public int recipeIndex = 0;

    public static int slotInput = 0;
    public static int slotOutput = 1;

    public TileEntityAnvil() {
        super(2);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        AnvilAction.writeToNBT(compound, this.workActions);
        compound.setInteger("workLine", this.workLine);
        compound.setInteger("recipeIndex", this.recipeIndex);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.workActions = AnvilAction.readFromNBT(compound);
        this.workLine = compound.getInteger("workLine");
        this.recipeIndex = compound.getInteger("recipeIndex");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        AnvilAction.writeToNBT(tag, this.workActions);
        tag.setInteger("workLine", this.workLine);
        tag.setInteger("recipeIndex", this.recipeIndex);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.workActions = AnvilAction.readFromNBT(tag);
        this.workLine = tag.getInteger("workLine");
        this.recipeIndex = tag.getInteger("recipeIndex");
    }

    public void onWorkButtonClick(int buttonID) {
        AnvilAction newAction = AnvilAction.get(buttonID);
        if (newAction != null) {
            moveLine(newAction);
            this.workActions = Utils.pushFront(this.workActions, newAction);
            playSound(SoundTypePrimal.soundAnvilHit);
            this.updateTEState();
        }
    }

    public void onRecipeChange(int recipeIndex) {
        if (recipeIndex < AnvilRecipe.getRecipeList()
            .size()) {
            this.recipeIndex = recipeIndex;
            this.updateTEState();
        }
    }

    public void moveLine(AnvilAction action) {
        int move = action.step;
        this.workLine = Utils.clamp(0, 140, this.workLine + move);
    }

    public AnvilRecipe getRecipe() {
        return AnvilRecipe.getRecipeList()
            .get(this.recipeIndex);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
