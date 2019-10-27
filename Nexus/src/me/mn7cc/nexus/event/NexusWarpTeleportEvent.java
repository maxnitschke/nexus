package me.mn7cc.nexus.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mn7cc.nexus.custom.NexusWarp;

public class NexusWarpTeleportEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	
	private Player player;
	private NexusWarp nexusWarp;
	private boolean cancelled;
	
	public NexusWarpTeleportEvent(Player player, NexusWarp nexusWarp) {
		this.player = player;
		this.nexusWarp = nexusWarp;
		this.cancelled = false;
	}
	
	public Player getPlayer() { return player; }
	public NexusWarp getWarp() { return nexusWarp; }
	
	@Override public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
	@Override public boolean isCancelled() { return cancelled; }
	@Override public HandlerList getHandlers() { return HANDLERS; }

}
