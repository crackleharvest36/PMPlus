package me.crackleharvest36.pmplus.commands.reply;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.crackleharvest36.pmplus.Main;
import me.crackleharvest36.pmplus.MessageManager;

public class ReplyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					Main.getInstance().getConfig().getString("players-only")));
			return true;
		}

		Player player = (Player) sender;

		if (!(args.length >= 1)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					Main.getInstance().getConfig().getString("reply-usage")));
			return true;
		}
		
		if (!(MessageManager.lastMessaged.containsKey(player))) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("no-one-to-reply-to")));
			return true;
		}
		
		Player target = Bukkit.getPlayer(MessageManager.lastMessaged.get(player));
		
		if (target == null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("player-not-found").replace("%player%", MessageManager.lastMessaged.get(player))));
			return true;
		}
		
        String message = "";
        for (String arg : args) {
            message = message + arg + " ";
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
		
		return true;

	}

}
