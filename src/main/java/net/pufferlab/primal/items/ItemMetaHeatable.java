package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

import com.falsepattern.rple.api.common.item.RPLECustomItemBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.item.RPLECustomItemBrightness", modid = "rple")
public class ItemMetaHeatable extends ItemMeta implements IHeatableItem, RPLECustomItemBrightness {

    public IIcon[] heatOverlay;

    public ItemMetaHeatable(String[] materials, String type) {
        super(materials, type);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);

        heatOverlay = new IIcon[1];
        heatOverlay[0] = register.registerIcon(Primal.MODID + ":" + getElementName() + "_overlay");
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        if (pass == 1) {
            return heatOverlay[0];
        }
        return super.getIconFromDamage(meta);
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
