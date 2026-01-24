package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMetaGravel extends BlockMetaFalling {

    public BlockMetaGravel(Material material, String[] materials, String type) {
        super(material, materials, type);
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return Blocks.gravel.canSustainPlant(world, x, y, z, direction, plantable);
    }
}
