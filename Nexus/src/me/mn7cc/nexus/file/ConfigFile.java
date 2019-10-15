package me.mn7cc.nexus.file;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ConfigFile extends BaseFile {
	
	public ConfigFile() {
		super(getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "config.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/Nexus/config.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		defaults.put("mysql.enable", false);
		defaults.put("mysql.hostname", "localhost");
		defaults.put("mysql.database", "minecraft");
		defaults.put("mysql.port", 3306);
		defaults.put("mysql.username", "root");
		defaults.put("mysql.password", "password");
		defaults.put("min-fly-speed", 1);
		defaults.put("max-fly-speed", 5);
		defaults.put("disabled-worlds", Arrays.asList("world_no_1", "another_world"));
		
		return defaults;
		
	}
	
	public boolean isUsingMySQL() { return getBooleanValue("mysql.enable"); }
	public String getMySQLHostname() { return getStringValue("mysql.hostname"); }
	public String getMySQLDatabase() { return getStringValue("mysql.database"); }
	public int getMySQLPort() { return getIntegerValue("mysql.port"); }
	public String getMySQLUsername() { return getStringValue("mysql.username"); }
	public String getMySQLPassword() { return getStringValue("mysql.password"); }
	public double getMinFlySpeed() { return getIntegerValue("min-fly-speed"); }
	public double getMaxFlySpeed() { return getIntegerValue("max-fly-speed"); }
	public List<String> getDisabledWorlds() { return getStringListValue("disabled-worlds"); }
	
}