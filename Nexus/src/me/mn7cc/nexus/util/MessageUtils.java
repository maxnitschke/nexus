package me.mn7cc.nexus.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.custom.FileManager;
import me.mn7cc.nexus.custom.Message;
import me.mn7cc.nexus.file.MessagesFile;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils {
	
	public static HashMap<Message, String> messages = new HashMap<Message, String>();
	
	public static void addMessage(String m, String text) {
		
		Message message = null;
		try { message = Message.valueOf(m); }
		catch(IllegalArgumentException e) { return; }
		
		messages.put(message, text);
		
	}
	
	public static String getMessage(Message message, String... values) {
		
		if(!messages.containsKey(message)) return null;
		
		String result = messages.get(message);
		if(result == null || result.isEmpty() || result.equalsIgnoreCase("disabled")) return null;
			
		if(values != null) {
		
			int index = 0;
			for(String v : values) {
				result = result.replaceAll("\\{" + index + "\\}", v);
				index++;
			}
		
		}
		
		return TextUtils.color(result);
		
	}
	
	public static void loadMessages() {
		
		MessagesFile messages = FileManager.getMessagesFile();
		for(Entry<String, String> entry : messages.getMessages().entrySet()) addMessage(entry.getKey(), entry.getValue());
		
	}
	
	public static void send(CommandSender sender, String message) {
		sender.sendMessage(TextUtils.color(message));
	}
	
	public static void send(CommandSender sender, TextComponent textComponent) {
		sender.spigot().sendMessage(textComponent);
	}
	
	public static void send(CommandSender sender, Message message) {
		if(message == null || message.getText().isEmpty()) return;
		sender.sendMessage(TextUtils.color(getMessage(message)));
	}
	
	public static void send(CommandSender sender, Message message, String... values) {
		if(message == null || message.getText().isEmpty()) return;
		sender.sendMessage(TextUtils.color(getMessage(message, values)));
	}
	
	public static void send(Player player, String message) {
		player.sendMessage(TextUtils.color(message));
	}
	
	public static void send(Player player, TextComponent textComponent) {
		player.spigot().sendMessage(textComponent);
	}

	public static void send(Player player, Message message) {
		if(message == null || message.getText().isEmpty()) return;
		player.sendMessage(TextUtils.color(getMessage(message)));
	}
	
	public static void send(Player player, Message message, String... values) {
		if(message == null || message.getText().isEmpty()) return;
		player.sendMessage(TextUtils.color(getMessage(message, values)));
	}
	
	public static void broadcast(String message) {
		Bukkit.getServer().broadcastMessage(TextUtils.color(message));
	}
	
	public static void broadcast(Message message) {
		if(message == null || message.getText().isEmpty()) return;
		Bukkit.getServer().broadcastMessage(TextUtils.color(getMessage(message)));
	}
	
	public static void broadcast(Message message, String... values) {
		if(message == null || message.getText().isEmpty()) return;
		Bukkit.getServer().broadcastMessage(TextUtils.color(getMessage(message, values)));
	}
	
	public static void broadcast(String permission, String message) {
		for(Player player : Bukkit.getOnlinePlayers()) if(player.hasPermission(permission)) player.sendMessage(TextUtils.color(message));
	}
	
	public static void broadcast(String permission, Message message) {
		if(message == null || message.getText().isEmpty()) return;
		for(Player player : Bukkit.getOnlinePlayers()) if(player.hasPermission(permission)) player.sendMessage(TextUtils.color(getMessage(message)));
	}
	
	public static void broadcast(String permission, Message message, String... values) {
		if(message == null || message.getText().isEmpty()) return;
		for(Player player : Bukkit.getOnlinePlayers()) if(player.hasPermission(permission)) player.sendMessage(TextUtils.color(getMessage(message, values)));
	}
	
}