package me.mn7cc.nexus.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class EventUtils {

	public static boolean isCancelled(Event event) {
		
		Bukkit.getPluginManager().callEvent(event);
		
		if(event instanceof Cancellable) {
			Cancellable cancellable = (Cancellable) event;
			if(cancellable.isCancelled()) return true;
		}
		
		return false;
		
	}
	
}
