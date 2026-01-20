package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaDirt extends BlockDirt implements IPrimalBlock, IMetaBlock {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public boolean isItemTexture;

    public BlockMetaDirt(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super();
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;

        for (int i = 0; i < elements.length; i++) {
            this.setHarvestLevel("shovel", 0, i);
        }

    }

    public BlockMetaDirt(Material material, String[] materials, String type) {
        this(material, materials, type, Constants.none, null, null);
    }

    public BlockMetaDirt setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaDirt setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaDirt setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public BlockMetaDirt setItemTexture() {
        this.isItemTexture = true;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        String prefix = "";
        if (isItemTexture) {
            prefix = "items/";
        }
        for (int i = 0; i < elements.length; i++) {
            if (elementsTextures == null) {
                if (!Utils.contains(elementsBlacklist, elements[i])) {
                    if (hasSuffix) {
                        icons[i] = register.registerIcon(Primal.MODID + ":" + prefix + elements[i] + "_" + name);
                    } else {
                        icons[i] = register.registerIcon(Primal.MODID + ":" + prefix + elements[i]);
                    }
                }
            } else {
                icons[i] = register.registerIcon(elementsTextures[i]);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i])) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= elements.length || Utils.contains(elementsBlacklist, elements[meta])) {
            return null;
        }
        return icons[meta];
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        return this.getIcon(side, worldIn.getBlockMetadata(x, y, z));
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return worldIn.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return Blocks.dirt.canSustainPlant(world, x, y, z, direction, plantable);
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    public String[] getElements() {
        return elements;
    }

    public String[] getElementsBlacklist() {
        return elementsBlacklist;
    }

    public String getElementName() {
        return name;
    }

    public boolean hasSuffix() {
        return this.hasSuffix;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockMeta.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }
}
