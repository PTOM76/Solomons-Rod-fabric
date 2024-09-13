package net.pitan76.solomonsrod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.pitan76.solomonsrod.SolomonsRodClient;

public class SolomonsRodClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SolomonsRodClient.init();
    }
}
