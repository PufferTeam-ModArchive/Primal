package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.CropType;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCropsPrimal extends BlockCrops implements IPrimalBlock {

    public CropType cropType;
    public IIcon[] cropsIcons;
    public String name;
    int growStages;

    public BlockCropsPrimal(CropType cropType) {
        super();
        this.cropType = cropType;
        this.cropType.cropBlock = this;
        this.growStages = cropType.growStages;
        this.name = cropType.cropName;
    }

    @Override
    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        this.checkAndDropBlock(worldIn, x, y, z);

        if (worldIn.getBlockLightValue(x, y + 1, z) >= 9) {
            int l = worldIn.getBlockMetadata(x, y, z);

            if (l < growStages - 1) {
                float f = this.func_149864_n(worldIn, x, y, z);

                if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                    ++l;
                    worldIn.setBlockMetadataWithNotify(x, y, z, l, 2);
                }
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();

        if (metadata >= (growStages - 1)) {
            int number = Utils.getRandomInRange(world.rand, this.cropType.minCrops, this.cropType.maxCrops);
            for (int i = 0; i < number; i++) {
                ret.add(new ItemStack(this.func_149865_P(), 1, this.cropType.cropMeta));
            }

            int amount = 1;
            for (int i = 0; i < 2 + fortune; ++i) {
                if (world.rand.nextInt(15) <= metadata) {
                    amount++;
                }
            }
            ret.add(new ItemStack(this.func_149866_i(), amount, this.cropType.seedMeta));
        }

        return ret;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.cropsIcons = new IIcon[growStages];

        for (int i = 0; i < this.cropsIcons.length; ++i) {
            this.cropsIcons[i] = reg.registerIcon(Primal.MODID + ":" + this.name + "_stage_" + i);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > getMaxMeta()) {
            meta = getMaxMeta();
        }

        return this.cropsIcons[meta];
    }

    @Override
    public int getRenderShape() {
        return this.cropType.cropRenderType;
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        return this.cropType.seedItem;
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return this.cropType.seedMeta;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + "." + this.name;
    }

    @Override
    public int getMaxMeta() {
        return growStages - 1;
    }

    @Override
    public void func_149863_m(World p_149863_1_, int p_149863_2_, int p_149863_3_, int p_149863_4_) {
        int l = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + 1;

        if (l > (growStages - 1)) {
            l = (growStages - 1);
        }

        p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, l, 2);
    }

    @Override
    public boolean func_149851_a(World worldIn, int x, int y, int z, boolean isClient) {
        return worldIn.getBlockMetadata(x, y, z) != (growStages - 1);
    }

    protected Item func_149866_i() {
        return this.cropType.seedItem;
    }

    protected Item func_149865_P() {
        return this.cropType.cropItem;
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getCropsRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
