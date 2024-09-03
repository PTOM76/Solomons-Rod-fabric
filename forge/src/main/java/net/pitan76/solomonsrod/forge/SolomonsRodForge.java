package net.pitan76.solomonsrod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.pitan76.solomonsrod.SolomonsRod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SolomonsRod.MOD_ID)
public class SolomonsRodForge {
    public SolomonsRodForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SolomonsRod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        new SolomonsRod();
    }
}
