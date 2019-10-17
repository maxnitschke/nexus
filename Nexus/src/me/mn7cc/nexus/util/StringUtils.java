package me.mn7cc.nexus.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StringUtils {

	public static String combineArguments(int start, String[] args) {
		if(args.length <= start) return "";
		StringBuilder combined = new StringBuilder();
		for(int i = start; i < args.length; i++) combined.append(args[i] + " ");
		combined.deleteCharAt(combined.length() - 1);
		return combined.toString();
	}
	
    public static String replaceLast(String string, String regex, String replacement) {
        int lastIndex = string.lastIndexOf(regex);
        if(lastIndex == -1) return string;
        String beginString = string.substring(0, lastIndex);
        String endString = string.substring(lastIndex + regex.length());
        return beginString + replacement + endString;
    }
	
	public static String generateUUID(int length) {
		
		String characters = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder uuid = new StringBuilder();
		for (int i = 0; i < length; i++) uuid.append(characters.charAt(MathUtils.random(0, characters.length() - 1)));
		return uuid.toString();
		
	}
	
	public static boolean isInteger(String string) {
		try { Integer.parseInt(string); }
		catch (NumberFormatException e) { return false;	}
		return true;
	}
	
	public static int parseInteger(String string) {
		int i;
		try { i = Integer.parseInt(string); }
		catch (NumberFormatException e) { return 0; }
		return i;
	}
	
	public static boolean isDouble(String string) {
		try { Double.parseDouble(string); }
		catch (NumberFormatException e) { return false;	}
		return true;
	}
	
	public static double parseDouble(String string) {
		double d;
		try { d = Double.parseDouble(string); }
		catch (NumberFormatException e) { return 0; }
		return d;
	}
	
	public static boolean isItem(String string) {
		String i = string;
		if(string.contains(":")) {
			String[] s = string.split(":");
			if(!isInteger(s[1])) return false;
			i = s[0];
		}
		if(Material.matchMaterial(i) == null) return false;
		return true;
	}
	
	public static ItemStack parseItem(String string) {
		String i = string;
		int d = 0;
		if(string.contains(":")) {
			String[] s = string.split(":");
			if(!isInteger(s[1])) return null;
			i = s[0];
			d = parseInteger(s[1]);
		}
		if(Material.matchMaterial(i) == null) return null;
		ItemStack item = new ItemStack(Material.matchMaterial(i));
		item.setDurability((short) d);
		return item;
	}
	
	public static String toString(double d) {
		if(d >= 100) return String.valueOf(Math.round(d));
		return d % 1 == 0 ? String.valueOf(Math.round(d)) : String.valueOf(MathUtils.shorten(d));
	}
	
	public static String toString(int i) {
		return String.valueOf(i);
	}
	
	public static String toString(boolean b) {
		return String.valueOf(b);
	}
	
	public static String formatDouble(double d) {
		return String.format("%.0f", d);
	}
	
	public static String toString(Material material) {
		StringBuilder name = new StringBuilder();
		for(String s : material.name().split("_")) name.append(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ");
		if(!name.toString().isEmpty()) name.delete(name.length() - 1, name.length());
		return name.toString();
	}
	
	public static String toString(ItemStack itemStack) {
		return itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : toString(itemStack.getType());
	}
	
	public static String toString(Enchantment enchantment) {
		
		if(enchantment.equals(Enchantment.DURABILITY)) return "Unbreaking";
		if(enchantment.equals(Enchantment.DIG_SPEED)) return "Efficiency";
		if(enchantment.equals(Enchantment.LOOT_BONUS_BLOCKS)) return "Fortune";
		if(enchantment.equals(Enchantment.SILK_TOUCH)) return "Silk Touch";
		
		return "Unknown";
		
	}
	
	public static String toString(List<Player> players) {
		List<String> names = new ArrayList<String>();
		for(Player player : players) names.add(player.getName());
		return StringUtils.replaceLast(String.join(", ", names), ", ", " and ");
	}
	
	public static String toAmountString(double amount) {
		return amount % 1 == 0 ? new DecimalFormat("#,###").format(amount) : new DecimalFormat("#,###.00").format(amount);
	}
	
	public static String toRomanNumber(int i) {
		
		if(i > 10000) return "";
		
		TreeMap<Integer, String> numbers = new TreeMap<Integer, String>();
        numbers.put(1000, "M");
	    numbers.put(900, "CM");
	    numbers.put(500, "D");
        numbers.put(400, "CD");
        numbers.put(100, "C");
        numbers.put(90, "XC");
        numbers.put(50, "L");
        numbers.put(40, "XL");
        numbers.put(10, "X");
        numbers.put(9, "IX");
        numbers.put(5, "V");
        numbers.put(4, "IV");
        numbers.put(1, "I");
        
        StringBuilder result = new StringBuilder();
        while(i > 0) {
        	int number = numbers.floorKey(i);
        	result.append(numbers.get(number));
        	i -= number;
        }
        
        return result.toString();
        
	}
	
	public static String toAoE(int radius) {
		int aoe = radius * 2 + 1;
		return aoe + "x" + aoe;
	}
	
}
