package net.pufferlab.primal.items;

import net.minecraft.util.IIcon;
import net.pufferlab.primal.utils.CropType;

public class ItemCropsFood extends ItemMetaFood {

    public CropType[] cropType;
    public IIcon icon;

    public ItemCropsFood(CropType[] cropType, String name) {
        super(CropType.getFoodTypes(cropType), name);
        this.cropType = cropType;
        for (int i = 0; i < cropType.length; i++) {
            cropType[i].cropItem = this;
            cropType[i].cropMeta = i;
        }
    }
}
