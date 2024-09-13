package net.pitan76.solomonsrod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.pitan76.solomonsrod.SolomonsRod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pitan76.solomonsrod.SolomonsRodClient;

@Mod(SolomonsRod.MOD_ID)
public class SolomonsRodForge {
    public SolomonsRodForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(SolomonsRod.MOD_ID, bus);

        bus.addListener(SolomonsRodForge::onSetupClient);

        new SolomonsRod();
    }

    public static void onSetupClient(FMLClientSetupEvent event) {
        SolomonsRodClient.init();
    }
}
