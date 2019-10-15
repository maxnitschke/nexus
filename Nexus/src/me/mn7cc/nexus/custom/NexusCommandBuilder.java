package me.mn7cc.nexus.custom;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class NexusCommandBuilder {
	
	private String label;
	private List<String> aliases;
	
	private INexusCommand command;
	private LinkedHashMap<String, INexusCommand> subCommands;
	
	public NexusCommandBuilder(String label, String... aliases) {
		this.label = label;
		this.aliases = Arrays.asList(aliases);
		this.command = null;
		this.subCommands = new LinkedHashMap<String, INexusCommand>();
	}
	
	public NexusCommandBuilder setCommand(INexusCommand command) {
		this.command = command;
		return this;
	}
	
	public NexusCommandBuilder addSubCommand(String label, INexusCommand command) {
		this.subCommands.put(label, command);
		return this;
	}
	
	public NexusCommand getNexusCommand() {
		return new NexusCommand(label, aliases, command, subCommands);
	}

}
