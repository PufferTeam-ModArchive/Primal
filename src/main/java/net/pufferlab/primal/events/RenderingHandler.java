package net.pufferlab.primal.events;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.client.renderer.RenderAccessory;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
        if (Config.wearableRenderer.getBoolean()) {
            RenderAccessory.instance.handleRendering(event.entityLiving, event.renderer);
        }
    }

}
