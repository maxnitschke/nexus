package me.mn7cc.nexus.file;

import java.io.File;
import java.util.LinkedHashMap;

public class ModulesFile extends BaseFile {
	
	public ModulesFile() {
		super(getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "modules.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/Nexus/modules.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		defaults.put("modules.teleportation.enable", false);
		defaults.put("modules.teleportation.settings.teleport-request-timeout", "2m");
		defaults.put("modules.teleportation.settings.teleport-delay", "0s");
		
		return defaults;
		
	}
	
	public boolean isEnabled(String module) { return getBooleanValue("modules." + module + ".enable"); }
	
	public String getTeleportationTeleportRequestTimeout() { return getStringValue("modules.teleportation.settings.teleport-request-timeout"); }
	public String getTeleportationTeleportDelay() { return getStringValue("modules.teleportation.settings.teleport-delay"); }
	
}