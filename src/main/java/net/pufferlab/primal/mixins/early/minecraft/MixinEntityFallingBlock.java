package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(EntityFallingBlock.class)
public abstract class MixinEntityFallingBlock extends Entity {
    @Shadow
    private Block field_145811_e;
    @Shadow
    public int field_145814_a;
    @Shadow
    public NBTTagCompound field_145810_d;

    public MixinEntityFallingBlock(World worldIn) {
        super(worldIn);
    }

    @Override
    public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY)
    {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);

        placeBlock(x, y, z);
        return null;
    }

    public void placeBlock(int i, int j, int k) {
        Block block = this.worldObj.getBlock(i, j, k);
        int meta = this.worldObj.getBlockMetadata(i, j, k);
        if(block.getMaterial() != Material.air) {
            block.dropBlockAsItem(this.worldObj, i, j, k, meta, 0);
            Primal.proxy.renderFX(this.worldObj, i, j, k, block, meta);
            this.worldObj.setBlockToAir(i, j, k);
        }
        this.worldObj.setBlock(i, j, k, this.field_145811_e, this.field_145814_a, 3);

        if (this.field_145811_e instanceof BlockFalling)
        {
            ((BlockFalling)this.field_145811_e).func_149828_a(this.worldObj, i, j, k, this.field_145814_a);
        }

        if (this.field_145810_d != null && this.field_145811_e instanceof ITileEntityProvider)
        {
            TileEntity tileentity = this.worldObj.getTileEntity(i, j, k);

            if (tileentity != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                tileentity.writeToNBT(nbttagcompound);
                Iterator iterator = this.field_145810_d.func_150296_c().iterator();

                while (iterator.hasNext())
                {
                    String  s = (String)iterator.next();
                    NBTBase nbtbase = this.field_145810_d.getTag(s);

                    if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                    {
                        nbttagcompound.setTag(s, nbtbase.copy());
                    }
                }

                tileentity.readFromNBT(nbttagcompound);
                tileentity.markDirty();
            }
        }
    }
}
