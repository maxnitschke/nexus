package me.mn7cc.nexus;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;

import me.mn7cc.nexus.custom.AccessList;
import me.mn7cc.nexus.util.Base64Utils;

public class Encoder {
	
	public static String SETTINGS(HashMap<String, String> settings) {
		StringBuilder encoded = new StringBuilder();
		for(Entry<String, String> e : settings.entrySet()) {
			encoded.append("setting@" + e.getKey() + ";");
			encoded.append("value@" + e.getValue() + ";");
			encoded.append("#");
		}
		return Base64Utils.encodeString(encoded.toString());
	}
	
	public static String STRING_LIST(List<String> list) {
		StringBuilder encoded = new StringBuilder();
		for(String s : list) encoded.append(s + "#");
		return Base64Utils.encodeString(encoded.toString());
	}
	
	public static String INTEGER_LIST(List<Integer> list) {
		StringBuilder encoded = new StringBuilder();
		for(int i : list) encoded.append(i + "#");
		return Base64Utils.encodeString(encoded.toString());
	}
	
	public static String LOCATION(Location location) {
		if(location == null) return "";
		StringBuilder encoded = new StringBuilder();
		encoded.append("world@" + location.getWorld().getName() + ";");
		encoded.append("x@" + location.getX() + ";");
		encoded.append("y@" + location.getY() + ";");
		encoded.append("z@" + location.getZ() + ";");
		encoded.append("yaw@" + location.getYaw() + ";");
		encoded.append("pitch@" + location.getPitch() + ";");
		return Base64Utils.encodeString(encoded.toString());
	}
	
	public static String LOCATIONS(HashMap<String, Location> locations) {
		if(locations == null || locations.isEmpty()) return "";
		StringBuilder encoded = new StringBuilder();
		for(Entry<String, Location> e : locations.entrySet()) {
			Location location = e.getValue();
			encoded.append("id@" + e.getKey().toLowerCase() + ";");
			encoded.append("world@" + location.getWorld().getName() + ";");
			encoded.append("x@" + location.getX() + ";");
			encoded.append("y@" + location.getY() + ";");
			encoded.append("z@" + location.getZ() + ";");
			encoded.append("yaw@" + location.getYaw() + ";");
			encoded.append("pitch@" + location.getPitch() + ";");
			encoded.append("#");
		}
		return Base64Utils.encodeString(encoded.toString());
	}
	
	public static String ACCESS_LIST(AccessList accessList) {
		StringBuilder encoded = new StringBuilder();
		if(accessList == null) return encoded.toString();
		for(String user : accessList.getUsers()) encoded.append("user@" + user + ";");
		for(String group : accessList.getGroups()) encoded.append("group@" + group + ";");
		for(String permission : accessList.getPermissions()) encoded.append("permission@" + permission + ";");
		return Base64Utils.encodeString(encoded.toString());
	}
	
}