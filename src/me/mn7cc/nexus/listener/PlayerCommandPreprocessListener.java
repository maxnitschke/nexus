package me.mn7cc.nexus.listener;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.mn7cc.nexus.Nexus;

public class PlayerCommandPreprocessListener implements Listener {
	
	private Nexus instance;
	
	public PlayerCommandPreprocessListener(Nexus instance) {
		this.instance = instance;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
 		
		String command = event.getMessage().toLowerCase();
		
		for(Entry<String, List<String>> entry : instance.getSettings().getCommandAliases().entrySet()) {
			for(String alias : entry.getValue()) {
				if(command.toLowerCase().startsWith("/" + alias.toLowerCase())) {
					command = command.replaceFirst(alias.toLowerCase(), entry.getKey().toLowerCase());
					event.setMessage(command);
					return;
				}
			}
		}
		
	}

}