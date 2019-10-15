package me.mn7cc.nexus.custom;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;


public class CommandManager {

	private static CommandMap commandMap;
	
	public static void setCommandMap(CommandMap commandMap) {
		CommandManager.commandMap = commandMap;
	}
	
	public static CommandMap getCommandMap() {
		return commandMap;
	}
	
	public static void registerCommand(NexusCommand command) {
		commandMap.register(command.getLabel().toLowerCase(), command);
	}
	
	public static void loadCommands() {
		
        try {
        
        	final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        	field.setAccessible(true);
        	commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
	            
		}
        catch (NoSuchFieldException e) { e.printStackTrace(); }
        catch (SecurityException e) { e.printStackTrace(); }
        catch (IllegalArgumentException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
        
//        registerCommand("tp",
//        				new NexusCommandExecutor("tp", Arrays.asList("teleport"), new NexusWarpsModule.CommandWarp(),
//        				new CommandModel(true, "nexus.tp", "/tp <player> [target]",
//        				new Argument(0, "<player>", Argument.Type.PLAYER),
//						new Argument(1, "[target]", Argument.Type.PLAYER, true))));
        
	}
	
}
