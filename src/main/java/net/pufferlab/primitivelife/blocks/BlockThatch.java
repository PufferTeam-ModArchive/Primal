package net.pufferlab.primitivelife.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Registry;

public class BlockThatch extends Block {

    public IIcon[] icons = new IIcon[1];

    public BlockThatch() {
        super(Material.grass);
        this.setHardness(0.2F);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(Registry.thatch);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(PrimitiveLife.MODID + ":thatch_grass");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + PrimitiveLife.MODID + ".thatch";
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
