package net.pufferlab.primal.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.utils.CropType;

public class ItemBerryFood extends ItemMetaFood implements IPlantable {

    public CropType[] cropType;
    public IIcon icon;

    public ItemBerryFood(CropType[] cropType, String name) {
        super(CropType.getFoodTypes(cropType), name);
        this.cropType = cropType;
        for (int i = 0; i < cropType.length; i++) {
            if (cropType[i].hasCropFood) {
                cropType[i].setCropItem(this, i);
                cropType[i].setCropSeedItem(this, i);
            }
        }
        this.setBlacklist(CropType.getCropsFoodBlacklistNames(cropType));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (side != 1) {
            return false;
        } else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
            if (world.getBlock(x, y, z)
                .canSustainPlant(world, x, y, z, ForgeDirection.UP, this) && world.isAirBlock(x, y + 1, z)) {
                if (stack.getItemDamage() >= this.cropType.length) return false;
                world.setBlock(x, y + 1, z, this.cropType[stack.getItemDamage()].cropBlock);
                --stack.stackSize;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return world.getBlock(x, y, z);
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
}
