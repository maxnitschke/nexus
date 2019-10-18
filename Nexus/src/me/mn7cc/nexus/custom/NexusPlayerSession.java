package me.mn7cc.nexus.custom;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class NexusPlayerSession {
	
	private Player player;
	private HashMap<Player, Double> pendingTPARequests;
	private HashMap<Player, Double> pendingTPAHereRequests;
	
	public NexusPlayerSession(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() { return player; }
	
	public void addTPARequest(Player player, double time) {
		pendingTPARequests.put(player, System.currentTimeMillis() + time);
	}
	
	public void removeTPARequest(Player player) {
		pendingTPARequests.remove(player);
	}
	
	public boolean hasTPARequest(Player player) {
		return pendingTPARequests.containsKey(player) && pendingTPARequests.get(player) >= System.currentTimeMillis();
	}
	
	public void addTPAHereRequest(Player player, double time) {
		pendingTPAHereRequests.put(player, System.currentTimeMillis() + time);
	}
	
	public void removeTPAHereRequest(Player player) {
		pendingTPAHereRequests.remove(player);
	}
	
	public boolean hasTPAHereRequest(Player player) {
		return pendingTPAHereRequests.containsKey(player) && pendingTPAHereRequests.get(player) >= System.currentTimeMillis();
	}

}
