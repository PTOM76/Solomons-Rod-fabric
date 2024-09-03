package net.pitan76.solomonsrod.fabric;

import net.pitan76.solomonsrod.SolomonsRod;
import net.fabricmc.api.ModInitializer;

public class SolomonsRodFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new SolomonsRod();
    }
}
