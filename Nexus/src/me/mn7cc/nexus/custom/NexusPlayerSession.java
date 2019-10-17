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
	
	public void addTPARequest() {
		
	}

}
