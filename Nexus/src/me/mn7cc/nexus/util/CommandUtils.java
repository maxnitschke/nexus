package me.mn7cc.nexus.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.custom.Message;

public class CommandUtils {
	
	public static boolean hasPermission(CommandSender sender, String permission, Message message, String... values) {
		
		if(!sender.hasPermission(permission)) {
			MessageUtils.send(sender, message, values);
			return false;
		}
		
		return true;
		
	}
	
	public static boolean hasPermission(Player player, String permission, Message message, String... values) {
		
		if(!player.hasPermission(permission)) {
			MessageUtils.send(player, message, values);
			return false;
		}
		
		return true;
		
	}
	
}
