package me.mn7cc.nexus.custom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mn7cc.nexus.exception.NexusCommandException;
import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.exception.InvalidCommandModelException;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;

public class NexusCommand extends Command {

	private Nexus instance;
	private INexusCommand command;
	private LinkedHashMap<String, INexusCommand> subCommands;
	
	public NexusCommand(Nexus instance, String label, List<String> aliases, INexusCommand command, LinkedHashMap<String, INexusCommand> subCommands) {
		super(label);
		setAliases(aliases);
		this.instance = instance;
		this.command = command;
		this.subCommands = subCommands;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		double time = System.currentTimeMillis();
		
		Log.INFO("Running command: /" + label + "..");		
		
		INexusCommand command = getCommand(args);
		
		if(command == null) {
			showHelp(sender);
			return true;
		}
		
		CommandModel model = getModel(command);
		CommandContent content;
		
		try {
			
			content =  new CommandContent(instance, sender, label, args, model);
			command.execute(sender, label, args, content);
			
		}
		catch (NexusCommandException e) {
			if(e.getMessage() != null && !e.getMessage().isEmpty()) MessageUtils.send(sender, e.getMessage());
			if(e.showUsage()) MessageUtils.send(sender, Message.COMMAND_USAGE, model.getUsage());
		}
		catch (InvalidCommandModelException e) {
			e.printStackTrace();
		}
		
		Log.INFO("Command has been &asuccessfully executed&f! &8(&7" + StringUtils.toString(System.currentTimeMillis() - time) + "ms&8)");
		
		return true;
		
	}
	
	public INexusCommand getCommand(String[] args) {
		
		if(args.length == 0) return command;
		
		String arguments = String.join(" ", args);
		for(Entry<String, INexusCommand> entry : subCommands.entrySet()) {
			if(arguments.startsWith(entry.getKey().toLowerCase())) return entry.getValue();
		}
		
		return command;

	}
	
	public CommandModel getModel(INexusCommand command) {
		
		return command instanceof CommandModel ? (CommandModel) command : null;
		
	}
	
	public void showHelp(CommandSender sender) {
		
		MessageUtils.send(sender, Message.COMMAND_HELP_HEADER, "/" + getLabel().toLowerCase());
		
		if(command != null) MessageUtils.send(sender, Message.COMMAND_HELP_COMMAND, getModel(command).getUsage());
		
		for(Entry<String, INexusCommand> entry : subCommands.entrySet()) {
			MessageUtils.send(sender, Message.COMMAND_HELP_COMMAND, getModel(entry.getValue()).getUsage());
		}
		
	}
	
}
