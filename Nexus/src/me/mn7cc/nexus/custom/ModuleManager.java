package me.mn7cc.nexus.custom;

import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.module.NexusTeleportationModule;

public class ModuleManager {

	private static NexusTeleportationModule nexusTeleportationModule;
	
	public static void loadModules() {
		
		Plugin nexus = Nexus.getPlugin();
		
		nexusTeleportationModule = new NexusTeleportationModule(true);
		if(nexusTeleportationModule.isEnabled()) {
			nexusTeleportationModule.enableModule(nexus);
		}
		
	}
	
}
