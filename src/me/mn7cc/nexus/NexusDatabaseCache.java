package me.mn7cc.nexus;

import java.util.HashMap;
import java.util.TreeSet;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.custom.NexusWarp;

public class NexusDatabaseCache {
	
	private HashMap<String, NexusPlayer> players;
	private HashMap<String, NexusWarp> warps;
	
	public static TreeSet<String> warpIdSet;
	
	public NexusDatabaseCache() {
		
		this.players = new HashMap<String, NexusPlayer>();
		this.warps = new HashMap<String, NexusWarp>();
		
		warpIdSet = new TreeSet<String>();;
		
	}
	
	public HashMap<String, NexusPlayer> getPlayers() { return players; }
	public HashMap<String, NexusWarp> getWarps() { return warps; }
	
	public TreeSet<String> getWarpIdSet() { return warpIdSet; }
	
	public void addPlayer(NexusPlayer player) { players.put(player.getUUID(), player); players.put(player.getName(), player); }
	public void removePlayer(NexusPlayer player) { players.remove(player.getUUID()); players.remove(player.getName()); }
	public void removePlayer(String key) { players.remove(key); }
	public boolean containsPlayer(String key) { return players.containsKey(key); }
	public NexusPlayer getPlayer(String key) { return players.get(key); }
	
	public void addWarp(NexusWarp warp) { warps.put(warp.getId(), warp); }
	public void removeWarp(NexusWarp warp) { warps.remove(warp.getId()); }
	public void removeWarp(String id) { warps.remove(id); }
	public boolean containsWarp(String id) { return warps.containsKey(id); }
	public NexusWarp getWarp(String id) { return warps.get(id); }
	
	public void addWarpIdToSet(String id) { warpIdSet.add(id); }
	public void addWarpIdToSet(NexusWarp warp) { warpIdSet.add(warp.getId()); }
	public void removeWarpIdFromSet(NexusWarp warp) { warpIdSet.remove(warp.getId()); }
	public void removeWarpIdFromSet(String id) { warpIdSet.remove(id); }
	public boolean containsWarpId(String id) { return warpIdSet.contains(id); }
	
}
	