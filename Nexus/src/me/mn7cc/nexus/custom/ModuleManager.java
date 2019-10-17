package me.mn7cc.nexus.custom;

import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.file.ModulesFile;
import me.mn7cc.nexus.module.NexusTeleportationModule;

public class ModuleManager {

	private static NexusTeleportationModule nexusTeleportationModule;
	
	public static void loadModules() {
		
		Plugin nexus = Nexus.getPlugin();
		ModulesFile modulesFile = FileManager.getModulesFile();
		
		nexusTeleportationModule = new NexusTeleportationModule(modulesFile.isEnabled("teleportation"));
		if(nexusTeleportationModule.isEnabled()) nexusTeleportationModule.enableModule(nexus);
		
	}
	
	public static NexusTeleportationModule getTeleportationModule() { return nexusTeleportationModule; }
	
}
