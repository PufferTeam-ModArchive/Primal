package net.pufferlab.primal.client.renderer.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockGroundcover;
import net.pufferlab.primal.client.models.ModelItem;
import net.pufferlab.primal.client.models.ModelRock;
import net.pufferlab.primal.client.models.ModelRockSmall;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockGroundcoverRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelRock> modelRockThread = ThreadLocal.withInitial(ModelRock::new);
    private final ThreadLocal<ModelRockSmall> modelRock2Thread = ThreadLocal.withInitial(ModelRockSmall::new);
    private final ThreadLocal<Random> randomThread = ThreadLocal.withInitial(Random::new);
    private final ThreadLocal<ModelItem> modelItemThread = ThreadLocal.withInitial(ModelItem::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelRock modelRock = modelRockThread.get();
        ModelRockSmall modelRock2 = modelRock2Thread.get();
        ModelItem modelItem = modelItemThread.get();
        Random random = randomThread.get();

        Tessellator tess = Tessellator.instance;
        Random rand = Utils.getSeededRandom(random, x, y, z);

        int meta = world.getBlockMetadata(x, y, z);
        float range = 0.3F;
        float ox = (rand.nextFloat() * 2F - 1F) * range;
        float oz = (rand.nextFloat() * 2F - 1F) * range;

        float range2 = 0.2F;
        float o2 = (rand.nextFloat() * 2F - 1F) * range2;

        int rotation = 1 + rand.nextInt(4);
        int modelType = rand.nextInt(6);

        if (block instanceof BlockGroundcover block2) {
            int type = block2.getGroundcoverRenderType();
            if (type == BlockGroundcover.typeRock) {
                if (modelType > 3) {
                    modelRock2.setFacing(rotation);
                    modelRock2.render(renderer, tess, block, x, y, z, 0.0F, 0.0F, 0.0F, meta);
                } else {
                    modelRock.setFacing(rotation);
                    modelRock.render(renderer, tess, block, x, y, z, ox, 0.0F, oz, meta);
                }
            }
            if (type == BlockGroundcover.typeShell) {
                modelItem.setFacingOffset(-0.25F, 0.0F, -0.25F);
                modelItem.setFacing(rotation);
                modelItem.bb_main.rotateAngleY = modelItem.bb_main.rotateAngleY + o2;
                modelItem.bb_main.rotateAngleX = (float) Math.PI / 2;
                modelItem.renderItem(renderer, tess, block, x, y, z, ox, 0.0F, ox, meta, 0.5F + o2);
            }
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getGroundcoverRenderID();
    }
}
