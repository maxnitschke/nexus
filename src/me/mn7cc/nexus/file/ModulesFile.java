package me.mn7cc.nexus.file;

import java.io.File;
import java.util.LinkedHashMap;

import me.mn7cc.nexus.NexusFileManager;

public class ModulesFile extends BaseFile {
	
	public ModulesFile(NexusFileManager fileManager) {
		super(fileManager, getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "modules.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/Nexus/config/modules.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		defaults.put("modules.teleportation.enable", true);
		defaults.put("modules.teleportation.settings.teleport-request-timeout", "2m");
		defaults.put("modules.teleportation.settings.teleport-delay", "0s");
		defaults.put("modules.warps.enable", true);
		
		return defaults;
		
	}
	
	public boolean isEnabled(String module) { return getBooleanValue("modules." + module + ".enable"); }
	
	public String getTeleportationTeleportRequestTimeout() { return getStringValue("modules.teleportation.settings.teleport-request-timeout"); }
	public String getTeleportationTeleportDelay() { return getStringValue("modules.teleportation.settings.teleport-delay"); }
	
}