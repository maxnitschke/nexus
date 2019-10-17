package me.mn7cc.nexus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.mn7cc.nexus.custom.CommandManager;
import me.mn7cc.nexus.custom.FileManager;
import me.mn7cc.nexus.custom.ModuleManager;
import me.mn7cc.nexus.listener.AsyncPlayerChatListener;
import me.mn7cc.nexus.listener.PlayerKickListener;
import me.mn7cc.nexus.listener.PlayerQuitListener;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.VaultUtils;

public class Nexus extends JavaPlugin {

	private static Plugin plugin;
	public static Plugin getPlugin() { return plugin; }
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		FileManager.loadFiles();
		MessageUtils.loadMessages();
		
		VaultUtils.setupVault();
		
		CommandManager.loadCommands();
		ModuleManager.loadModules();
		
		Bukkit.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerKickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		
		Database.setup();
		Database.load();
		
//    	Bukkit.getServer().getScheduler().runTaskTimer(this, new DatabaseHeartbeatTask(), 5L, 5L);
		
	}
	
	@Override
	public void onDisable() {
		
		if(!Database.isClosed()) Database.heartbeat();
		
		Database.close();
		
	}
    
}
