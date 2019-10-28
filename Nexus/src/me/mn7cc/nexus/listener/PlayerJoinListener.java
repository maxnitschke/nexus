package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.custom.Message;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.PlayerUtils;

public class PlayerJoinListener implements Listener {
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
 		
    	Player player = event.getPlayer();
    	
		String uuid = player.getUniqueId().toString();
		String name = player.getName().toLowerCase();
		
		NexusPlayer nexusPlayerByUUID = Database.getPlayerByUUID(uuid);
		NexusPlayer nexusPlayerByName = Database.getPlayerByName(name);
		
		if(nexusPlayerByUUID == null && nexusPlayerByName == null) {
			
			NexusPlayer nexusPlayer = new NexusPlayer(player);
			nexusPlayer.insert();
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_FIRST, player.getName());
			
		}
		else if(nexusPlayerByUUID == null && nexusPlayerByName != null) {
			
			Database.removePlayer(nexusPlayerByName.getUUID());
			Database.removePlayer(nexusPlayerByName.getName());
			
			nexusPlayerByName.setName(nexusPlayerByName.getUUID());
			nexusPlayerByName.update();
			
			NexusPlayer nexusPlayer = new NexusPlayer(player);
			nexusPlayer.insert();
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_FIRST, player.getName());
			
		}
		else if(nexusPlayerByUUID != null && nexusPlayerByName == null) {
			
			String oldName = nexusPlayerByUUID.getLastName();
			String newName = player.getName();
			
			Database.removePlayer(nexusPlayerByUUID.getUUID());
			Database.removePlayer(nexusPlayerByUUID.getName());
			
			nexusPlayerByUUID.setName(name);
			nexusPlayerByUUID.update();
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_NEW_NAME, oldName, newName);
	    	
		}
		else {

	    	NexusPlayer nexusPlayer = Database.getPlayer(player);
	    	nexusPlayer.setLastAccountName(player.getName());
	    	nexusPlayer.setIPAddress(PlayerUtils.getIP(player));
	    	nexusPlayer.update();
			
			event.setJoinMessage(MessageUtils.getMessage(Message.PLAYER_JOINED, player.getName()));
			
		}
		
	}

}