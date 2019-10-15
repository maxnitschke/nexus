package me.mn7cc.nexus.custom;

import org.bukkit.entity.Player;

public class NexusPlayerSession {
	
	private Player player;
	
	public NexusPlayerSession(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() { return player; }

}
