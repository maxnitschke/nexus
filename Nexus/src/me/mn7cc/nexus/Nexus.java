package me.mn7cc.nexus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.mn7cc.nexus.listener.AsyncPlayerChatListener;
import me.mn7cc.nexus.listener.PlayerJoinListener;
import me.mn7cc.nexus.listener.PlayerKickListener;
import me.mn7cc.nexus.listener.PlayerQuitListener;
import me.mn7cc.nexus.task.DatabaseHeartbeatTask;
import me.mn7cc.nexus.util.MessageUtils;
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
		
		fileManager = new NexusFileManager(this);
		fileManager.loadFiles();
		
		MessageUtils.loadMessages(fileManager.getMessagesFile());
		
		commandManager = new NexusCommandManager();
		commandManager.loadCommands();
		
		moduleManager = new NexusModuleManager(this, fileManager.getModulesFile());
		moduleManager.loadModules();
		
		Bukkit.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		
		settings = new NexusSettings(false, this);
		
		database = new NexusDatabase(settings);
		database.load();
		
		networkData = new NexusNetworkData();
		
    	Bukkit.getServer().getScheduler().runTaskTimer(this, new DatabaseHeartbeatTask(database), 4L, 4L);
		
		VaultUtils.setupVault();
    	
	}
	
	@Override
	public void onDisable() {
		
		if(!database.isClosed()) database.heartbeat();
		
		database.close();
		
	}
	
}
