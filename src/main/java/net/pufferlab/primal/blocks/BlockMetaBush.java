package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaBush extends BlockBush implements IPrimalBlock, IMetaBlock, IPlantable {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public boolean isItemTexture;

    public BlockMetaBush(String[] materials, String type, String[] blacklist) {
        super(Material.plants);
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;

    }

    public BlockMetaBush(String[] materials, String type) {
        this(materials, type, Constants.none);
    }

    public BlockMetaBush setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaBush setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaBush setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public BlockMetaBush setItemTexture() {
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

    @Override
    public int getRenderShape(int meta) {
        return Constants.crossedModel;
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
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }
}
