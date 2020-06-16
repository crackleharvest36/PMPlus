package me.crackleharvest36.pmplus;

import java.net.URL;
import java.util.Scanner;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.crackleharvest36.pmplus.commands.message.MessageCommand;
import me.crackleharvest36.pmplus.commands.reply.ReplyCommand;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getCommand("msg").setExecutor(new MessageCommand());
		getCommand("reply").setExecutor(new ReplyCommand());
		
		this.saveDefaultConfig();

		URL url = null;
		Scanner scanner = null;
		try {
			url = new URL("https://pastebin.com/raw/yG2XYpCe");
			scanner = new Scanner(url.openStream());
		} catch (Exception e) {
			getLogger().severe("Couldn't find latest version for PMPlus");
		}
		
		String str = scanner.nextLine();
		
		PluginDescriptionFile pdf = this.getDescription();
		
		String ver = pdf.getVersion();
		if (!str.equals(ver)) {
			getLogger().info("There is a new update available for PMPlus!");
		}
	}
	
	public static Main getInstance() {
		return instance;
	}

}
