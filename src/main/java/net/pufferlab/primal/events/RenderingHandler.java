package net.pufferlab.primal.events;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.renderer.RenderAccessory;
import net.pufferlab.primal.client.renderer.RenderBounds;
import net.pufferlab.primal.client.renderer.RenderDebug;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler implements IEventHandler {

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
        if (Config.wearableRenderer.getBoolean()) {
            RenderAccessory.handleRendering(event.entityLiving, event.renderer);
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        boolean state = RenderBounds.handleRendering(event.player, event.target, event.partialTicks);
        if (!state) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        RenderDebug.handleDebugMenuText(Primal.proxy.getClientPlayer(), event.left, event.right);
    }

}
