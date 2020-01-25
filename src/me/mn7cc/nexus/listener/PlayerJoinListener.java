package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.NexusDatabase;
import me.mn7cc.nexus.custom.Message;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.PlayerUtils;

public class PlayerJoinListener implements Listener {
	
	private Nexus instance;
	
	public PlayerJoinListener(Nexus instance) {
		this.instance = instance;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
 		
		NexusDatabase database = instance.getDatabase();
		
    	Player player = event.getPlayer();
    	
		String uuid = player.getUniqueId().toString();
		String name = player.getName().toLowerCase();
		
		NexusPlayer nexusPlayerByUUID = NexusPlayer.fromDatabaseByUUID(database, uuid);
		NexusPlayer nexusPlayerByName = NexusPlayer.fromDatabaseByName(database, name);
		
		if(nexusPlayerByUUID == null && nexusPlayerByName == null) {
			
			NexusPlayer nexusPlayer = new NexusPlayer(player);
			nexusPlayer.insert(database);
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_FIRST, player.getName());
			
		}
		else if(nexusPlayerByUUID == null && nexusPlayerByName != null) {
			
			database.getCache().removePlayer(nexusPlayerByName.getUUID());
			database.getCache().removePlayer(nexusPlayerByName.getName());
			
			nexusPlayerByName.setName(nexusPlayerByName.getUUID());
			nexusPlayerByName.update(database);
			
			NexusPlayer nexusPlayer = new NexusPlayer(player);
			nexusPlayer.insert(database);
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_FIRST, player.getName());
			
		}
		else if(nexusPlayerByUUID != null && nexusPlayerByName == null) {
			
			String oldName = nexusPlayerByUUID.getLastName();
			String newName = player.getName();
			
			database.getCache().removePlayer(nexusPlayerByUUID.getUUID());
			database.getCache().removePlayer(nexusPlayerByUUID.getName());
			
			nexusPlayerByUUID.setName(name);
			nexusPlayerByUUID.update(database);
			
			event.setJoinMessage(null);
			MessageUtils.broadcast(Message.PLAYER_JOINED_NEW_NAME, oldName, newName);
	    	
		}
		else {

	    	NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(database, player);
	    	nexusPlayer.setLastAccountName(player.getName());
	    	nexusPlayer.setIPAddress(PlayerUtils.getIP(player));
	    	nexusPlayer.update(database);
			
			event.setJoinMessage(MessageUtils.getMessage(Message.PLAYER_JOINED, player.getName()));
			
		}
		
	}

}