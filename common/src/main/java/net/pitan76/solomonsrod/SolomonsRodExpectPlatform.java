package net.pitan76.solomonsrod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import java.nio.file.Path;

public class SolomonsRodExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }
}
