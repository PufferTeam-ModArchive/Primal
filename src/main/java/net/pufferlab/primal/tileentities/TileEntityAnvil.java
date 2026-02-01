package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.utils.SoundTypePrimal;

public class TileEntityAnvil extends TileEntityInventory {

    public AnvilAction[] actions = new AnvilAction[3];
    public int recipeIndex;

    public TileEntityAnvil() {
        super(1);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        AnvilAction.writeToNBT(compound, this.actions);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.actions = AnvilAction.readFromNBT(compound);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        AnvilAction.writeToNBT(tag, this.actions);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.actions = AnvilAction.readFromNBT(tag);
    }

    public void onWorkButtonClick(int buttonID) {
        AnvilAction newAction = AnvilAction.get(buttonID);
        this.actions = Utils.pushFront(this.actions, newAction);
        playSound(SoundTypePrimal.soundAnvilHit);
        this.updateTEState();
    }
}
