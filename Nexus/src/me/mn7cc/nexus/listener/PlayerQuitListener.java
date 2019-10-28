package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.custom.Message;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.util.MessageUtils;

public class PlayerQuitListener implements Listener {
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
 		
		Player player = event.getPlayer();
		
		event.setQuitMessage(MessageUtils.getMessage(Message.PLAYER_LEFT, player.getName()));
		
    	if(player == null || !player.isOnline()) return;
		
		NexusPlayer nexusPlayer = Database.getPlayer(player);
		
		nexusPlayer.quit();
		
	}

}