package me.mn7cc.nexus;

import java.util.ArrayList;
import java.util.List;

import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.file.ModulesFile;
import me.mn7cc.nexus.module.NexusTeleportationModule;
import me.mn7cc.nexus.module.NexusWarpsModule;

public class NexusModuleManager {

	private Nexus instance;
	private ModulesFile modulesFile;
	private NexusTeleportationModule nexusTeleportationModule;
	private NexusWarpsModule nexusWarpsModule;
	private List<NexusModule> modules;
	
	public NexusModuleManager(Nexus instance, ModulesFile modulesFile) {
		this.instance = instance;
		this.modulesFile = modulesFile;
		this.modules = new ArrayList<NexusModule>();
	}
	
	public void loadModules() {
		
		nexusTeleportationModule = new NexusTeleportationModule(instance, modulesFile.isEnabled("teleportation"), "Teleporation");
		if(nexusTeleportationModule.isEnabled()) nexusTeleportationModule.enableModule();
		modules.add(nexusTeleportationModule);
		
		nexusWarpsModule = new NexusWarpsModule(instance, modulesFile.isEnabled("warps"), "Warps");
		if(nexusWarpsModule.isEnabled()) nexusWarpsModule.enableModule();
		modules.add(nexusWarpsModule);
		
	}
	
	public NexusTeleportationModule getTeleportationModule() { return nexusTeleportationModule; }
	public NexusWarpsModule getWarpsModule() { return nexusWarpsModule; }
	
	public List<NexusModule> getModules() { return modules; }
	
	public List<NexusModule> getEnabledModules() {
		List<NexusModule> enabledModules = new ArrayList<NexusModule>();
		for(NexusModule module : modules) if(module.isEnabled()) enabledModules.add(module);
		return enabledModules;
	}
	
	public List<NexusModule> getDisabledModules() {
		List<NexusModule> enabledModules = new ArrayList<NexusModule>();
		for(NexusModule module : modules) if(!module.isEnabled()) enabledModules.add(module);
		return enabledModules;
	}
	
}
