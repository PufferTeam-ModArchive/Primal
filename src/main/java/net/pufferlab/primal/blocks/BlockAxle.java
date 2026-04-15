package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelBracket;
import net.pufferlab.primal.client.models.ModelGear;
import net.pufferlab.primal.items.itemblocks.ItemBlockAxle;
import net.pufferlab.primal.tileentities.TileEntityAxle;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockAxle extends BlockMotion {

    public IIcon bracket;
    public IIcon particle;

    public static final int iconAxle = 99;

    public BlockAxle() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(worldIn, x, y, z);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            float b0 = 0.3125F;
            float b1 = 1 - b0;
            if (tef.axisMeta == 0) {
                this.setBlockBounds(b0, 0.0F, b0, b1, 1.0F, b1);
            } else if (tef.axisMeta == 1) {
                this.setBlockBounds(b0, b0, 0.0F, b1, b1, 1.0F);
            } else if (tef.axisMeta == 2) {
                this.setBlockBounds(0.0F, b0, b0, 1.0F, b1, b1);
            }
        }
    }

    public ModelBracket modelBracket = new ModelBracket();
    ModelGear modelGearPos = new ModelGear();
    ModelGear modelGearNeg = new ModelGear();

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, BoundsType type) {
        List<AxisAlignedBB> bounds = new ArrayList<>();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            if (tef.hasBracket) {
                modelBracket.setFacingFromAxis(tef.facingMeta, tef.axisMeta);
                bounds.addAll(modelBracket.getBounds());
            }
            if (type != BoundsType.rayTraced) {
                int axis = tef.axisMeta;
                if (tef.hasGearPos) {
                    modelGearPos.setAxis(axis);
                    bounds.addAll(modelGearPos.getBounds(0.0F, 0.5F, 0.0F));
                }
                if (tef.hasGearNeg) {
                    modelGearNeg.setAxisReversed(axis);
                    bounds.addAll(modelGearNeg.getBounds(0.0F, 0.5F, 0.0F));
                }
            }
        }
        return bounds;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem == null) return false;
            ItemStack gear = ItemUtils.getModItem("gear", 1);
            ItemStack bracket = ItemUtils.getModItem("bracket", 1);
            if (Utils.equalsStack(heldItem, gear)) {
                int axis = tef.axisMeta;
                int axisClicked = BlockUtils.getAxis(side);
                if (axis == axisClicked) {
                    tef.setGear(side, player);
                    return true;
                }
            }
            if (Utils.equalsStack(heldItem, bracket)) {
                return tef.setBracket(side, player);
            }
        }
        return false;
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            int numberGear = 0;
            if (tef.hasGearPos) {
                numberGear++;
            }
            if (tef.hasGearNeg) {
                numberGear++;
            }
            if (numberGear > 0) {
                dropItemStack(worldIn, x, y, z, new ItemStack(this, numberGear, 1));
            }
            tef.scheduleStrongUpdate();
            if (tef.hasBracket) {
                dropItemStack(worldIn, x, y, z, new ItemStack(this, 1, 2));
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        particle = reg.registerIcon("minecraft:planks_spruce");
        bracket = reg.registerIcon(Primal.MODID + ":bracket");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconAxle) {
            return bracket;
        }
        return particle;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAxle();
    }

    @Override
    public TileEntity createFacingTileEntity(int facingMeta, int axisMeta) {
        return new TileEntityAxle(facingMeta, axisMeta);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".axle";
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.axleRenderer;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockAxle.class;
    }
}
