package net.pufferlab.primal.compat.wdmla;

import static net.pufferlab.primal.tileentities.TileEntityQuern.*;

import java.util.Collections;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.QuernRecipe;
import net.pufferlab.primal.tileentities.TileEntityQuern;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public class WDQuernHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdquernhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        NBTTagCompound tag = accessor.getServerData();
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityQuern tef) {
            int timeToProcess = tef.getGrindTime();
            boolean isMoving = tef.isMoving;
            if (isMoving && tef.getInventoryStack(slotInput) != null) {
                int timePassed = tag.getInteger("timeGround");
                ItemStack input = tef.getInventoryStack(slotInput);
                ItemStack output = QuernRecipe.getOutput(input);
                ItemStack actualOutput = tef.getInventoryStack(slotOutput);
                if (actualOutput != null) {
                    output = actualOutput;
                } else {
                    if (output != null) {
                        output.stackSize = 0;
                    }
                }
                if (output != null) {
                    IComponent progress = ThemeHelper.INSTANCE.furnaceLikeProgress(
                        Collections.singletonList(input),
                        Collections.singletonList(output),
                        timePassed,
                        timeToProcess,
                        false);
                    tooltip.child(progress.tag(resourceLocation));
                }
            }
        }

    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityQuern tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
