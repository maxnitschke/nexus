package me.mn7cc.nexus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
 		
		Player player = event.getPlayer();
		String message = event.getMessage();
		
	}
	
}