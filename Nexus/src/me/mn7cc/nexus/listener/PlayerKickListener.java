package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.custom.NexusPlayer;

public class PlayerKickListener implements Listener {
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerKick(PlayerKickEvent event) {
 		
		event.setLeaveMessage(null);
		
		Player player = event.getPlayer();
		
    	if(player == null || !player.isOnline()) return;
		
		NexusPlayer nexusPlayer = Database.getPlayer(player);
		
		nexusPlayer.quit();
		
	}

}