package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Primal;

public class ItemMetaHeatable extends ItemMeta implements IHeatableItem {

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
}
