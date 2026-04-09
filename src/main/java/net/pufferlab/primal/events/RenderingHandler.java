package net.pufferlab.primal.events;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.client.renderer.RenderAccessory;
import net.pufferlab.primal.client.renderer.RenderBounds;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
        if (Config.wearableRenderer.getBoolean()) {
            RenderAccessory.handleRendering(event.entityLiving, event.renderer);
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        RenderBounds.handleRendering(event.player, event.target, event.partialTicks);
    }

}
