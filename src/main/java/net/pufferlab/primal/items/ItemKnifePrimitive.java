package net.pufferlab.primal.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKnifePrimitive extends ItemSword {

    String toolName;
    protected ToolMaterial toolMaterial;

    public ItemKnifePrimitive(ToolMaterial material, String name) {
        super(material);
        this.toolMaterial = material;
        this.maxStackSize = 1;
        this.toolName = name;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
        this.field_150934_a = 3.0F + toolMaterial.getDamageVsEntity();
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }


    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;

        Block block = world.getBlock(x,y,z);
        int meta = world.getBlockMetadata(x,y,z);
        Block target = null;

        if (block == Blocks.log) {
            target = GameRegistry.findBlock("etfuturum", "log_stripped");
        } else if (block == Blocks.log2) {
            target = GameRegistry.findBlock("etfuturum", "log2_stripped");
        }


        System.out.println(target);

        if (target!=null)  {
            world.setBlock(x,y,z, target, meta,3);
            itemStack.damageItem(1, player);
            world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, "dig_wood", 1.0F, 0.8F);

            ItemStack droppedStack = new ItemStack(Registry.wood, 1, 2);
            EntityItem entityItem = new EntityItem(world, x+0.5F, y+0.5F, z+0.5F, droppedStack);

            entityItem.motionX = world.rand.nextGaussian() *0.05D;
            entityItem.motionY = 0.02D;
            entityItem.motionZ = world.rand.nextGaussian() *0.05D;

            world.spawnEntityInWorld(entityItem);
        }



        return true;

    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        return itemStackIn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(Primal.MODID + ":" + toolName);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Primal.MODID + "." + toolName;
    }

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        ItemStack material = this.toolMaterial.getRepairItemStack();
        return material != null && net.minecraftforge.oredict.OreDictionary.itemMatches(material, repairMaterial, false);
    }
}
