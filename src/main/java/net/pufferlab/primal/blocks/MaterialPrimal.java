package net.pufferlab.primal.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.material.MaterialLogic;

public class MaterialPrimal {

    public static final Material tannin = new MaterialLiquid(MapColor.grayColor);
    public static final Material limewater = new MaterialLiquid(MapColor.grayColor);
    public static final Material dye = new MaterialLiquid(MapColor.grayColor);
    public static final Material groundcover = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();

}
