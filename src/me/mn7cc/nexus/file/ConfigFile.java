package me.mn7cc.nexus.file;

import java.io.File;
import java.util.LinkedHashMap;

import me.mn7cc.nexus.NexusFileManager;

public class ConfigFile extends BaseFile {
	
	public ConfigFile(NexusFileManager fileManager) {
		super(fileManager, getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "config.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/Nexus/config/config.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		defaults.put("config.database.connection-pool-size", 5);
		defaults.put("config.database.mysql.enable", false);
		defaults.put("config.database.mysql.hostname", "localhost");
		defaults.put("config.datanase.mysql.database", "minecraft");
		defaults.put("config.database.mysql.port", 3306);
		defaults.put("config.database.mysql.username", "root");
		defaults.put("config.database.mysql.password", "password");
		defaults.put("config.database.table-prefix.ban", "nexus_");
		defaults.put("config.database.table-prefix.home", "nexus_");
		defaults.put("config.database.table-prefix.mute", "nexus_");
		defaults.put("config.database.table-prefix.player", "nexus_");
		defaults.put("config.database.table-prefix.portal", "nexus_");
		defaults.put("config.database.table-prefix.spawn", "nexus_");
		defaults.put("config.database.table-prefix.ticket", "nexus_");
		defaults.put("config.database.table-prefix.warp", "nexus_");
		defaults.put("config.network.server-id", "server1");
		
		return defaults;
		
	}
	
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