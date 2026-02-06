package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.utils.SoundTypePrimal;

public class TileEntityAnvil extends TileEntityInventory {

    public AnvilAction[] workActions = new AnvilAction[3];
    public int workLine = 0;
    public String recipeID = "None";

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
        compound.setString("recipeID", this.recipeID);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.workActions = AnvilAction.readFromNBT(compound);
        this.workLine = compound.getInteger("workLine");
        this.recipeID = compound.getString("recipeID");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        AnvilAction.writeToNBT(tag, this.workActions);
        tag.setInteger("workLine", this.workLine);
        tag.setString("recipeID", this.recipeID);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.workActions = AnvilAction.readFromNBT(tag);
        this.workLine = tag.getInteger("workLine");
        this.recipeID = tag.getString("recipeID");
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

    public void onRecipeChange(String recipeID) {
        this.recipeID = recipeID;
        this.updateTEState();
    }

    public void moveLine(AnvilAction action) {
        int move = action.step;
        this.workLine = Utils.clamp(0, 140, this.workLine + move);
    }

    public AnvilRecipe getRecipe() {
        return AnvilRecipe.getRecipe(this.recipeID);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
