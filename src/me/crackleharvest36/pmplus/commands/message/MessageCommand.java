package me.crackleharvest36.pmplus.commands.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.crackleharvest36.pmplus.Main;
import me.crackleharvest36.pmplus.MessageManager;

public class MessageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					Main.getInstance().getConfig().getString("players-only")));
			return true;
		}

		Player player = (Player) sender;

		if (!(args.length >= 2)) {
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("msg-usage")));
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					Main.getInstance().getConfig().getString("player-not-found").replace("%player%", args[0])));
			return true;
		}

		String message = "";
		for (String arg : args) {
			if (arg != args[0]) {
				message = message + arg + " ";
			}
		}

		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
				Main.getInstance().getConfig().getString("output-msg").replace("%sender%", player.getName())
						.replace("%target%", target.getName().replace("%msg$", message))));

		target.sendMessage(ChatColor.translateAlternateColorCodes('&',
				Main.getInstance().getConfig().getString("output-msg").replace("%sender%", player.getName())
						.replace("%target%", target.getName().replace("%msg$", message))));

		if (!(Main.getInstance().getConfig().getString("sound-to-play").equalsIgnoreCase("NO_SOUND"))) {

			if (Sound.valueOf(Main.getInstance().getConfig().getString("sound-to-play")) == null) {
				Main.getInstance().getLogger().info(
						"Sound \"" + Main.getInstance().getConfig().getString("sound-to-play") + "\" was not found!");
			} else {

				player.playSound(player.getLocation(),
						Sound.valueOf(Main.getInstance().getConfig().getString("sound-to-play").toUpperCase()), 1, 1);
				target.playSound(target.getLocation(),
						Sound.valueOf(Main.getInstance().getConfig().getString("sound-to-play").toUpperCase()), 1, 1);

			}
		}
		
		MessageManager.lastMessaged.put(player, target.getName());

		return true;

	}

}
