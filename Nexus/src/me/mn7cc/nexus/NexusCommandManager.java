package me.mn7cc.nexus;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import me.mn7cc.nexus.custom.NexusCommand;

public class NexusCommandManager {

	private NexusSettings settings;
	private CommandMap commandMap;
	
	public NexusCommandManager(NexusSettings settings) {
		
		this.settings = settings;
		
        try {
            
        	final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        	field.setAccessible(true);
        	this.commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
	            
		}
        catch (NoSuchFieldException e) { e.printStackTrace(); }
        catch (SecurityException e) { e.printStackTrace(); }
        catch (IllegalArgumentException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
		
	}
	
	public void setCommandMap(CommandMap commandMap) {
		this.commandMap = commandMap;
	}
	
	public CommandMap getCommandMap() {
		return commandMap;
	}
	
	public void registerCommand(NexusCommand command) {
		if(!settings.getDisabledCommands().contains(command.getLabel().toLowerCase())) commandMap.register(command.getLabel().toLowerCase(), command);
	}
	
	public void loadCommands() {
        
//        registerCommand("tp",
//        				new NexusCommandExecutor("tp", Arrays.asList("teleport"), new NexusWarpsModule.CommandWarp(),
//        				new CommandModel(true, "nexus.tp", "/tp <player> [target]",
//        				new Argument(0, "<player>", Argument.Type.PLAYER),
//						new Argument(1, "[target]", Argument.Type.PLAYER, true))));
        
	}
	
}
