package nfn11.joinitem.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import nfn11.joinitem.main.JoinItem;

public class JoinItemUtils {

	public static void dispatchCommands(int slot, Player player) {
		List<String> commands = JoinItem.getConfigurator().config.getStringList("data." + slot + "commands");

		if (commands.isEmpty() || commands == null)
			return;

		for (String command : commands) {
			if (command.startsWith("[console]: ")) {
				command = command.replaceFirst("[console]: ", "").replace("%player%", player.getName());
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
				continue;
			}
			if (command.startsWith("[player]: ")) {
				command = command.replaceFirst("[player]: ", "").replace("%player%", player.getName());
				command = "/" + command;
				player.performCommand(command);
				continue;
			} else
				continue;
		}
		return;
	}
}
