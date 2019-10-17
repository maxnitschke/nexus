package me.mn7cc.nexus.file;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

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
		defaults.put("modules.teleportation.settings.teleport-request-timeout", 120);
		defaults.put("modules.teleportation.settings.teleport-delay", 0);
		
		return defaults;
		
	}
	
	public int getTeleportRequestTimout() { return getIntegerValue("modules.teleportation.settings.teleport-request-timeout"); }
	public int getTeleportDelay() { return getIntegerValue("modules.teleportation.settings.teleport-delay"); }
	
}