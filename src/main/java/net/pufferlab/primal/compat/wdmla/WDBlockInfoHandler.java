package net.pufferlab.primal.compat.wdmla;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.RecipeUtils;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class WDBlockInfoHandler implements IBlockComponentProvider {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdblockinfohandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        EntityPlayer player = accessor.getPlayer();
        PlayerData data = PlayerData.get(player);

        if (data.blockInfoDebug) {
            Block block = accessor.getBlock();
            String name = BlockUtils.getName(block);
            int meta = accessor.getMetadata();
            tooltip.child(new TextComponent("ID: " + name));
            tooltip.child(new TextComponent("Metadata: " + meta));
            boolean hastile = block.hasTileEntity(meta);
            tooltip.child(new TextComponent(RecipeUtils.getStateTooltip("HasTile", hastile, "Yes", "No")));

            TileEntity te = accessor.getTileEntity();
            if (te != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                te.writeToNBT(nbt);
                if (nbt.hasKey("facingMeta", 1)) {
                    int metaFacing = nbt.getByte("facingMeta");
                    tooltip.child(new TextComponent("Facing: " + metaFacing));
                }
                if (nbt.hasKey("axisMeta", 1)) {
                    int metaAxis = nbt.getByte("axisMeta");
                    tooltip.child(new TextComponent("Axis: " + metaAxis));
                }
                if (nbt.hasKey("materialMeta")) {
                    int metaMaterial = nbt.getShort("materialMeta");
                    tooltip.child(new TextComponent("Material: " + metaMaterial));
                }
                if (nbt.hasKey("materialMeta2")) {
                    int metaMaterial = nbt.getShort("materialMeta2");
                    tooltip.child(new TextComponent("Material2: " + metaMaterial));
                }
            }
        }

    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }

}
