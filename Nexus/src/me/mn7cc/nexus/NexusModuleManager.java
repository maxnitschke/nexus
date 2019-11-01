package me.mn7cc.nexus;

import me.mn7cc.nexus.file.ModulesFile;
import me.mn7cc.nexus.module.NexusTeleportationModule;

public class NexusModuleManager {

	private Nexus instance;
	private ModulesFile modulesFile;
	private NexusTeleportationModule nexusTeleportationModule;
	
	public NexusModuleManager(Nexus instance, ModulesFile modulesFile) {
		this.instance = instance;
		this.modulesFile = modulesFile;
	}
	
	public void loadModules() {
		
		nexusTeleportationModule = new NexusTeleportationModule(instance, modulesFile.isEnabled("teleportation"));
		if(nexusTeleportationModule.isEnabled()) nexusTeleportationModule.enableModule();
		
	}
	
	public NexusTeleportationModule getTeleportationModule() { return nexusTeleportationModule; }
	
}
