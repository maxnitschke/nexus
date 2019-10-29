package me.mn7cc.nexus.custom;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class NexusCommandBuilder {
	
	private INexusCommand command;
	private String label;
	private List<String> aliases;
	
	private LinkedHashMap<String, INexusCommand> subCommands;
	
	public NexusCommandBuilder(INexusCommand command, String label, String... aliases) {
		this.command = command;
		this.label = label;
		this.aliases = Arrays.asList(aliases);
		this.subCommands = new LinkedHashMap<String, INexusCommand>();
	}
	
	public NexusCommandBuilder setCommand(INexusCommand command) {
		this.command = command;
		return this;
	}
	
	public NexusCommandBuilder addSubCommand(INexusCommand command, String label, String... aliases) {
		this.subCommands.put(label, command);
		for(String alias : aliases) this.subCommands.put(alias, command);
		return this;
	}
	
	public NexusCommand getNexusCommand() {
		return new NexusCommand(label, aliases, command, subCommands);
	}

}
