package net.pufferlab.primal.items;

import net.minecraft.util.IIcon;
import net.pufferlab.primal.utils.CropType;

public class ItemCrops extends ItemMeta {

    public CropType[] cropType;
    public IIcon icon;

    public ItemCrops(CropType[] cropType, String name) {
        super(CropType.getNames(cropType), name);
        this.cropType = cropType;
        for (int i = 0; i < cropType.length; i++) {
            if (!cropType[i].hasCropFood) {
                cropType[i].setCropItem(this, i);
            }
        }
        this.setBlacklist(CropType.getCropsBlacklistNames(cropType));
    }
}
