package net.pitan76.solomonsrod;

import net.pitan76.easyapi.config.JsonConfig;
import net.pitan76.mcpitanlib.api.util.PlatformUtil;

import java.io.File;
import java.util.LinkedHashMap;

public class Config {
    public static File configFile = new File(PlatformUtil.getConfigFolderAsFile(), "solomons_rod.json");

    public static JsonConfig config = new JsonConfig();
    public static boolean initialized = false;

    // Config values
    public static boolean useHighlight = true;
    public static boolean infiniteDurability = false;
    public static int maxDamage = 192;

    public static void init() {
        if (initialized) return;
        initialized = true;

        if (configFile.exists())
            config.load(configFile);

        if (config.configMap == null)
            config.configMap = new LinkedHashMap<>();

        useHighlight = config.getBooleanOrCreate("use_highlight", true);
        infiniteDurability = config.getBooleanOrCreate("infinite_durability", false);
        maxDamage = config.getIntOrCreate("max_damage", 192);

        save();
    }

    public static boolean reload() {
        if (configFile.exists() && configFile.isFile()) {
            config.load(configFile);
            return true;
        }
        return false;
    }

    public static void save() {
        config.save(configFile, true);
    }
}
