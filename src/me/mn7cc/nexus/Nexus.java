package me.mn7cc.nexus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.mn7cc.nexus.custom.Log;
import me.mn7cc.nexus.listener.AsyncPlayerChatListener;
import me.mn7cc.nexus.listener.PlayerCommandPreprocessListener;
import me.mn7cc.nexus.listener.PlayerJoinListener;
import me.mn7cc.nexus.listener.PlayerKickListener;
import me.mn7cc.nexus.listener.PlayerQuitListener;
import me.mn7cc.nexus.task.DatabaseHeartbeatTask;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;
import me.mn7cc.nexus.util.VaultUtils;

public class Nexus extends JavaPlugin {
	
	private NexusFileManager fileManager;
	private NexusCommandManager commandManager;
	private NexusModuleManager moduleManager;
	private NexusSettings settings;
	private NexusDatabase database;
	private NexusNetworkData networkData;
	
	public NexusSettings getSettings() { return settings; }
	public NexusCommandManager getCommandManager() { return commandManager; }
	public NexusFileManager getFileManager() { return fileManager; }
	public NexusModuleManager getModuleManager() { return moduleManager; }
	public NexusDatabase getDatabase() { return database; }
	public NexusNetworkData getNetworkData() { return networkData; }
	
	@Override
	public void onEnable() {
		
		double time = System.currentTimeMillis();
		
		Log.INFO("Enabling Nexus v" + this.getDescription().getVersion() + "..");
		
		fileManager = new NexusFileManager(this);
		fileManager.loadFiles();
		
		MessageUtils.loadMessages(fileManager.getMessagesFile());
		
		settings = new NexusSettings(false, this);
		
		commandManager = new NexusCommandManager(settings);
		commandManager.loadCommands();
		
		moduleManager = new NexusModuleManager(this, fileManager.getModulesFile());
		moduleManager.loadModules();
		
		Bukkit.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		
		database = new NexusDatabase(settings);
		database.load();
		
		networkData = new NexusNetworkData();
		
    	Bukkit.getServer().getScheduler().runTaskTimer(this, new DatabaseHeartbeatTask(database), 4L, 4L);
		
		VaultUtils.setupVault();
    	
		Log.INFO("Nexus has been &asuccessfully enabled&f! &8(&7" + StringUtils.toString(System.currentTimeMillis() - time) + "ms&8)");
		
		Log.INFO("&7Database ~ Platform: &f" + (settings.isDatabaseUsingMySQL() ? "MySQL" : "SQLite"));
		Log.INFO("&7Database ~ Connection Pool Size: &f" + settings.getDatabaseConnectionPoolSize());
		List<String> enabledModulesNames = new ArrayList<String>(); moduleManager.getEnabledModules().forEach(module -> enabledModulesNames.add(module.getName()));
		List<String> disabledModulesNames = new ArrayList<String>(); moduleManager.getDisabledModules().forEach(module -> disabledModulesNames.add(module.getName()));
		Log.INFO("&7Modules ~ &2Enabled&7: &f(" + enabledModulesNames.size() + ") " + (enabledModulesNames.size() > 0 ? StringUtils.replaceLast(String.join(", ", enabledModulesNames), ", ", " and ") : "- None -"));
		Log.INFO("&7Modules ~ &4Disabled&7: &f(" + disabledModulesNames.size() + ") " + (disabledModulesNames.size() > 0 ? StringUtils.replaceLast(String.join(", ", disabledModulesNames), ", ", " and ") : "- None -"));
		
		
	}
	
	@Override
	public void onDisable() {
		
		if(!database.isClosed()) database.heartbeat();
		
		database.close();
		
	}
	
}
