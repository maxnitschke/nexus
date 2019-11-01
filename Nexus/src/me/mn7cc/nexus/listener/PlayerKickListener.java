package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.custom.Message;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.util.MessageUtils;

public class PlayerKickListener implements Listener {
	
	private Nexus instance;
	
	public PlayerKickListener(Nexus instance) {
		this.instance = instance;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerKick(PlayerKickEvent event) {
 		
		Player player = event.getPlayer();
		
		event.setLeaveMessage(MessageUtils.getMessage(Message.PLAYER_LEFT, player.getName()));
		
    	if(player == null || !player.isOnline()) return;
		
		NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(instance.getDatabase(), player);
		
		nexusPlayer.quit(instance.getDatabase());
		
	}

}