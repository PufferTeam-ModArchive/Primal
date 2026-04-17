package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.tileentities.TileEntityFarmland;
import net.pufferlab.primal.utils.CropType;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.Tasks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCropsBush extends BlockCrops implements IPrimalBlock, IScheduledBlock {

    public CropType cropType;
    public IIcon[] cropsIcons;
    public String name;
    int growStages;

    public BlockCropsBush(CropType cropType) {
        super();
        this.cropType = cropType;
        this.cropType.cropBlock = this;
        this.growStages = cropType.growStages;
        this.name = cropType.cropName;
    }

    @Override
    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        this.checkAndDropBlock(worldIn, x, y, z);

        if (!sentSchedule(worldIn, x, y, z, Tasks.growth)) {
            updateGrowth(worldIn, x, y, z, worldIn.rand);
        }
    }

    @Override
    public float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_) {
        return 1.0F;
    }

    public void updateGrowth(World worldIn, int x, int y, int z, Random rand) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (meta != (this.growStages - 1) && worldIn.getBlockLightValue(x, y + 1, z) >= 9) {
            TileEntity te = worldIn.getTileEntity(x, y - 1, z);
            float growthSpeed = 0.75F;
            if (te instanceof TileEntityFarmland farmland) {
                growthSpeed = farmland.getGrowthSpeed(this.cropType.nutrient);
            } else {
                if (needsFarmland()) return;
            }
            int ticksToGrow = this.cropType.getGrowthTicks(rand);
            int updateTick = (int) (((float) ticksToGrow) / growthSpeed);
            addSchedule(worldIn, x, y, z, updateTick, Tasks.growth);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);

        if (!sentSchedule(worldIn, x, y, z, Tasks.growth)) {
            updateGrowth(worldIn, x, y, z, worldIn.rand);
        }
    }

    public boolean needsFarmland() {
        return true;
    }

    @Override
    public void onScheduleTask(World world, int x, int y, int z, Tasks task) {
        IScheduledBlock.super.onScheduleTask(world, x, y, z, task);

        if (task == Tasks.growth) {
            grow(world, x, y, z);
        }
    }

    public void grow(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y - 1, z);
        if (needsFarmland()) {
            if (te instanceof TileEntityFarmland farmland) {
                farmland.consumeNutrient(
                    this.cropType.nutrient,
                    this.cropType.nutrientConsumption / (float) this.cropType.growStages);
            }
        }
        int l = world.getBlockMetadata(x, y, z) + 1;

        if (l > (growStages - 1)) {
            l = (growStages - 1);
        }

        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        updateGrowth(world, x, y, z, world.rand);
    }

    public void bonemealGrow(World world, int x, int y, int z) {
        if (Config.bonemealInstantGrowth.getBoolean()) {
            grow(world, x, y, z);
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
    public void func_149863_m(World world, int x, int y, int z) {
        bonemealGrow(world, x, y, z);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        removeSchedule(worldIn, x, y, z);
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
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getCropsRenderer();
    }

    @Override
    public int getRenderType() {
        return getRenderId();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public Block getBlock() {
        return this;
    }
}
