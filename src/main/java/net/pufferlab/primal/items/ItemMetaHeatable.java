package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

import com.falsepattern.rple.api.common.item.RPLECustomItemBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.item.RPLECustomItemBrightness", modid = Mods.ID.rple)
public class ItemMetaHeatable extends ItemMeta implements IHeatableItem, RPLECustomItemBrightness {

    public IIcon heatOverlay;

    public ItemMetaHeatable(String[] materials, String type) {
        super(materials, type);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);

        heatOverlay = register.registerIcon(Primal.MODID + ":" + getElementName() + "_overlay");
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        if (pass == 1) {
            return heatOverlay;
        }
        return super.getIconFromDamage(meta);
    }

    @Override
    public short rple$getCustomBrightnessColor(ItemStack stack) {
        if (HeatUtils.hasImpl(stack)) {
            IHeatableItem impl = HeatUtils.getImpl(stack);
            int temperature = impl.getTemperature(stack, GlobalTickingData.getClientTickTime());
            return HeatUtils.getHeatingColor(temperature);
        }
        return Constants.lightNone;
    }
}
