package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

import com.falsepattern.rple.api.common.item.RPLECustomItemBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.item.RPLECustomItemBrightness", modid = "rple")
public class ItemBlockMetaHeatable extends ItemBlockMeta implements IHeatableItem, RPLECustomItemBrightness {

    public ItemBlockMetaHeatable(Block block) {
        super(block);
    }

    @Override
    public short rple$getCustomBrightnessColor(ItemStack stack) {
        if (HeatUtils.hasImpl(stack)) {
            int temperature = HeatUtils
                .getInterpolatedTemperature(GlobalTickingData.getClientTickTime(), stack.getTagCompound());
            return HeatUtils.getHeatingColor(temperature);
        }
        return Constants.lightNone;
    }
}
