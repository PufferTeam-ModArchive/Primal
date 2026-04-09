package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelBracket;
import net.pufferlab.primal.client.models.ModelGear;
import net.pufferlab.primal.items.itemblocks.ItemBlockAxle;
import net.pufferlab.primal.tileentities.TileEntityAxle;
import net.pufferlab.primal.utils.BlockUtils;

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
    public MovingObjectPosition collisionRayTrace(World worldIn, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        List<AxisAlignedBB> bounds;
        bounds = getBoundsSimple(worldIn, x, y, z);
        if (bounds != null && !bounds.isEmpty()) {
            for (AxisAlignedBB bb : bounds) {
                MovingObjectPosition mop = BlockUtils.collisionRayTrace(bb, worldIn, x, y, z, startVec, endVec);
                if (mop != null) {
                    return mop;
                }
            }
        }
        return super.collisionRayTrace(worldIn, x, y, z, startVec, endVec);
    }

    public List<AxisAlignedBB> getBoundsSimple(World world, int x, int y, int z) {
        List<AxisAlignedBB> bounds = new ArrayList<>();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            if (tef.hasBracket) {
                modelBracket.setFacingFromAxis(tef.facingMeta, tef.axisMeta);
                bounds.addAll(modelBracket.getBounds());
            }
        }
        return bounds;
    }

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z) {
        List<AxisAlignedBB> bounds = new ArrayList<>();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            if (tef.hasBracket) {
                modelBracket.setFacingFromAxis(tef.facingMeta, tef.axisMeta);
                bounds.addAll(modelBracket.getBounds());
            }
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
        return bounds;
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        List<AxisAlignedBB> bounds;
        bounds = getBounds(worldIn, x, y, z);
        if (bounds != null && !bounds.isEmpty()) {
            for (AxisAlignedBB bb : bounds) {
                bb.offset(x, y, z);
                if (mask.intersectsWith(bb)) {
                    list.add(bb);
                }
            }
        }
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem == null) return false;
            if (heldItem.getItem() == Item.getItemFromBlock(this) && heldItem.getItemDamage() == 1) {
                int axis = tef.axisMeta;
                int axisClicked = BlockUtils.getAxis(side);
                if (axis == axisClicked) {
                    tef.setGear(side, player);
                    return true;
                }
            }
            if (heldItem.getItem() == Item.getItemFromBlock(this) && heldItem.getItemDamage() == 2) {
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

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
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
    public int getRenderType() {
        return Primal.proxy.getAxleRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockAxle.class;
    }
}
