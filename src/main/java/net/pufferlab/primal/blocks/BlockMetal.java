package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.items.itemblocks.ItemBlockMetal;
import net.pufferlab.primal.utils.MetalType;

public class BlockMetal extends BlockMeta {

    public MetalType[] metalTypes;

    public BlockMetal(MetalType[] metalTypes, String type) {
        this(
            Material.iron,
            MetalType.getNames(metalTypes),
            type,
            Constants.none,
            MetalType.getTools(metalTypes),
            MetalType.getLevels(metalTypes));

        this.metalTypes = metalTypes;
        this.setStepSound(soundTypeMetal);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setHasSuffix();
    }

    public BlockMetal(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super(material, materials, type, blacklist, tools, levels);
    }

    public MetalType[] getMetalTypes() {
        return metalTypes;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockMetal.class;
    }
}
