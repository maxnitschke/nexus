package me.mn7cc.nexus.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtils {

	public static Player getPlayer(String name) {
		
		if(Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name);

		Player p = null;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getName().contains(name)) {
				if(p != null) return null;
				p = player;
			}
		}
		
		return p;
		
	}
	
	public static Player getPlayerExact(String name) {
		
		Player p = null;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getName().equalsIgnoreCase(name)) {
				if(p != null) return null;
				p = player;
			}
		}
		
		return p;
		
	}
	
}
