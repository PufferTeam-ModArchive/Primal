package net.pufferlab.primal.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.SoundTypePrimal;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.GlobalTickingData;

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
        AnvilAction.readFromNBT(compound, this.workActions);

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
        AnvilAction.readFromNBT(tag, this.workActions);

        this.workLine = tag.getInteger("workLine");
        this.recipeID = tag.getString("recipeID");
    }

    public boolean onWorkButtonClick(ItemStack heldItem, int buttonID) {
        AnvilAction newAction = AnvilAction.get(buttonID);
        if (newAction != null && isInputHotEnough() && hasMetalLevel() && hasMetalLevel(heldItem)) {
            moveLine(newAction);
            this.workActions = Utils.pushFront(this.workActions, newAction);
            playSound(SoundTypePrimal.soundAnvilHit);
            tryCompleteRecipe();
            this.updateTEState();
            return true;
        }
        return false;
    }

    public void tryCompleteRecipe() {
        AnvilRecipe recipe = getRecipe();
        boolean isComplete = recipe.equals(getInventoryStack(slotInput), this.workLine, this.workActions);
        if (isComplete) {
            this.recipeID = "None";
            setOutput(recipe.output.copy());
        }
    }

    public void onRecipeChange(String recipeID) {
        this.recipeID = recipeID;
        this.updateTEState();
    }

    public void setInput(EntityPlayer player) {
        addInventorySlotContentsUpdate(slotInput, player);
        ItemStack stack = getInventoryStack(slotInput);
        if (HeatUtils.hasImpl(stack)) {
            IHeatableItem item = HeatUtils.getImpl(stack);
            item.updateHeat(stack, this.worldObj, -0.25F, 3000);
        }
    }

    public void setOutput(ItemStack stackToCopy) {
        setInventorySlotContents(slotOutput, stackToCopy);
        ItemStack stack = getInventoryStack(slotInput);
        ItemStack stack2 = getInventoryStack(slotOutput);
        if (HeatUtils.hasImpl(stack) && HeatUtils.hasImpl(stack2)) {
            HeatUtils.transferInterpolatedTemperatureToNBT(
                Utils.getOrCreateTagCompound(stack2),
                Utils.getOrCreateTagCompound(stack));
        }
        setInventorySlotContents(slotInput, null);
    }

    public boolean isInputHotEnough() {
        ItemStack stack = getInventoryStack(slotInput);
        if (HeatUtils.hasImpl(stack) && stack.hasTagCompound()) {
            IHeatableItem item = HeatUtils.getImpl(stack);
            int temperature = HeatUtils
                .getInterpolatedTemperature(GlobalTickingData.getTickTime(this.worldObj), stack.getTagCompound());
            int forging = item.getForgingTemperature(stack);
            if (temperature > forging) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMetalLevel() {
        ItemStack stack = getInventoryStack(slotInput);
        if (HeatUtils.hasImpl(stack)) {
            IHeatableItem impl = HeatUtils.getImpl(stack);
            MetalType metal = impl.getMetal(stack);
            if (metal != null) {
                if (metal.level <= ItemUtils.getHarvestLevel(getBlockType(), getBlockMetadata())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasMetalLevel(ItemStack tool) {
        ItemStack stack = getInventoryStack(slotInput);
        if (HeatUtils.hasImpl(stack) && HeatUtils.hasImpl(tool)) {
            IHeatableItem impl = HeatUtils.getImpl(stack);
            IHeatableItem impl2 = HeatUtils.getImpl(tool);
            MetalType metal = impl.getMetal(stack);
            MetalType metal2 = impl2.getMetal(tool);
            if (metal != null) {
                if (metal.level <= metal2.level) {
                    return true;
                }
            }
        }
        return false;
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
