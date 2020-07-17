package nfn11.joinitem.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import nfn11.joinitem.main.JoinItem;

public class ReloadCommand extends JoinItemBaseCommand {

	public ReloadCommand() {
		super("reload", ADMIN_PERMISSION, true);
	}

	@Override
	public boolean execute(CommandSender sender, List<String> args) {
		Bukkit.getPluginManager().disablePlugin(JoinItem.getInstance());
		Bukkit.getPluginManager().enablePlugin(JoinItem.getInstance());
		sender.sendMessage("[CustomJoinItems-reloaded] Reloaded!");
		return true;
	}

	@Override
	public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
	}

}
