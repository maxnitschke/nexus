package me.mn7cc.nexus.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.mn7cc.nexus.NexusFileManager;

public class CommandsFile extends BaseFile {
	
	public CommandsFile(NexusFileManager fileManager) {
		super(fileManager, getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "commands.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/Nexus/config/commands.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		defaults.put("commands.aliases.time day", new ArrayList<String>(Arrays.asList("day")));
		defaults.put("commands.aliases.time night", new ArrayList<String>(Arrays.asList("night")));
		defaults.put("commands.aliases.time dawn", new ArrayList<String>(Arrays.asList("dawn", "sunrise", "time sunrise")));
		defaults.put("commands.aliases.time dusk", new ArrayList<String>(Arrays.asList("dusk", "sunset", "time sunset")));
		defaults.put("commands.aliases.weather sun", new ArrayList<String>(Arrays.asList("sun")));
		defaults.put("commands.aliases.weather rain", new ArrayList<String>(Arrays.asList("rain")));
		defaults.put("commands.aliases.weather thunder", new ArrayList<String>(Arrays.asList("thunder")));
		defaults.put("commands.aliases.home set", new ArrayList<String>(Arrays.asList("sethome")));
		defaults.put("commands.aliases.home delete", new ArrayList<String>(Arrays.asList("delhome")));
		defaults.put("commands.aliases.home list", new ArrayList<String>(Arrays.asList("homes")));
		defaults.put("commands.aliases.warp set", new ArrayList<String>(Arrays.asList("setwarp")));
		defaults.put("commands.aliases.warp delete", new ArrayList<String>(Arrays.asList("delwarp")));
		defaults.put("commands.aliases.warp list", new ArrayList<String>(Arrays.asList("warps")));
		defaults.put("commands.disabled", new ArrayList<String>(Arrays.asList("command1", "command2")));
		
		return defaults;
		
	}
	
	public HashMap<String, List<String>> getAliases() {
	
		HashMap<String, List<String>> aliases = new HashMap<String, List<String>>();
		
		ConfigurationSection configurationSection = getConfigurationSection("commands.aliases");
		if(configurationSection == null) return aliases;
		
		for(String command : configurationSection.getKeys(false)) {
			aliases.put(command, getStringListValue("commands.aliases." + command));
		}
	
		return aliases;
		
	}
	
	public List<String> getDisabledCommands() { return getStringListValue("commands.disabled"); }
	
	public String getServerId() { return getStringValue("config.network.server-id"); }
	public int getDatabaseConnectionPoolSize() { return getIntegerValue("config.database.connection-pool-size"); }
	public String getDatabaseTablePrefix(String table) { return containsValue("config.database.table-prefix." + table.toLowerCase()) ? getStringValue("config.database.table-prefix." + table.toLowerCase()) : "nexus_"; }
	public boolean isUsingMySQL() { return getBooleanValue("config.database.mysql.enable"); }
	public String getMySQLHostname() { return getStringValue("config.database.mysql.hostname"); }
	public String getMySQLDatabase() { return getStringValue("config.database.mysql.database"); }
	public int getMySQLPort() { return getIntegerValue("config.database.mysql.port"); }
	public String getMySQLUsername() { return getStringValue("config.database.mysql.username"); }
	public String getMySQLPassword() { return getStringValue("config.database.mysql.password"); }
	
}