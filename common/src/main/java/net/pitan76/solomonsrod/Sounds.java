package net.pitan76.solomonsrod;

import net.minecraft.sound.SoundEvent;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

public class Sounds {

    public static final RegistryResult<SoundEvent> CREATE_SOUND = register("create");
    public static final RegistryResult<SoundEvent> CRASH_SOUND = register("crash");
    public static final RegistryResult<SoundEvent> ERASE_SOUND = register("erase");
    public static final RegistryResult<SoundEvent> BAM_SOUND = register("bam");
    public static final RegistryResult<SoundEvent> NOCRASH_SOUND = register("nocrash");

    public static void init() {

    }

    private static RegistryResult<SoundEvent> register(String id) {
        return SolomonsRod.INSTANCE.registry.registerSoundEvent(SolomonsRod.INSTANCE.compatId(id));
    }
}
