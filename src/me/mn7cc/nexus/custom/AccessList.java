package me.mn7cc.nexus.custom;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.NexusDatabase;

public class AccessList {
	
	private List<String> users;
	private List<String> groups;
	private List<String> permissions;
	
	public List<String> getUsers() { return users; }
	public List<String> getGroups() { return groups; }
	public List<String> getPermissions() { return permissions; }
	
	public AccessList() {
		this.users = new ArrayList<String>();
		this.groups = new ArrayList<String>();
		this.permissions = new ArrayList<String>();
	}
	
	public AccessList(List<String> users, List<String> groups, List<String> permissions) {
		this.users = users;
		this.groups = groups;
		this.permissions = permissions;
	}
	
	public boolean hasAccess(NexusPlayer nexusPlayer) {
		
		if(users.contains(nexusPlayer.getUUID())) return true;
		for(String group : nexusPlayer.getGroups()) if(groups.contains(group)) return true;
		for(String permission : permissions) if(nexusPlayer.getSession() != null && nexusPlayer.getSession().getPlayer().hasPermission(permission)) return true;
		
		return false;
		
	}
	
	public void addUser(String uuid) { if(!users.contains(uuid)) users.add(uuid); }
	public void addGroup(String group) { if(!groups.contains(group)) groups.add(group); }
	public void addPermission(String permission) { if(!permissions.contains(permission)) permissions.add(permission); }
	
	public void removeUser(String uuid) { if(users.contains(uuid)) users.remove(uuid); }
	public void removeGroup(String group) { if(groups.contains(group)) groups.remove(group); }
	public void removePermission(String permission) { if(permissions.contains(permission)) permissions.remove(permission); }
	
	public boolean containsUser(String uuid) { return users.contains(uuid); }
	public boolean containsGroup(String group) { return groups.contains(group); }
	public boolean containsPermission(String permission) { return permissions.contains(permission); }
	
}
