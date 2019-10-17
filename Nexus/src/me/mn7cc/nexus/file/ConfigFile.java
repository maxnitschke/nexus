package me.mn7cc.nexus.file;

import java.io.File;
import java.util.LinkedHashMap;

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
		
		defaults.put("config.mysql.enable", false);
		defaults.put("config.mysql.hostname", "localhost");
		defaults.put("config.mysql.database", "minecraft");
		defaults.put("config.mysql.port", 3306);
		defaults.put("config.mysql.username", "root");
		defaults.put("config.mysql.password", "password");
		
		return defaults;
		
	}
	
	public boolean isUsingMySQL() { return getBooleanValue("config.mysql.enable"); }
	public String getMySQLHostname() { return getStringValue("config.mysql.hostname"); }
	public String getMySQLDatabase() { return getStringValue("config.mysql.database"); }
	public int getMySQLPort() { return getIntegerValue("config.mysql.port"); }
	public String getMySQLUsername() { return getStringValue("config.mysql.username"); }
	public String getMySQLPassword() { return getStringValue("config.mysql.password"); }
	
}