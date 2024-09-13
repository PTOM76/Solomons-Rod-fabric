package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.client.event.WorldRenderRegistry;
import net.pitan76.solomonsrod.renderer.HighlightRenderer;

public class SolomonsRodClient {

    public static void init() {
        Config.init();
        if (Config.useHighlight)
            WorldRenderRegistry.registerWorldRenderAfterLevel(new HighlightRenderer());
    }
}
