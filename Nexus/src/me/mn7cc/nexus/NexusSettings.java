package me.mn7cc.nexus;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.mn7cc.nexus.file.CommandsFile;
import me.mn7cc.nexus.file.ConfigFile;
import me.mn7cc.nexus.file.ModulesFile;
import me.mn7cc.nexus.util.TimeUtils;

public class NexusSettings {
	
	private boolean isProxy;
	private String serverId;
	
	private int databaseConnectionPoolSize;
	
	private String databaseBanTableId;
	private String databaseHomeTableId;
	private String databaseMuteTableId;
	private String databasePlayerTableId;
	private String databasePortalTableId;
	private String databaseSpawnTableId;
	private String databaseTicketTableId;
	private String databaseWarpTableId;
	
	private boolean isDatabaseUsingMySQL;
	private String databaseMySQLHostname;
	private String databaseMySQLDatabase;
	private int databaseMySQLPort;
	private String databaseMySQLUsername;
	private String databaseMySQLPassword;
	
	private double teleportRequestTimeout;
	private double teleportDelay;
	
	private HashMap<String, List<String>> commandAliases;
	private List<String> disabledCommands;
	
	public NexusSettings(boolean isProxy, Nexus instance) {
		
		ConfigFile configFile = instance.getFileManager().getConfigFile();
		ModulesFile modulesFile = instance.getFileManager().getModulesFile();
		CommandsFile commandsFile = instance.getFileManager().getCommandsFile();
		
		this.isProxy = isProxy;
		this.serverId = configFile.getServerId();
		
		this.databaseConnectionPoolSize = configFile.getDatabaseConnectionPoolSize();
		
		this.databaseBanTableId = configFile.getDatabaseTablePrefix("ban") + "ban";
		this.databaseHomeTableId = configFile.getDatabaseTablePrefix("home") + "home";
		this.databaseMuteTableId = configFile.getDatabaseTablePrefix("mute") + "mute";
		this.databasePlayerTableId = configFile.getDatabaseTablePrefix("player") + "player";
		this.databasePortalTableId = configFile.getDatabaseTablePrefix("portal") + "portal";
		this.databaseSpawnTableId = configFile.getDatabaseTablePrefix("spawn") + "spawn";
		this.databaseTicketTableId = configFile.getDatabaseTablePrefix("ticket") + "ticket";
		this.databaseWarpTableId = configFile.getDatabaseTablePrefix("warp") + "warp";
		
		this.isDatabaseUsingMySQL = configFile.isUsingMySQL();
		this.databaseMySQLHostname = configFile.getMySQLHostname();
		this.databaseMySQLDatabase = configFile.getMySQLDatabase();
		this.databaseMySQLPort = configFile.getMySQLPort();
		this.databaseMySQLUsername = configFile.getMySQLUsername();
		this.databaseMySQLPassword = configFile.getMySQLPassword();
		
		this.teleportRequestTimeout = TimeUtils.parseTime(modulesFile.getTeleportationTeleportRequestTimeout());
		this.teleportDelay = TimeUtils.parseTime(modulesFile.getTeleportationTeleportDelay());
		
		this.commandAliases = commandsFile.getAliases();
		this.disabledCommands = commandsFile.getDisabledCommands();
		
	}
	
	public boolean isProxy() { return isProxy; }
	public String getServerId() { return serverId; }
	
	public int getDatabaseConnectionPoolSize() { return databaseConnectionPoolSize; }
	
	public String getDatabaseBanTableId() { return databaseBanTableId; }
	public String getDatabaseHomeTableId() { return databaseHomeTableId; }
	public String getDatabaseMuteTableId() { return databaseMuteTableId; }
	public String getDatabasePlayerTableId() { return databasePlayerTableId; }
	public String getDatabasePortalTableId() { return databasePortalTableId; }
	public String getDatabaseSpawnTableId() { return databaseSpawnTableId; }
	public String getDatabaseTicketTableId() { return databaseTicketTableId; }
	public String getDatabaseWarpTableId() { return databaseWarpTableId; }
	
	public boolean isDatabaseUsingMySQL() { return isDatabaseUsingMySQL; }
	public String getDatabaseMySQLHostname() { return databaseMySQLHostname; }
	public String getDatabaseMySQLDatabase() { return databaseMySQLDatabase; }
	public int getDatabaseMySQLPort() { return databaseMySQLPort; }
	public String getDatabaseMySQLUsername() { return databaseMySQLUsername; }
	public String getDatabaseMySQLPassword() { return databaseMySQLPassword; }
	
	public double getTeleportRequestTimeout() { return teleportRequestTimeout; }
	public double getTeleportDelay() { return teleportDelay; }
	
	public HashMap<String, List<String>> getCommandAliases() { return commandAliases; }
	public List<String> getDisabledCommands() { return disabledCommands; }
	
}
