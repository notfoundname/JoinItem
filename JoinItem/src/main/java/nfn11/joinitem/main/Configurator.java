package nfn11.joinitem.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configurator {
	public File config_file;
	public FileConfiguration config;

	public final File dataFolder;
	public final JoinItem main;

	public Configurator(JoinItem main) {
		this.dataFolder = main.getDataFolder();
		this.main = main;
	}

	@SuppressWarnings("serial")
	public void loadDefaults() {
		dataFolder.mkdirs();

		config_file = new File(dataFolder, "config.yml");

		config = new YamlConfiguration();

		if (!config_file.exists()) {
			try {
				config_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			config.load(config_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		AtomicBoolean modify = new AtomicBoolean(false);

		checkOrSetConfig(modify, "version", 1);

		if (config.getInt("version") != 1) {
			config_file.renameTo(new File(dataFolder, "config_backup.yml"));
			Bukkit.getServer().getLogger()
					.info("[JoinItem] Your XPWars configuration file was backed up. Please transfer values.");
			loadDefaults();
			return;
		}
		checkOrSetConfig(modify, "check-for-updates", false);

		checkOrSetConfig(modify, "data", new HashMap<Integer, Object>() {
			{
				put(4, new HashMap<String, Object>() {
					{
						put("allow-drag", false);
						put("allow-drop", false);
						put("allowed-worlds", new ArrayList<String>() {
							{
								add("world");
								add("test");
							}
						});
						put("allowed-worlds-as-blacklist", false);
						put("only-on-first-join", true);
						put("give-at-respawn", true);
						put("cooldown", 5);
						put("permissions", new ArrayList<String>() {
							{
								add("OP");
								add("joinitem.test");
							}
						});
						put("interact-triggers", new ArrayList<String>() {
							{
								add("LEFT_CLICK_AIR");
								add("RIGHT_CLICK_AIR");
								add("LEFT_CLICK_BLOCK");
								add("RIGHT_CLICK_BLOCK");
							}
						});
						put("stack", "COMPASS;1;ExampleItem;===;&fMATERIAL;AMOUNT;NAME;LORE;LORE;...;LORE");
						put("commands", new ArrayList<String>() {
							{
								add("[console]: tell %player% hey bro");
								add("[player]: me sup");
							}
						});
					}
				});
			}
		});

		if (modify.get()) {
			try {
				config.save(config_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveConfig() {
		try {
			config.save(config_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkOrSetConfig(AtomicBoolean modify, String path, Object value) {
		checkOrSet(modify, this.config, path, value);
	}

	private static void checkOrSet(AtomicBoolean modify, FileConfiguration config, String path, Object value) {
		if (!config.isSet(path)) {
			if (value instanceof Map) {
				config.createSection(path, (Map<?, ?>) value);
			} else {
				config.set(path, value);
			}
			modify.set(true);
		}
	}
}
