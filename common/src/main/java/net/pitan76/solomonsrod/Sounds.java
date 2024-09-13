package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.sound.CompatSoundEvent;

public class Sounds {

    public static final CompatSoundEvent CREATE_SOUND = register("create");
    public static final CompatSoundEvent CRASH_SOUND = register("crash");
    public static final CompatSoundEvent ERASE_SOUND = register("erase");
    public static final CompatSoundEvent BAM_SOUND = register("bam");
    public static final CompatSoundEvent NOCRASH_SOUND = register("nocrash");

    public static void init() {

    }

    private static CompatSoundEvent register(String id) {
        return SolomonsRod.INSTANCE.registry.registerCompatSoundEvent(SolomonsRod.INSTANCE.compatId(id));
    }
}
