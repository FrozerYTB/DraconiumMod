package fr.draconiummc.draconiummod.world;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModConfig {
    public static boolean enableOreGeneration = true;

    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        enableOreGeneration = config.getBoolean("enableOreGeneration", "generation", false,
                "Définir sur false pour désactiver la génération des minerais moddés.");

        config.save();
    }
}