package com.hakitsyu.pixelquitbattle.config;

import com.hakitsyu.pixelquitbattle.PixelQuitBattle;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ConfigurationManager {

    private static ConfigurationManager INSTANCE = new ConfigurationManager();

    public static ConfigurationManager getInstance() {
        return INSTANCE;
    }

    private PixelQuitBattle plugin;
    private File configurationFile;
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private CommentedConfigurationNode config;

    public void init(PixelQuitBattle plugin, File configurationFile, ConfigurationLoader<CommentedConfigurationNode> configurationLoader) {
        this.plugin = plugin;
        this.configurationFile = configurationFile;
        this.configurationLoader = configurationLoader;

        if (!configurationFile.exists())
            copyDefaultConfig();
        load();
    }

    public void save() {
        try {
            plugin.getLogger().debug("Trying save the configuration file");
            configurationLoader.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            plugin.getLogger().debug("Trying load the configuration file");
            config = configurationLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDefaultConfig() {
        Optional<Asset> asset = Sponge.getAssetManager().getAsset(plugin, "default.conf");
        plugin.getLogger().debug("Checking if default.conf exists");

        if (asset.isPresent()) {
            try {
                plugin.getLogger().debug("default.conf is present");
                plugin.getLogger().debug("Trying copy the default config");
                asset.get().copyToFile(configurationFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }

}
