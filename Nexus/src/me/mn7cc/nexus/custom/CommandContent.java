package me.mn7cc.nexus.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.exception.NexusCommandException;
import me.mn7cc.nexus.exception.InvalidCommandModelException;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;

public class CommandContent {
	
	private CommandSender sender;
	private String label;
	private String[] args;
	private boolean requiresPlayer;
	private String permission;
	private String usage;
	private HashMap<Integer, Argument> arguments;
	
	public CommandSender getCommandSender() { return sender; }
	public String getLabel() { return label; }
	public String[] getArgs() { return args; }
	public boolean doesRequirePlayer() { return requiresPlayer; }
	public String getPermission() { return permission; }
	public String getUsage() { return usage; }
	
	public CommandContent(CommandSender sender, String label, String[] args, CommandModel model) throws NexusCommandException, InvalidCommandModelException {
		
		this.sender = sender;
		this.label = label;
		this.args = args;
		this.requiresPlayer = model.doesRequirePlayer();
		this.permission = model.getPermission();
		this.usage = model.getUsage();
		this.arguments = model.getArguments();
		
		if(requiresPlayer && sender instanceof Player == false) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_REQUIRES_PLAYER_AS_SENDER));
		if(!sender.hasPermission(permission)) throw new NexusCommandException(MessageUtils.getMessage(Message.INSUFFICIENT_PERMISSIONS));
		
		int minimumLength = 0; for(Argument argument : this.arguments.values()) if(!argument.isOptional()) minimumLength++;
		if(args.length < minimumLength) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_TOO_FEW_ARGUMENTS));
		
		boolean reachedOptional = false;
		boolean reachedAcceptsRemaining = false;
		int lastIndex = 0;
		int invalidOptionals = 0;
		
		for(Entry<Integer, Argument> entry : this.arguments.entrySet()) {
			
			int index = entry.getKey() - invalidOptionals;
			Argument argument = entry.getValue();
			if(index > lastIndex) lastIndex = index;
			
			if(args.length < index + 1) {
				if(argument.isOptional()) continue;
				else throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_TOO_FEW_ARGUMENTS));
			}
			
			if(reachedAcceptsRemaining || (reachedOptional && !argument.isOptional())) throw new InvalidCommandModelException();
			
			if(argument.isOptional()) reachedOptional = true;
			if(argument.getRequiredType().doesAcceptRemainingArguments()) reachedAcceptsRemaining = true;
			
			try {
				
				argument.setGivenArgument(argument.getRequiredType().doesAcceptRemainingArguments() ? StringUtils.combineArguments(index, args) : args[index]);
			
			}
			catch (NexusCommandException e) {
				if(argument.isOptional()) {
					invalidOptionals++;
					continue;
				}
				throw e;
			}
			catch (InvalidCommandModelException e) {
				if(argument.isOptional()) {
					invalidOptionals++;
					continue;
				}
				throw e;
			}
			
		}
		
		if(!reachedAcceptsRemaining && args.length > lastIndex + 2) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_TOO_MANY_ARGUMENTS));
		
	}
	
	public String getString(int index) { return arguments.containsKey(index) ? arguments.get(index).getString() : null; }
	public double getDouble(int index) { return arguments.containsKey(index) ? arguments.get(index).getDouble() : null; }
	public int getInteger(int index) { return arguments.containsKey(index) ? arguments.get(index).getInteger() : null; }
	public Player getPlayer(int index) { return arguments.containsKey(index) ? arguments.get(index).getPlayer() : null; }
	public List<Player> getPlayers(int index) { return arguments.containsKey(index) ? arguments.get(index).getPlayers() : null; }
	public NexusPlayer getNexusPlayer(int index) { return arguments.containsKey(index) ? arguments.get(index).getNexusPlayer() : null; }
	public NexusWarp getNexusWarp(int index) { return arguments.containsKey(index) ? arguments.get(index).getNexusWarp() : null; }

}
