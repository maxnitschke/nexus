package me.mn7cc.nexus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.mn7cc.nexus.custom.AccessList;
import me.mn7cc.nexus.util.Base64Utils;
import me.mn7cc.nexus.util.StringUtils;

public class Decoder {
	
	public static HashMap<String, String> SETTINGS(String settings) {
		
		HashMap<String, String> decoded = new HashMap<String, String>();
		
		if(settings == null || settings.equals("")) return decoded;
		settings = Base64Utils.decodeString(settings);
		
		for(String setting : settings.split("#")) {
			
			String s1 = "";
			String s2 = "";
			
			for(String value : setting.split(";")) {
				String p = value.split("@")[0];
				String v = value.split("@")[1];
				
				if(p.equals("setting")) s1 = v;
				if(p.equals("value")) s2 = v;
				
			}
			
			decoded.put(s1, s2);
			
		}
		
		return decoded;
		
	}
	
	public static List<String> STRING_LIST(String list) {
		List<String> decoded = new ArrayList<String>();
		if(list == null) return decoded;
		list = Base64Utils.decodeString(list);
		if(list.equals("")) return decoded;
		for(String s : list.split("#")) decoded.add(s);
		return decoded;
	}
	
	public static List<Integer> INTEGER_LIST(String list) {
		List<Integer> decoded = new ArrayList<Integer>();
		if(list == null) return decoded;
		list = Base64Utils.decodeString(list);
		if(list.equals("")) return decoded;
		for(String s : list.split("#")) decoded.add(StringUtils.parseInteger(s));
		return decoded;
	}
	
	public static Location LOCATION(String location) {
		
		if(location == null || location.equals("")) return null;
		location = Base64Utils.decodeString(location);
		
		World world = null;
		double x = 0;
		double z = 0;
		double y = 0;
		double yaw = 0;
		double pitch = 0;
		
		for(String value : location.split(";")) {
			String p = value.split("@")[0];
			String v = value.split("@")[1];
			if(p.equals("world")) { world = Bukkit.getWorld(v); if(world == null) return null; }
			if(p.equals("x")) x = StringUtils.parseDouble(v);
			if(p.equals("y")) y = StringUtils.parseDouble(v);
			if(p.equals("z")) z = StringUtils.parseDouble(v);
			if(p.equals("yaw")) yaw = StringUtils.parseDouble(v);
			if(p.equals("pitch")) pitch = StringUtils.parseDouble(v);
		}
		
		return new Location(world, x, y, z, (float) yaw, (float) pitch);
		
	}
	
	public static HashMap<String, Location> LOCATIONS(String locations) {
		
		HashMap<String, Location> decoded = new HashMap<String, Location>();
		
		if(locations == null || locations.equals("")) return decoded;
		locations = Base64Utils.decodeString(locations);
		
		for(String location : locations.split("#")) {
		
			String id = "";
			World world = null;
			double x = 0;
			double z = 0;
			double y = 0;
			double yaw = 0;
			double pitch = 0;
			
			for(String value : location.split(";")) {
				String p = value.split("@")[0];
				String v = value.split("@")[1];
				
				if(p.equals("id")) id = v;
				if(p.equals("world")) { world = Bukkit.getWorld(v); if(world == null) continue; }
				if(p.equals("x")) x = StringUtils.parseDouble(v);
				if(p.equals("y")) y = StringUtils.parseDouble(v);
				if(p.equals("z")) z = StringUtils.parseDouble(v);
				if(p.equals("yaw")) yaw = StringUtils.parseDouble(v);
				if(p.equals("pitch")) pitch = StringUtils.parseDouble(v);
			}
			
			decoded.put(id, new Location(world, x, y, z, (float) yaw, (float) pitch));
		
		}
		
		return decoded;
		
	}
	
	public static AccessList ACCESS_LIST(String accessList) {
		
		if(accessList == null || accessList.equals("")) return null;
		accessList = Base64Utils.decodeString(accessList);
		
		List<String> users = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		
		for(String value : accessList.split(";")) {
			String p = value.split("@")[0];
			String v = value.split("@")[1];
			if(p.equals("user")) users.add(v);
			if(p.equals("group")) groups.add(v);
			if(p.equals("permission")) permissions.add(v);
		}
		
		return new AccessList(users, groups, permissions);
		
	}
	
}
