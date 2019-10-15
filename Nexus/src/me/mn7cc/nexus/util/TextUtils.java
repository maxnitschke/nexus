package me.mn7cc.nexus.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class TextUtils {

	public static String color(String text) {
        return text = text
	    		.replaceAll("&0", ChatColor.BLACK.toString())
	    		.replaceAll("&1", ChatColor.DARK_BLUE.toString())
	    		.replaceAll("&2", ChatColor.DARK_GREEN.toString())
	    		.replaceAll("&3", ChatColor.DARK_AQUA.toString())
	    		.replaceAll("&4", ChatColor.DARK_RED.toString())
	    		.replaceAll("&5", ChatColor.DARK_PURPLE.toString())
	    		.replaceAll("&6", ChatColor.GOLD.toString())
	    		.replaceAll("&7", ChatColor.GRAY.toString())
	    		.replaceAll("&8", ChatColor.DARK_GRAY.toString())
	    		.replaceAll("&9", ChatColor.BLUE.toString())
	    		.replaceAll("&a", ChatColor.GREEN.toString())
	    		.replaceAll("&b", ChatColor.AQUA.toString())
	    		.replaceAll("&c", ChatColor.RED.toString())
	    		.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString())
	    		.replaceAll("&e", ChatColor.YELLOW.toString())
	    		.replaceAll("&f", ChatColor.WHITE.toString())
	    		.replaceAll("&k", ChatColor.MAGIC.toString())
	    		.replaceAll("&l", ChatColor.BOLD.toString())
	    		.replaceAll("&m", ChatColor.STRIKETHROUGH.toString())
	    		.replaceAll("&n", ChatColor.UNDERLINE.toString())
	    		.replaceAll("&o", ChatColor.ITALIC.toString());
	}
	
	public static List<String> color(List<String> text) {
		List<String> list = new ArrayList<String>();
		for(String s : text) list.add(TextUtils.color(s));
		return list;
	}
	
}
