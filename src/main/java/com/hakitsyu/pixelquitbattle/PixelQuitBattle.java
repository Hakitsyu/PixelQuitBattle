package com.hakitsyu.pixelquitbattle;

import com.google.inject.Inject;
import com.hakitsyu.pixelquitbattle.commands.Command;
import com.hakitsyu.pixelquitbattle.commands.ReloadChildCommand;
import com.hakitsyu.pixelquitbattle.config.ConfigurationManager;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.File;

@Plugin(
        id = "pixelquitbattle",
        name = "PixelQuitBattle"
)
public class PixelQuitBattle {

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File configurationFile;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    @Listener
    public void onInitialization(GameInitializationEvent event) {
        setupConfig();
        setupCommand();
    }

    private void setupConfig() {
        ConfigurationManager.getInstance().init(this, configurationFile, configurationLoader);
    }

    private void setupCommand() {
        CommandSpec reloadChildCommand = CommandSpec.builder()
                .description(Text.of("Use this to reload the configuration"))
                .executor(new ReloadChildCommand(this))
                .build();

        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Use this command to quit of a pokemon battle"))
                .child(reloadChildCommand, "reload")
                .executor(new Command(this))
                .build();

        game.getCommandManager().register(this, commandSpec, "quitbattle", "qbattle");
    }

    public Game getGame() {
        return game;
    }

    public Logger getLogger() {
        return logger;
    }

    public CommentedConfigurationNode getConfig() {
        return ConfigurationManager.getInstance().getConfig();
    }

    public Text getConfigMessage(String key) {
        return TextSerializers.FORMATTING_CODE.deserialize(getConfig().getNode("messages", key).getString());
    }

}
