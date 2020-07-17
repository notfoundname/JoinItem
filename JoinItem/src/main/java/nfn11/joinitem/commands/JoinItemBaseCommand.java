package nfn11.joinitem.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import nfn11.joinitem.main.JoinItem;

public abstract class JoinItemBaseCommand {

	public static final String ADMIN_PERMISSION = "customjoinitems.admin";

	private String name;
	private String permission;
	private boolean allowConsole;
	
	protected JoinItemBaseCommand(String name, String permission, boolean allowConsole) {
		this.name = name.toLowerCase();
		this.permission = permission;
		this.allowConsole = allowConsole;
		JoinItem.getCommands().put(this.name, this);
	}
	
	public String getName() {
        return this.name;
    }

    public boolean isConsoleCommand() {
        return this.allowConsole;
    }

    public String getPermission() {
        return this.permission;
    }

    public abstract boolean execute(CommandSender sender, List<String> args);

    public abstract void completeTab(List<String> completion, CommandSender sender, List<String> args);

    public boolean hasPermission(CommandSender sender) {
        if (permission == null || "".equals(permission)) {
            return true; // There's no permissions required
        }

        return sender.hasPermission(permission);
    }
}
