package com.hakitsyu.pixelquitbattle.commands;

import com.hakitsyu.pixelquitbattle.PixelQuitBattle;
import com.hakitsyu.pixelquitbattle.config.ConfigurationManager;
import com.hakitsyu.pixelquitbattle.utils.Permissions;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class ReloadChildCommand implements CommandExecutor {

    private PixelQuitBattle plugin;

    public ReloadChildCommand(PixelQuitBattle plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (src instanceof Player && !src.hasPermission(Permissions.RELOAD)) {
            src.sendMessage(plugin.getConfigMessage("need_permission"));
            return CommandResult.empty();
        }

        ConfigurationManager.getInstance().load();
        src.sendMessage(plugin.getConfigMessage("reload"));
        return CommandResult.success();
    }

}
