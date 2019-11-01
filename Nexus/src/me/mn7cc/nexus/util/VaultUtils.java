package me.mn7cc.nexus.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.mn7cc.nexus.custom.Log;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtils {
	
	private static Economy economy;
	private static Permission permission;
	private static Chat chat;
	
	public static Economy getEconomy() { return economy; }
	public static Permission getPermission() { return permission; }
	public static Chat getChat() { return chat; }
	
	public static void setupVault() {
		
		Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
		
		if(vault == null) {
			Log.INFO("Error while hooking into Vault: Plugin not found!");
			return;
		}
		
		
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
        	Log.INFO("Error while hooking into Vault: Invalid economy provider!");
            return;
        }
        
        economy = economyProvider.getProvider();
        
        RegisteredServiceProvider<Permission> permissionsProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionsProvider == null) {
        	Log.INFO("Error while hooking into Vault: Invalid permissions provider!");
            return;
        }
        
        permission = permissionsProvider.getProvider();
        
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider == null) {
        	Log.INFO("Error while hooking into Vault: Invalid chat provider!");
            return;
        }
        
        chat = chatProvider.getProvider();
        
        Log.INFO("Successfully hooked into Vault! (Version: " + vault.getDescription().getVersion() + ")");
		
	}

}
