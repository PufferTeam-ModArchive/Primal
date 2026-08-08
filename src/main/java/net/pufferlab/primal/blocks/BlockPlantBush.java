package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.PlantType;

public class BlockPlantBush extends BlockMetaBush {

    public PlantType[] plantTypes;

    public BlockPlantBush(PlantType[] plantTypes, String name) {
        super(getPlantNames(plantTypes), name);
        this.plantTypes = plantTypes;
        for (int i = 0; i < plantTypes.length; i++) {
            plantTypes[i].setPlantItem(this, i);
        }
        this.setStepSound(soundTypeGrass);
    }

    public static String[] getPlantNames(PlantType[] plantTypes) {
        String[] types = new String[plantTypes.length];
        for (int i = 0; i < plantTypes.length; i++) {
            types[i] = plantTypes[i].name;
        }
        return types;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= plantTypes.length) return EnumPlantType.Plains;
        if (plantTypes[meta].isDesertic) return EnumPlantType.Desert;
        return EnumPlantType.Plains;
    }

    @Override
    public int getRenderShape(int meta) {
        if (meta >= plantTypes.length) return Constants.crossedModel;
        return plantTypes[meta].modelType;
    }

    @Override
    public boolean isSnowlogged(int meta) {
        if (meta >= plantTypes.length) return false;
        if (plantTypes[meta].isSnowy) return true;
        return false;
    }

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, EntityPlayer player, BoundsType bounds) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean snowy = isSnowlogged(meta);
        List<AxisAlignedBB> list = new ArrayList<>();
        if (snowy) {
            list.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F));
        }
        return list;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
