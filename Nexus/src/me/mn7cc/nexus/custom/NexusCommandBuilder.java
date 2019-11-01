package me.mn7cc.nexus.custom;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import me.mn7cc.nexus.Nexus;

public class NexusCommandBuilder {
	
	private Nexus instance;
	private INexusCommand command;
	private String label;
	private List<String> aliases;
	
	private LinkedHashMap<String, INexusCommand> subCommands;
	
	public NexusCommandBuilder(Nexus instance) {
		this.instance = instance;
		this.subCommands = new LinkedHashMap<String, INexusCommand>();
	}
	
	public NexusCommandBuilder setCommand(INexusCommand command, String label, String... aliases) {
		this.command = command;
		this.label = label;
		this.aliases = Arrays.asList(aliases);
		return this;
	}
	
	public NexusCommandBuilder addSubCommand(INexusCommand command, String label, String... aliases) {
		this.subCommands.put(label, command);
		for(String alias : aliases) this.subCommands.put(alias, command);
		return this;
	}
	
	public NexusCommand getNexusCommand() {
		return new NexusCommand(instance, label, aliases, command, subCommands);
	}

}
