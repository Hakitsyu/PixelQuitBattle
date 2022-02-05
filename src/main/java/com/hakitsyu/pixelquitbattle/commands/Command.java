package com.hakitsyu.pixelquitbattle.commands;

import com.hakitsyu.pixelquitbattle.PixelQuitBattle;
import com.hakitsyu.pixelquitbattle.utils.CooldownManager;
import com.hakitsyu.pixelquitbattle.utils.Permissions;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class Command implements CommandExecutor {

    private PixelQuitBattle plugin;
    private CooldownManager cooldown;

    public Command(PixelQuitBattle plugin) {
        this.plugin = plugin;
        this.cooldown = new CooldownManager(plugin.getConfig().getNode("cooldown").getInt());
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(plugin.getConfigMessage("inconsole"));
            return CommandResult.empty();
        }

        Player player = (Player) src;
        if (!player.hasPermission(Permissions.QUITBATTLE)) {
            player.sendMessage(plugin.getConfigMessage("need_permission"));
            return CommandResult.empty();
        }

        if (!player.hasPermission(Permissions.BYPASS_COOLDOWN) && cooldown.contains(player)) {
            if (!cooldown.ended(player)) {
                player.sendMessage(plugin.getConfigMessage("incooldown").replace("%s", Text.of(cooldown.timeToEnd(player) / 1000)));
                return CommandResult.empty();
            }

            cooldown.remove(player);
        }

        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "endbattle " + player.getName());
        player.sendMessage(plugin.getConfigMessage("quit"));
        if (!player.hasPermission(Permissions.BYPASS_COOLDOWN)) cooldown.apply(player);
        return CommandResult.empty();
    }

}
