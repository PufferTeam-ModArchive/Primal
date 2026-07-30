package net.pufferlab.primal.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.utils.CropType;

public class ItemCrops extends ItemMeta {

    public CropType[] cropType;
    public IIcon icon;

    public ItemCrops(CropType[] cropType, String name) {
        super(CropType.getNames(cropType), name);
        this.cropType = cropType;
        String[] blacklistedNames = new String[cropType.length];
        for (int i = 0; i < cropType.length; i++) {
            if (!cropType[i].hasCropFood) {
                cropType[i].setCropItem(this, i);
            }
            if (!cropType[i].hasCropItem || cropType[i].hasCropFood) {
                blacklistedNames[i] = cropType[i].name;
            }
        }
        this.setBlacklist(blacklistedNames);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int i = 0; i < cropType.length; i++) {
            CropType crop = cropType[i];
            if (!crop.hasCropItem && !crop.hasCropFood) {
                list.add(crop.getCropItem());
            }
        }
        super.getSubItems(item, creativeTabs, list);
    }

}
