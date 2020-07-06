package nfn11.joinitem.main;

import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

import nfn11.joinitem.commands.JoinItemBaseCommand;

public class JoinItem extends JavaPlugin {
	private static JoinItem instance;
	private Configurator configurator;
	private HashMap<String, JoinItemBaseCommand> commands;
	
	@Override
	public void onEnable() {
		instance = this;
		
		configurator = new Configurator(this);
		configurator.loadDefaults();
	}
	
	public static Configurator getConfigurator() {
		return instance.configurator;
	}

	public static JoinItem getInstance() {
		return instance;
	}
	
	public static HashMap<String, JoinItemBaseCommand> getCommands() {
		return instance.commands;
	}
}
