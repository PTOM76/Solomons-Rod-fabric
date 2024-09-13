package net.pitan76.solomonsrod;

import net.pitan76.easyapi.config.JsonConfig;
import net.pitan76.mcpitanlib.api.util.PlatformUtil;

import java.io.File;
import java.util.LinkedHashMap;

public class Config {
    public static File configFile = new File(PlatformUtil.getConfigFolder().toFile(), "solomons_rod.json");

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

        useHighlight = config.getBooleanOrDefault("use_highlight", true);
        infiniteDurability = config.getBooleanOrDefault("infinite_durability", false);
        maxDamage = config.getIntOrDefault("max_damage", 192);

        if (!config.has("use_highlight"))
            config.setBoolean("use_highlight", useHighlight);

        if (!config.has("infinite_durability"))
            config.setBoolean("infinite_durability", infiniteDurability);

        if (!config.has("max_damage"))
            config.setInt("max_damage", maxDamage);

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

    public static File getConfigFile() {
        return configFile;
    }

    public static File getConfigDir() {
        return configFile.getParentFile();
    }

    public static void setConfigDir(File configDir) {
        Config.configFile = new File(configDir, "solomons_rod.json");
    }
}
