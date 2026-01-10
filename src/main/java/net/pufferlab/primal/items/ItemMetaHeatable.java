package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;

public class ItemMetaHeatable extends ItemMeta implements IHeatableItem {

    public IIcon[] heatOverlay;

    public ItemMetaHeatable(String[] materials, String type) {
        super(materials, type);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {
        super.onUpdate(stack, worldIn, entityIn, p_77663_4_, p_77663_5_);

        onUpdateHeat(stack, worldIn);
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
