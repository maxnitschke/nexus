package me.mn7cc.nexus.custom;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.util.VaultUtils;

public class AccessList {
	
	private List<String> users;
	private List<String> groups;
	private List<String> permissions;
	
	public List<String> getUsers() { return users; }
	public List<String> getGroups() { return groups; }
	public List<String> getPermissions() { return permissions; }
	
	public AccessList(List<String> users, List<String> groups, List<String> permissions) {
		this.users = users;
		this.groups = groups;
		this.permissions = permissions;
	}
	
	public boolean hasAccess(Player player) {
		
		NexusPlayer nexusPlayer = Database.getPlayer(player);

		if(users.contains(nexusPlayer.getUUID())) return true;
		for(String group : nexusPlayer.getGroups()) if(groups.contains(group)) return true;
		for(String permission : permissions) if(player.hasPermission(permission)) return true;
		
		return false;
		
	}
	
}
