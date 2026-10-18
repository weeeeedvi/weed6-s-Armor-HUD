package com.weedvi.armorhud;

import com.weedvi.armorhud.config.ModConfig;
import com.weedvi.armorhud.render.ArmorHudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ArmorHud implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModConfig.get();
        HudRenderCallback.EVENT.register(ArmorHudRenderer::render);
    }
}
