package me.mn7cc.nexus.custom;

import org.bukkit.Bukkit;

import me.mn7cc.nexus.util.TextUtils;

public class Log {
	
	public static void INFO(String message) {
		
		Bukkit.getConsoleSender().sendMessage(TextUtils.color("&cNexus&7: &f" + message));
		
	}
	
}
